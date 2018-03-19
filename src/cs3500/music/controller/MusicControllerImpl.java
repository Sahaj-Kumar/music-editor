package cs3500.music.controller;

import cs3500.music.model.MusicEditorModel;
import cs3500.music.model.Note;
import cs3500.music.util.NoteFactory;
import cs3500.music.view.IViewMain;


import javax.sound.midi.*;
import java.awt.event.KeyEvent;
import java.lang.reflect.Array;
import java.util.*;

/**
 * Created by sahaj on 6/18/2017.
 */

/**
 * Implementation of MusicController. Has Access to the model and view. Assigns a mouse handler
 * and key handler to view so that it knows what to do based off of various different key presses
 * and mouse clicks.
 */
public class MusicControllerImpl implements MusicController {

  MusicEditorModel model;
  IViewMain view;
  // takes instance of mouseHandler to continually manipulate.
  private MouseHandler mouseHandler;
  private Timer timer;

  /**
   * Constuctor for MusicControllerImpl. Takes model and view, and assigns appropriate mouse and
   * key handlers to the view.
   * @param model the model
   * @param view the view
   * @throws InvalidMidiDataException error during midi processes
   * @throws MidiUnavailableException error during midi processes
   */
  public MusicControllerImpl(MusicEditorModel model, IViewMain view)
          throws InvalidMidiDataException, MidiUnavailableException {
    this.model = model;
    this.view = view;
    this.timer = new Timer();
    this.buildAndAssignMouseHandler();
    this.buildAndAssignKeyHandler();
  }

  /**
   * Dummy constructor.
   */
  MusicControllerImpl() {
    // does nothing
  }

  @Override
  public void buildAndAssignKeyHandler() throws InvalidMidiDataException, MidiUnavailableException {
    Map<Integer, Runnable> keyTypes = new HashMap<>();
    Map<Integer, Runnable> keyPresses = new HashMap<>();
    Map<Integer, Runnable> keyReleases = new HashMap<>();

    // plays and pauses music
    keyPresses.put(KeyEvent.VK_SPACE, new PlayPause());
    // move marker right
    keyPresses.put(KeyEvent.VK_RIGHT, new MoveRight());
    // move marker left
    keyPresses.put(KeyEvent.VK_LEFT, new MoveLeft());
    // go to first beat
    keyPresses.put(KeyEvent.VK_HOME, new GoToStart());
    // go to last beat
    keyPresses.put(KeyEvent.VK_END, new GoToEnd());
    // increase tempo
    keyPresses.put(KeyEvent.VK_F, new IncreaseTempo());
    // decrease tempo
    keyPresses.put(KeyEvent.VK_S, new DecreaseTempo());
    // enter and exit practice mode. (performing any other command while in practice mode
    // exits practice mode.
    keyPresses.put(KeyEvent.VK_P, new SetPracticeMode());
    // Adds a start repeater
    keyPresses.put(KeyEvent.VK_B, new SetStartRepeat());
    // Adds a end repeater
    keyPresses.put(KeyEvent.VK_E, new SetEndRepeat());
    // removed all repeaters and varied endings
    keyPresses.put(KeyEvent.VK_X, new CancelRepeats());
    // adds a varied ending
    keyPresses.put(KeyEvent.VK_M, new SetMidRepeater());

    KeyHandler keyHandler = new KeyHandler();
    keyHandler.setKeyTypeMap(keyTypes);
    keyHandler.setKeyPressedMap(keyPresses);
    keyHandler.setKeyReleaseMap(keyReleases);

    view.setKeyListener(keyHandler);
  }

  /**
   * Sets the practice mode of mouseHandler.
   * @param mode the mode.
   */
  private void setPractceMode(boolean mode) {
    mouseHandler.setPractice(mode);
  }

  @Override
  public void buildAndAssignMouseHandler() {
    MouseHandler listener = new MouseHandler();
    listener.setNotePlacer(new NotePlacer());
    listener.setCount(0);
    listener.setRunnable(new BeatCounter());
    this.mouseHandler = listener;
    view.setMouseListener(listener);
  }

