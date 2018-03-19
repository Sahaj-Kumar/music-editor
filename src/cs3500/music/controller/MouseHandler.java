package cs3500.music.controller;

import javax.sound.midi.InvalidMidiDataException;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

/**
 * Created by sahaj on 6/20/2017.
 */

/**
 * Represents a mouse handler for music editor. Controller should define its duties such
 * that it can support all desired commands involving key presses. As of now, that means
 * pressing keys on a piano and add the appropriate notes.
 */
public class MouseHandler implements MouseListener {

  // Has dummy class called NotePlacer. The MouseHandler will not know what it does
  // until the controller gives it.
  private MusicControllerImpl.NotePlacer notePlacer;
  // has dummy runnable beat counter.  The MouseHandler will not know what it does
  // until the controller gives it. They are named this way for convenience.
  private Runnable beatCounter;

  // several parameters that are set to default values. The controller will manipulate them
  // however it sees fit. They were named for convenience.
  private int beat;
  private int count;
  private int tempo;
  private boolean practice;
  private List<Integer> startBeats;
  private List<Integer> endBeats;
  private List<Integer> midBeats;


  /**
   * Empty constructor for instantiating mouse handler and default values.
   */
  public MouseHandler() {
    this.count = 0;
    this.practice = false;
    this.startBeats = new ArrayList<Integer>();
    this.endBeats = new ArrayList<Integer>();
    this.midBeats = new ArrayList<Integer>();
  }

  /**
   * Sets the NotePlacer to the given.
   * @param r the note placer.
   */
  void setNotePlacer(MusicControllerImpl.NotePlacer r) {
    this.notePlacer = r;
  }

  /**
   * Sets the beat counter runnable to the given.
   * @param r the runnable.
   */
  void setRunnable(Runnable r) {
    this.beatCounter = r;
  }

  /**
   * Sets the beat to the given.
   * @param beat the beat
   */
  void setBeat(int beat) {
    this.beat = beat;
  }

  /**
   * Sets the count to the given value.
   * @param count the count
   */
  void setCount(int count) {
    this.count = count;
  }

  /**
   * Sets tempo to the given.
   * @param tempo the tempo
   */
  void setTempo(int tempo) {
    this.tempo = tempo;
  }

  /**
   * Gets the count.
   * @return the count
   */
  int getCount() {
    return this.count;
  }

  /**
   * Sets the practice mode to given.
   * @param mode the mode
   */
  void setPractice(boolean mode) {
    this.practice = mode;
    System.out.print("practice mode activated");
  }

  /**
   * Resets the startRepeats (look like this |:)
   */
  void resetStartRepeats() {
    this.startBeats = new ArrayList<Integer>();
  }

  /**
   * Resets the end repeats (look list thie :|)
   */
  void resetEndRepeats() {
    this.endBeats = new ArrayList<Integer>();
  }

  /**
   * Resets the mid repeats. (These are varied endings)
   */
  void resetMidRepeats() {
    this.endBeats = new ArrayList<Integer>();
  }

  /**
   * Gets the list of start beats.
   * @return start beats
   */
  List<Integer> getStartRepeats() {
    return this.startBeats;
  }

  /**
   * Gets the list of end repeats.
   * @return end repeats
   */
  List<Integer> getEndRepeats() {
    return this.endBeats;
  }

  /**
   * Gets the list of mid repeats. (varied endings)
   * @return mid repeats
   */
  List<Integer> getMidRepeats() {
    return this.midBeats;
  }

  /**
   * Gets the state of practice mode.
   * @return practice mode state
   */
  boolean getPractice() {
    return this.practice;
  }

  // The mouse click is only active when the practice mode boolean is on. Otherwise click do
  // nothing at all.
  @Override
  public void mouseClicked(MouseEvent e) {
    if (practice) {
      double xPos = e.getX();
      double yPos = e.getY();
      try {
        this.notePlacer.practiceModeClick(xPos, yPos);
      } catch (InvalidMidiDataException e1) {
        e1.printStackTrace();
      }
    }
  }

  // The next two handled mouse presses, each one using one of the two assigned runnables.
  @Override
  public void mousePressed(MouseEvent e) {
    System.out.print("holding mouse down");
    if (!practice) {
      this.beatCounter.run();
    }
  }

  @Override
  public void mouseReleased(MouseEvent e) {
    if (!practice) {
      double xPos = e.getX();
      double yPos = e.getY();
      try {
        this.notePlacer.runSpecial(xPos, yPos, this.beat, this.count);
      } catch (InvalidMidiDataException e1) {
        e1.printStackTrace();
      }
    }
  }

  @Override
  public void mouseEntered(MouseEvent e) {
    // does nothing
  }

  @Override
  public void mouseExited(MouseEvent e) {
    // does nothing
  }
}
