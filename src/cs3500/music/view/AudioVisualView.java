package cs3500.music.view;

import cs3500.music.model.MusicEditorModel;
import cs3500.music.model.Note;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;
import javax.swing.JPanel;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.util.*;

/**
 * Created by sahaj on 6/18/2017.
 */

/**
 * Composite, visual and audio view. Not only can display and music, but has mode controls like
 * play, pause, right, left, home, end, and placing notes. This is all however dependant on what
 * the controller dictates.
 */
public class AudioVisualView extends JPanel implements IViewMain {

  private MusicEditorModel model;

  private GuiView visual;
  private MidiView midi;
  private boolean playing;
  // for moving beat marker during play
  private Timer timer;

  /**
   * Constructs composite view. Uses model to make midi and visual view.
   * @param model the model
   * @throws InvalidMidiDataException midi error
   * @throws MidiUnavailableException midi error
   */
  public AudioVisualView(MusicEditorModel model)
          throws InvalidMidiDataException, MidiUnavailableException {
    this.model = model;
    this.visual = new GuiViewFrame(model);
    this.midi = new MidiViewImpl(model);
    this.playing = false;
    this.timer = new Timer();
  }

  @Override
  public void activate() throws InvalidMidiDataException, MidiUnavailableException {
    this.visual.initialize();
    this.requestFocusInWindow();
  }

  /**
   * Timer task class that quickly updates the beat marker whenever the sequencer surpasses
   * it relative to its current beat. Used when music is playing.
   */
  class PlayMusic extends TimerTask {

    List<Integer> starts = new ArrayList<Integer>();
    List<Integer> mids = new ArrayList<Integer>();
    List<Integer> midGrave = new ArrayList<Integer>();
    List<Integer> ends = new ArrayList<Integer>();
    List<Integer> endGrave = new ArrayList<Integer>();
    boolean midH = false;

    void setStarts(List<Integer> starts) {
      this.starts.addAll(starts);
      Collections.sort(this.starts);
    }

    void setMids(List<Integer> mids) {
      this.mids.addAll(mids);
      Collections.sort(this.mids);
    }

    void setEnds(List<Integer> ends) {
      for (int i : ends) {
        if (model.getCurrentBeat() < i) {
          this.ends.add(i);
        }
      }
      Collections.sort(this.ends);
    }

    @Override
    public void run() {
      int musicBeat = (int) Math.floor(midi.getSequenceBeat());
      boolean moved = false;
      if (model.getCurrentBeat() < musicBeat) {
        visual.moveRight();
        moved = true;
      }
      if (!this.mids.isEmpty() && this.mids.get(0) == model.getCurrentBeat() && !midH) {
        midH = true;
      }
      else if (!this.mids.isEmpty() && this.midGrave.contains(model.getCurrentBeat()) && midH) {
        int beat = 1;
        for (int i : this.endGrave) {
          if (i > beat && i > this.mids.get(0)) {
            beat = i;
          }
        }
        while (model.getCurrentBeat() < beat) {
          moveRight();
        }
        midH = false;
        this.midGrave.add(this.mids.get(0));
        this.mids.remove(0);
      }
      else if (!this.mids.isEmpty() && this.mids.get(0) == model.getCurrentBeat() && midH &&
              moved) {
        int beatJump = model.getLength();
        for (int i : this.endGrave) {
          if (i < beatJump && model.getCurrentBeat() < i) {
            beatJump = i;
          }
        }
        while (model.getCurrentBeat() < beatJump) {
          moveRight();
        }
        midH = false;
        this.midGrave.add(this.mids.get(0));
        this.mids.remove(0);
      }

      if (!this.ends.isEmpty() && this.ends.get(0) == model.getCurrentBeat()) {
        this.endGrave.add(this.ends.get(0));
        this.ends.remove(0);
        int lastBeat = 1;
        for (int i : this.starts) {
          if (i > lastBeat && i < model.getCurrentBeat()) {
            lastBeat = i;
          }
        }
        while (model.getCurrentBeat() > lastBeat) {
          moveLeft();
        }
      }
      /*
      else if (!this.mids.isEmpty() && this.mids.get(0) == model.getCurrentBeat() && !midH) {
        midH = true;
      }
      else if (!this.mids.isEmpty() && this.mids.get(0) == model.getCurrentBeat() && midH) {
        int beatJump = model.getLength();
        for (int i : this.endGrave) {
          if (i < beatJump && model.getCurrentBeat() > i) {
            beatJump = i;
          }
        }
        while (model.getCurrentBeat() < beatJump) {
          moveRight();
        }
        midH = false;
      }
      */
      visual.rePaintScene();
    }
  }

  @Override
  public void resetFocus() {
    this.setFocusable(true);
    this.requestFocus();
  }

  @Override
  public boolean isPlaying() {
    return this.playing;
  }

  @Override
  public void moveRight() {
    this.midi.moveRight();
    this.visual.moveRight();
  }

  @Override
  public void moveLeft() {
    this.midi.moveLeft();
    this.visual.moveLeft();
  }

  @Override
  public void goToStart() {
    try {
      this.visual.goToStart();
      this.midi.goToStart();
    }
    catch (IllegalArgumentException x) {
      x.printStackTrace();
    }
  }

  @Override
  public void goToEnd() {
    try {
      this.visual.goToEnd();
      this.midi.goToEnd();
    }
    catch (IllegalArgumentException x) {
      x.printStackTrace();
    }
  }

  @Override
  public void increaseTempo() {
    this.model.setTempo(this.model.getTempo() + 10);
    this.midi.increaseTempo();
  }

  @Override
  public void decreaseTempo() {
    this.model.setTempo(this.model.getTempo() - 10);
    this.midi.decreaseTempo();
  }

  @Override
  public void placeNote(Note n)  {
    try {
      this.visual.placeNote(n);
      this.midi.placeNote(n);
    }
    catch (InvalidMidiDataException x) {
      x.printStackTrace();
    }
  }


  @Override
  public void play(List<Integer> starts, List<Integer> mids, List<Integer> ends) throws
          InvalidMidiDataException,
          MidiUnavailableException {
    PlayMusic playMusic = new PlayMusic();
    playMusic.setStarts(starts);
    playMusic.setMids(mids);
    playMusic.setEnds(ends);
    timer.schedule(playMusic, 0, model.getTempo() /  10);
    midi.play();
    this.playing = true;
  }


  @Override
  public void pause() {
    this.playing = false;
    timer.cancel();
    timer = new Timer();
    midi.pause();
  }

  @Override
  public void reDrawScene() {
    this.visual.rePaintScene();
  }

  @Override
  public void setKeyListener(KeyListener listener) {
    this.visual.setKeyListener(listener);
  }

  @Override
  public void setMouseListener(MouseListener listener) {
    this.visual.setMouseListener(listener);
  }
}