  /**
   * Runnable class that plays or pauses program, depending on if it was playing or not
   * when runnable is run. This should be associated with the space press.
   */
  class PlayPause implements Runnable {
    @Override
    public void run() {
      if (mouseHandler.getPractice()) {
        new SetPracticeMode().run();
      }
      if (view.isPlaying()) {
        try {
          view.pause();
          view.reDrawScene();
        }
        catch (IllegalArgumentException x) {
          x.printStackTrace();
        }
        view.resetFocus();
      }
      else {
        try {
          view.play(mouseHandler.getStartRepeats(), mouseHandler.getMidRepeats(), mouseHandler
                  .getEndRepeats());
          view.reDrawScene();
        }
        catch (IllegalArgumentException | MidiUnavailableException | InvalidMidiDataException e) {
          e.printStackTrace();
        }
      }
    }
  }

  /**
   * Runnable class that moves beat marker to the right. Of course, also moves the sequencer's beat
   * so that if played the music starts one beat over. This should be associated with the right key
   */
  class MoveRight implements Runnable {
    @Override
    public void run() {
      if (mouseHandler.getPractice()) {
        new SetPracticeMode();
      }
      if (!view.isPlaying()) {
        try {
          view.moveRight();
          view.reDrawScene();
        }
        catch (IllegalArgumentException x) {
          x.printStackTrace();
        }
      }
    }
  }

  /**
   * Runnable class that moves beat marker to the left. Of course, also moves the sequencer's beat
   * so that if played the music starts one beat back. This should be associated with the left key
   */
  class MoveLeft implements Runnable {

    @Override
    public void run() {
      if (mouseHandler.getPractice()) {
        new SetPracticeMode();
      }
      if (!view.isPlaying()) {
        try {
          view.moveLeft();
          view.reDrawScene();
        }
        catch (IllegalArgumentException x) {
          x.printStackTrace();
        }
      }
    }
  }

  /**
   * Runnable class that sends model and view back to start. Should be associated with home key.
   */
  class GoToStart implements Runnable {

    @Override
    public void run() {
      if (mouseHandler.getPractice()) {
        new SetPracticeMode();
      }
      if (!view.isPlaying()) {
        view.goToStart();
        model.setCurrentBeat(1);
        view.reDrawScene();
      }
    }
  }

  /**
   * Runnable class that sends model and view to end of piece. Should be associated with end key.
   */
  class GoToEnd implements Runnable {

    @Override
    public void run() {
      if (mouseHandler.getPractice()) {
        new SetPracticeMode();
      }
      if (!view.isPlaying()) {
        view.goToEnd();
        model.setCurrentBeat(model.getLength() + 1);
        view.reDrawScene();
      }
    }
  }

  /**
   * Increases Tempo.
   */
  class IncreaseTempo implements Runnable {

    @Override
    public void run() {
      if (mouseHandler.getPractice()) {
        new SetPracticeMode();
      }
      if (view.isPlaying()) {
        view.increaseTempo();
      }
    }
  }

  /**
   * Decreases Tempo.
   */
  class DecreaseTempo implements Runnable {

    @Override
    public void run() {
      if (mouseHandler.getPractice()) {
        new SetPracticeMode();
      }
      try {
        if (view.isPlaying()) {
          view.decreaseTempo();
        }
      }
      catch (IllegalArgumentException x) {
        x.printStackTrace();
      }
    }
  }

  /**
   * Increments the beat counter. This is so the music editor knows how long the onte need to be
   * that is being placed.
   */
  class IncrementBeatCounter extends TimerTask {

    @Override
    public void run() {
      mouseHandler.setCount(mouseHandler.getCount() + 1);
      try {
        new MoveRight().run();
      }
      catch (IllegalArgumentException x) {
        x.printStackTrace();
      }
    }
  }

  /**
   * Counts the beats to aid in placing notes of various length.
   */
  class BeatCounter implements Runnable{

