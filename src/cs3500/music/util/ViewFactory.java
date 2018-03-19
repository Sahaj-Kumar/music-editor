package cs3500.music.util;

import cs3500.music.controller.MusicController;
import cs3500.music.controller.MusicControllerImpl;
import cs3500.music.model.MusicEditorModel;
import cs3500.music.view.AudioVisualView;
import cs3500.music.view.ConsoleView;
import cs3500.music.view.GuiViewFrame;
import cs3500.music.view.IView;
import cs3500.music.view.IViewMain;
import cs3500.music.view.MidiViewImpl;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;

/**
 * Created by sahaj on 6/14/2017.
 */

/**
 * A factory for building views. As of now, currently can build either a visual, or
 * midi (music) view.
 */
public class ViewFactory {

  /**
   * Builds a view, given the model and name of type of view.
   * @param model model view is based off of
   * @param type type of view to be built
   * @return model's view of given type
   * @throws InvalidMidiDataException error during midi constructing process
   * @throws MidiUnavailableException error during midi constructing process
   */
  public static IView generateView(MusicEditorModel model, String type)
          throws InvalidMidiDataException, MidiUnavailableException {
    IView view;
    switch (type) {
      case "console":
        view = new ConsoleView(model);
        break;
      case "visual":
        view = new GuiViewFrame(model);
        break;
      case "midi":
        view = new MidiViewImpl(model);
        break;
      case "audiovisual":
        view = new AudioVisualView(model);
        MusicController c = new MusicControllerImpl(model, (IViewMain) view);
        break;
      default:
        throw new IllegalArgumentException("invalid view type");
    }
    return view;
  }
}
