package cs3500.music;

import cs3500.music.model.MusicEditorModel;
import cs3500.music.util.CompositionBuilder;
import cs3500.music.util.MusicEditorBuilder;
import cs3500.music.util.MusicReader;
import cs3500.music.util.ViewFactory;

import java.io.FileReader;
import java.io.IOException;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;

/**
 * What you all have been waiting for. The class with main method that runs the program.
 * Main method takes a text file and view type, and return that view of a model constructed
 * on the paramters of the text file.
 */
public class MusicEditor {

  /**
   * Champion main method that takes the arguments to build a particular view of a model
   * based of a given text file.
   * @param args list of arguments that determine the text file for building the model,
   *             and the type of view to returned.
   * @throws IOException error if problem during input/output processes
   * @throws InvalidMidiDataException error if problem during midi processes
   * @throws MidiUnavailableException error if problem during midi processes
   */
  public static void main(String[] args)
          throws IOException, InvalidMidiDataException, MidiUnavailableException {

    String fileName = args[0];
    String type = args[1];

    Readable readeableFile = new FileReader(fileName);
    CompositionBuilder<MusicEditorModel> compBuilder = new MusicEditorBuilder();
    MusicEditorModel model = MusicReader.parseFile(readeableFile, compBuilder);

    ViewFactory.generateView(model, type).activate();
  }
}