    int tempo;

    @Override
    public void run() {
      tempo = model.getTempo();
      System.out.print(model.getTempo() + "");
      mouseHandler.setBeat(model.getCurrentBeat());
      mouseHandler.setCount(0);
      timer.schedule(new IncrementBeatCounter(), 0, (int) Math.floor(1000 * (60.0 / tempo)));
    }

  }


  /**
   * NotePlaces class with a runnable method that process coordinates. Provides all the
   * functionality of mouse clicks on a piano keyboard.
   */
  class NotePlacer {

    List<Integer> currentNotes;

    /**
     * Old note placer that adds a note of constant duration.
     * @param x x coordinate
     * @param y y coordinate
     * @throws InvalidMidiDataException midi error
     */
    void run(double x, double y) throws InvalidMidiDataException {
      int index = this.findIndex(x, y);
      if (index >= 24) {
        Note n = NoteFactory.buildNote("MusicNote", model.getCurrentBeat() - 1, model
                .getCurrentBeat() + 1, 1, index, 1);
        view.placeNote(n);
        view.reDrawScene();
      }
    }

    /**
     * Sets the current notes so the handler know the desired keys to press during practice mode.
     */
    void setCurrentNotes() {
      List<Note> notes = model.currentNotes();
      List<Integer> noteIndices = new ArrayList<Integer>();
      for (Note n : notes) {
        noteIndices.add(n.noteIndex());
      }
      this.currentNotes = noteIndices;
    }

    /**
     * Performs a click in practice mode. Will play the note, and proceed if all current notes
     * have been clicked.
     * @param x x coord
     * @param y y coord
     * @throws InvalidMidiDataException midi error
     */
    void practiceModeClick(double x, double y) throws InvalidMidiDataException {
      if (this.currentNotes == null) {
        this.setCurrentNotes();
      }
      int index = this.findIndex(x, y) - 23;
      System.out.print("index: " + index + "\n");
      currentNotes.remove(new Integer(index));
      Sequence s = new Sequence(Sequence.PPQ, 1);
      Track t = s.createTrack();
      MidiMessage startMessage = new ShortMessage(ShortMessage.NOTE_ON, 0, index + 24,
              64);
      MidiMessage stopMessage = new ShortMessage(ShortMessage.NOTE_OFF, 0, index + 24, 64);
      MidiEvent startEvent = new MidiEvent(startMessage, 1);
      MidiEvent stopEvent = new MidiEvent(stopMessage, 2);
      t.add(startEvent);
      t.add(stopEvent);
      System.out.print("Contents: " + currentNotes.toString() + "\n");
      if (currentNotes.isEmpty()) {
        new MoveRight().run();
        while (model.currentNotes().isEmpty()) {
          new MoveRight().run();
        }
        this.currentNotes = null;
      }
    }

    /**
     * Special run method that adds a note based off coordinates and a given beat and duration.
     * @param x x coord
     * @param y y coord
     * @param beat beat to place on
     * @param duration duration of desired note
     * @throws InvalidMidiDataException midi error
     */
    void runSpecial(double x, double y, int beat, int duration) throws InvalidMidiDataException {
      timer.cancel();
      timer = new Timer();
      System.out.print("PLACING NOTE SPECIAL");
      int index = this.findIndex(x, y);
      if (index >= 24) {
        Note n = NoteFactory.buildNote("MusicNote", beat - 1, beat + duration - 1, 1, index,
                1);
        view.placeNote(n);
        view.reDrawScene();
      }
      mouseHandler.setCount(0);
    }

