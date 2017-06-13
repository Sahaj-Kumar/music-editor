package cs3500.music;

import cs3500.music.model.EditorModel;
import cs3500.music.model.MusicEditorModel;
import cs3500.music.model.MusicNote;
import cs3500.music.model.Pitch;
import cs3500.music.util.CompositionBuilder;
import cs3500.music.util.MusicReader;
import cs3500.music.view.GuiViewFrame;
import cs3500.music.view.MidiViewImpl;

import java.io.FileReader;
import java.io.IOException;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;


public class MusicEditor {
  public static void main(String[] args) throws IOException, InvalidMidiDataException, MidiUnavailableException {

    // midi view of music editor
    MidiViewImpl midiView = new MidiViewImpl();

    // music editor model
    MusicEditorModel model = new EditorModel();
    model.placeNote(new MusicNote(Pitch.C, 4, 1, 4));
    model.placeNote(new MusicNote(Pitch.Cx, 4, 5, 4));
    model.placeNote(new MusicNote(Pitch.C, 5, 11, 4));
    model.placeNote(new MusicNote(Pitch.D, 5,11, 4));
    model.placeNote(new MusicNote(Pitch.Ax, 6, 20, 8));
    model.setCurrentBeat(12);

    // visual view of music editor
    GuiViewFrame view = new GuiViewFrame(model);

    view.initialize();
  }
}
