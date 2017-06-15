package cs3500.music.view;

import cs3500.music.model.MusicEditorModel;
import cs3500.music.model.Note;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiUnavailableException;

/**
 * Created by sahaj on 6/14/2017.
 */
public class MidiViewMock extends MidiViewImpl {



    public MidiViewMock(MusicEditorModel model) throws MidiUnavailableException, InvalidMidiDataException {
        super(model);
    }

    // TODO make messages something understandable
    @Override
    protected void addToTrack(Note n) {
        this.log.append("Start: ").append(n.getPitch())
                .append(n.getOctave()).append(" at beat ")
                .append(n.getStartPoint())
                .append("|");
        this.log.append("Stop: ").append(n.getPitch())
                .append(n.getOctave()).append(" at beat ")
                .append(n.getStartPoint() + n.getDuration() - 1)
                .append("\n");
    }

    @Override
    public String log() {
        return this.log.toString();
    }


}
