package cs3500.music.util;

import com.sun.org.apache.regexp.internal.RE;
import cs3500.music.MusicEditor;
import cs3500.music.model.MusicEditorModel;
import cs3500.music.view.GuiViewFrame;
import cs3500.music.view.IView;
import cs3500.music.view.MidiViewImpl;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;
import javax.swing.text.View;
import java.io.FileNotFoundException;
import java.io.FileReader;

/**
 * Created by sahaj on 6/14/2017.
 */
public class ViewFactory {

    public static IView generateView(MusicEditorModel model, String type) throws FileNotFoundException, InvalidMidiDataException, MidiUnavailableException {

        IView view;

        switch (type) {
            case "visual":
                view = new GuiViewFrame(model);
                break;
            case "midi":
                view = new MidiViewImpl(model);
                break;
            default:
                throw new IllegalArgumentException("invalid view type");
        }
        return view;
    }
}