    /**
     * Finds the note index of a note given mouse coordinates.
     * @param x x coord
     * @param y y coord
     * @return
     */
    int findIndex(double x, double y) {
      if (x <= 40 || x >= 1440 || y <= 40 || y >= 240) {
        return 0;
      }
      else if (y >= 140) {
        double val = ((x - 40) / 140);
        int octave = (int) Math.floor(val);
        int whitePitch = (int) Math.floor((val - octave) * 7);
        int pitchActual;
        switch (whitePitch) {
          case 0:
            pitchActual = 0;
            break;
          case 1:
            pitchActual = 2;
            break;
          case 2:
            pitchActual = 4;
            break;
          case 3:
            pitchActual = 5;
            break;
          case 4:
            pitchActual = 7;
            break;
          case 5:
            pitchActual = 9;
            break;
          default:
            pitchActual = 11;
        }
        return octave * 12 + pitchActual + 24;
      }
      else {
        double val = ((x - 40) / 140);
        int octave = (int) Math.floor(val);
        double pitch = (val - octave) * 140;
        int pitchIndex = -1;
        if (pitch < 15)  {
          pitchIndex = 0;
        }
        else if (pitch < 25) {
          pitchIndex = 1;
        }
        else if (pitch < 35) {
          pitchIndex = 2;
        }
        else if (pitch < 45) {
          pitchIndex = 3;
        }
        else if (pitch < 60) {
          pitchIndex = 4;
        }
        else if (pitch < 75) {
          pitchIndex = 5;
        }
        else if (pitch < 85) {
          pitchIndex = 6;
        }
        else if (pitch < 95) {
          pitchIndex = 7;
        }
        else if (pitch < 105) {
          pitchIndex = 8;
        }
        else if (pitch < 115) {
          pitchIndex = 9;
        }
        else if (pitch < 125) {
          pitchIndex = 10;
        }
        else {
          pitchIndex = 11;
        }
        return octave * 12 + pitchIndex + 24;
      }
    }
  }

  /**
   * Sets practice mode to either true or false given current mode state.
   */
  class SetPracticeMode implements Runnable {

    @Override
    public void run() {
      if (mouseHandler.getPractice()) {
        setPractceMode(false);
      }
      else {
        setPractceMode(true);

      }
    }
  }

  /**
   * Adds a start repeater (this one ---> |:) AS LONG as there is an end repeat ahead of it.
   */
  class SetStartRepeat implements Runnable {

    @Override
    public void run() {
      if (!view.isPlaying() && !mouseHandler.getPractice()) {
        if (!mouseHandler.getEndRepeats().isEmpty() &&
                mouseHandler.getEndRepeats().size() > mouseHandler.getStartRepeats().size() &&
                model.getCurrentBeat() < mouseHandler.getEndRepeats().get(mouseHandler
                        .getEndRepeats().size() - 1)) {
          mouseHandler.getStartRepeats().add(model.getCurrentBeat());
        }
      }
    }
  }

  /**
   * Adds an end repeater (this one ---> :|).
   */
  class SetEndRepeat implements Runnable {

    @Override
    public void run() {
      if (!view.isPlaying() && !mouseHandler.getPractice()) {
        mouseHandler.getEndRepeats().add(model.getCurrentBeat());
      }
    }
  }

  /**
   * Cancels all start, end repeater and varied endings.
   */
  class CancelRepeats implements Runnable {

    @Override
    public void run() {
      if (!view.isPlaying() && !mouseHandler.getPractice()) {
        mouseHandler.resetEndRepeats();
        mouseHandler.resetMidRepeats();
        mouseHandler.resetStartRepeats();
      }
    }
  }

  /**
   * Adds varied ending AS LONG AS there is and end repeater ahead of it.
   */
  class SetMidRepeater implements Runnable {
    @Override
    public void run() {
      if (!view.isPlaying() && !mouseHandler.getPractice()) {
        if (!mouseHandler.getEndRepeats().isEmpty() &&
                mouseHandler.getEndRepeats().size() > mouseHandler.getMidRepeats().size()) {
          boolean validMid = false;
          for (int i : mouseHandler.getEndRepeats()) {
            if (model.getCurrentBeat() < i) {
              validMid = true;
            }
          }
          if (validMid) {
            System.out.print("adding varied ending");
            mouseHandler.getMidRepeats().add(model.getCurrentBeat());
          }
        }
      }
    }
  }
}
