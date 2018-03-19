package cs3500.music.view;

import cs3500.music.model.Note;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.util.List;

/**
 * Created by sahaj on 6/18/2017.
 */

/**
 * Special IView that supports functionality for more versatile, audiovisual view. Should
 * be able to play, pause, move right, left, go to end and start, and some other things to
 * support the abilities of a music editor.
 */
public interface IViewMain extends IView {

  /**
   * Resets focus of view when something has changed.
   */
  void resetFocus();

  /**
   * Plays music from current beat.
   * @throws InvalidMidiDataException midi error
   * @throws MidiUnavailableException midi error
   */
  void play(List<Integer> starts, List<Integer> mids, List<Integer> ends) throws
          InvalidMidiDataException,
          MidiUnavailableException;

  /**
   * Pauses music at current beat.
   */
  void pause();

  /**
   * Redraws scene when somethind has changed.
   */
  void reDrawScene();

  /**
   * Sets key listener to given.
   * @param listener key listener
   */
  void setKeyListener(KeyListener listener);

  /**
   * Sets mouse listener to given.
   * @param listener mouse listener
   */
  void setMouseListener(MouseListener listener);

  /**
   * Checks if music is currently playing.
   * @return true if playing, falst otherwise.
   */
  boolean isPlaying();

  /**
   * Moves beat one to right.
   */
  void moveRight();

  /**
   * Moves beat one to left.
   */
  void moveLeft();

  /**
   * Sets current beat to the start of piece.
   */
  void goToStart();

  /**
   * Sets current beat to the end of piece.
   */
  void goToEnd();

  /**
   * Increase tempo by 10
   */
  void increaseTempo();

  /**
   * Decrease tempo by 10
   */
  void decreaseTempo();

  /**
   * Places given note at current beat marker.
   * @param n note added
   * @throws InvalidMidiDataException midi error
   */
  void placeNote(Note n) throws InvalidMidiDataException;

}
