package cs3500.music.view;

import cs3500.music.model.Note;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;

/**
 * Created by sahaj on 6/12/2017.
 */

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
  void play() throws InvalidMidiDataException,
          MidiUnavailableException;

  /**
   * Pauses music.
   */
  void pause();

  /**
   * Moves Right.
   */
  void moveRight();

  /**
   * Moves left.
   */
  void moveLeft();

  /**
   * Go to start of music.
   */
  void goToStart();

  /**
   * Goes to end of music.
   */
  void goToEnd();

  /**
   * Increases tempo by 10bpm
   */
  void increaseTempo();

  /**
   * Decreases tempo by 10bpm
   */
  void decreaseTempo();

  /**
   * Returns beat of sequencer.
   * @return beat of sequencer.
   */
  long getSequenceBeat();

  /**
   * Places note in sequencer.
   * @param n not being placed.
   * @throws InvalidMidiDataException midi error
   */
  void placeNote(Note n) throws InvalidMidiDataException;

  /**
   * Depending on implementation, returns a log add added notes to track.
   * Otherswise would return an empty string.
   * @return log of notes or empty string.
   */
  String log();
}


