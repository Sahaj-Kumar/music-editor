package cs3500.music;

import cs3500.music.model.MusicEditorModel;
import cs3500.music.util.CompositionBuilder;
import cs3500.music.util.MusicEditorBuilder;
import cs3500.music.util.MusicReader;
import cs3500.music.view.GuiView;
import cs3500.music.view.GuiViewFrame;
import org.junit.Test;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;
import java.io.FileNotFoundException;
import java.io.FileReader;

import static junit.framework.TestCase.assertEquals;

/**
 * Created by sahaj on 6/15/2017.
 */
public class GuiPanelTests {

  private Readable maryFile;
  private CompositionBuilder<MusicEditorModel> builder;

  private void initCond() throws InvalidMidiDataException,
          MidiUnavailableException, FileNotFoundException {
    maryFile = new FileReader("mary-little-lamb.txt");
    Readable mystery2File = new FileReader("mystery-2.txt");
    builder = new MusicEditorBuilder();
  }


  // Tests that ensures left and right functionalit work on visual view
  @Test
  public void testMary() throws MidiUnavailableException,
          InvalidMidiDataException, FileNotFoundException {
    this.initCond();
    MusicEditorModel song = MusicReader.parseFile(maryFile, builder);
    GuiView view = new GuiViewFrame(song);
    assertEquals(song.getCurrentBeat(), 1);
    assertEquals(song.currentNotes().size(), 2);
    view.moveLeft();
    assertEquals(song.getCurrentBeat(), 1);
    view.moveRight();
    assertEquals(song.getCurrentBeat(), 2);
    view.moveRight();
    view.moveRight();
    view.moveRight();
    view.moveRight();
    view.moveRight();
    view.moveRight();
    assertEquals(song.getCurrentBeat(), 8);
    assertEquals(song.currentNotes().size(), 1);
  }
}
