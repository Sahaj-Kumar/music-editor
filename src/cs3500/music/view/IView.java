package cs3500.music.view;

/**
 * Created by sahaj on 6/14/2017.
 */

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;

/**
 * Interface for generic view. It can be any type of view: visual, audial, etc.
 * It only has one requirement, and that is the ability to "activate the view".
 * This interface's serves the purposes of unifying views to provide convenience
 * to the view factory.
 */
public interface IView {

  /**
   * "Activates" the current view. This can mean many things, depending one
   * what type of view. If visual, this probably suggests initializing the
   * scene. If audial, it probably suggests playing the music, and so on.
   */
  void activate() throws InvalidMidiDataException, MidiUnavailableException;
}
