package cs3500.music.controller;

/**
 * Created by sahaj on 6/18/2017.
 */

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;

/**
 * Represents a model for a controller for the music editor. Should consrtuct a
 * keyhandler and mousehandler that determine what to do based off of various
 * different key presses and mouse clicks.
 */
public interface MusicController {

  /**
   * Build and assigns a KeyHandler to the view. This should support all the functionality
   * desired involving key presses.
   * @throws InvalidMidiDataException error during midi processes
   * @throws MidiUnavailableException error during midi processes
   */
  void buildAndAssignKeyHandler() throws InvalidMidiDataException, MidiUnavailableException;

  /**
   * Builds and assigns a MouseHandler to the view, this should support all the functionality
   * desired involving mouse clicks.
   */
  void buildAndAssignMouseHandler();

}
