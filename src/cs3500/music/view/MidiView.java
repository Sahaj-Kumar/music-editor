package cs3500.music.view;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;

/**
 * Created by sahaj on 6/12/2017.
 */

// TODO Documentation

/**
 * View implementing generic IView. This view is audial, and has the ability to
 * play music, and if necessary log current state or processes.
 */
public interface MidiView extends IView {

  /**
   * Plays the music, presumably from the start.
   * @throws InvalidMidiDataException error if problem with midi processes
   * @throws MidiUnavailableException error if problem with midi processes
   */
  void play() throws InvalidMidiDataException, MidiUnavailableException;

  void pause();

  void moveRight();

  void moveLeft();

  void goToStart();

  long getSequenceBeat();

  /**
   * Depending on implementation, returns a log add added notes to track.
   * Otherswise would return an empty string.
   * @return log of notes or empty string.
   */
  String log();
}


