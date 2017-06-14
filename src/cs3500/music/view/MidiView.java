package cs3500.music.view;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;

/**
 * Created by sahaj on 6/12/2017.
 */
public interface MidiView {

    void play() throws InvalidMidiDataException, MidiUnavailableException;

    void pause();

    void rewind();

    void fastForward();
}
