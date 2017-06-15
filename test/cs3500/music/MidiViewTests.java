package cs3500.music;

import cs3500.music.model.EditorModel;
import cs3500.music.model.MusicEditorModel;
import cs3500.music.util.CompositionBuilder;
import cs3500.music.util.MusicEditorBuilder;
import cs3500.music.util.MusicReader;
import cs3500.music.view.MidiView;
import cs3500.music.view.MidiViewMock;
import org.junit.Test;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;
import java.io.FileNotFoundException;
import java.io.FileReader;

import static junit.framework.TestCase.assertEquals;

/**
 * Created by sahaj on 6/14/2017.
 */
public class MidiViewTests {

    Readable maryFile;
    Readable mystery2File;
    CompositionBuilder<MusicEditorModel> builder;
    MusicEditorModel marySong;
    MidiView view;

    private void initCond() throws InvalidMidiDataException, MidiUnavailableException, FileNotFoundException {
        maryFile = new FileReader("mary-little-lamb.txt");
        mystery2File = new FileReader("mystery-2.txt");
        builder = new MusicEditorBuilder();
    }

    @Test
    public void testMary() throws MidiUnavailableException, InvalidMidiDataException, FileNotFoundException {
        this.initCond();
        marySong = MusicReader.parseFile(maryFile, builder);
        view = new MidiViewMock(marySong, true);
        assertEquals(view.log(), "");
    }
}
