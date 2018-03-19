package cs3500.music.view;

import cs3500.music.model.MusicEditorModel;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;

/**
 * Created by sahaj on 6/23/2017.
 */

/**
 * Console view for music editor. Only feature is outputing to console a string form of the
 * music editor state.
 */
public class ConsoleView implements IView {

  private MusicEditorModel model;

  /**
   * Simple constructor, just takes and assigns the model.
   * @param model the model
   */
  public ConsoleView(MusicEditorModel model) {
    this.model = model;
  }

  @Override
  public void activate() throws InvalidMidiDataException, MidiUnavailableException {
    System.out.print(model.getMusicState());
  }
}
