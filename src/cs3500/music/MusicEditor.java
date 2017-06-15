package cs3500.music;

import cs3500.music.model.EditorModel;
import cs3500.music.model.MusicEditorModel;
import cs3500.music.model.MusicNote;
import cs3500.music.model.Pitch;
import cs3500.music.util.CompositionBuilder;
import cs3500.music.util.MusicEditorBuilder;
import cs3500.music.util.MusicReader;
import cs3500.music.util.ViewFactory;
import cs3500.music.view.*;

import java.io.FileReader;
import java.io.IOException;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;


public class MusicEditor {
  public static void main(String[] args) throws IOException, InvalidMidiDataException, MidiUnavailableException {

    String fileName = args[0];
    String type = args[1];

    Readable readeableFile = new FileReader(fileName);
    CompositionBuilder<MusicEditorModel> compBuilder = new MusicEditorBuilder();
    MusicEditorModel model = MusicReader.parseFile(readeableFile, compBuilder);

    if (type.equals("console")) {
      System.out.print(model.getMusicState());
      return;
    }
    else {
      ViewFactory.generateView(model, type).activate();
    }
  }
}
