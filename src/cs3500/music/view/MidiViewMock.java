package cs3500.music.view;

import cs3500.music.model.MusicEditorModel;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiUnavailableException;

/**
 * Created by sahaj on 6/14/2017.
 */
public class MidiViewMock extends MidiViewImpl {



    public MidiViewMock(MusicEditorModel model, boolean foo) throws MidiUnavailableException, InvalidMidiDataException {
        super(model);
    }

    // TODO make messages something understandable
    @Override
    protected void addToTrack(MidiEvent start, MidiEvent stop) {
        this.log.append(start);
        this.log.append(stop);
    }

    @Override
    public String log() {
        return this.log.toString();
    }


}
