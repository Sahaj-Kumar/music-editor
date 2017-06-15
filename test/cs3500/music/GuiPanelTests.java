package cs3500.music;

import cs3500.music.model.MusicEditorModel;
import cs3500.music.util.CompositionBuilder;
import cs3500.music.util.MusicEditorBuilder;
import cs3500.music.util.MusicReader;
import cs3500.music.view.*;
import org.junit.Test;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;
import java.awt.*;
import java.io.FileNotFoundException;
import java.io.FileReader;

import static junit.framework.TestCase.assertEquals;

/**
 * Created by sahaj on 6/15/2017.
 */
public class GuiPanelTests {

    private Readable maryFile;
    private Readable mystery2File;
    private CompositionBuilder<MusicEditorModel> builder;
    private MusicEditorModel song;
    GuiView guiView;
    MockPianoPanel pianoView;

    private void initCond() throws InvalidMidiDataException, MidiUnavailableException, FileNotFoundException {
        maryFile = new FileReader("mary-little-lamb.txt");
        mystery2File = new FileReader("mystery-2.txt");
        builder = new MusicEditorBuilder();
    }

    /*
    @Test
    public void testMary() throws MidiUnavailableException, InvalidMidiDataException, FileNotFoundException {
        this.initCond();
        song = MusicReader.parseFile(maryFile, builder);
        pianoView = new MockPianoPanel(song);
        pianoView.paintComponent(new Graphics());
        GuiViewFrame g = new GuiViewFrame(song);
        g.getContentPane().add(pianoView);
        g.pack();

        g.initialize();
        assertEquals(this.pianoView.log(), "");
    }
    */
}
