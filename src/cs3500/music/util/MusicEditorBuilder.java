package cs3500.music.util;

import cs3500.music.model.EditorModel;
import cs3500.music.model.MusicEditorModel;
import cs3500.music.model.MusicNote;
import cs3500.music.model.Pitch;

/**
 * Created by sahaj on 6/13/2017.
 */
public class MusicEditorBuilder implements CompositionBuilder<MusicEditorModel> {

    MusicEditorModel model;

    public MusicEditorBuilder() {
        this.model = new EditorModel();
    }

    @Override
    public MusicEditorModel build() {
        return this.model;
    }

    @Override
    public CompositionBuilder<MusicEditorModel> setTempo(int tempo) {
        int newTempo = (int) Math.floor((tempo / 100000.0) * 500);
        model.setTempo(newTempo);
        return this;
    }

    // TODO idealize this
    @Override
    public CompositionBuilder<MusicEditorModel> addNote(int start, int end, int instrument, int pitch, int volume) {
        model.placeNote(
                new MusicNote(
                        Pitch.pitchMap.get(Math.floorMod(pitch, 12) + 1),
                        pitch / 12 + 1,
                        start + 1,
                        end - start));
        return this;
    }
}
