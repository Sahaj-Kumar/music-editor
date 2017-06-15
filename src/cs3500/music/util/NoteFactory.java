package cs3500.music.util;

import cs3500.music.model.MusicNote;
import cs3500.music.model.Note;
import cs3500.music.model.Pitch;

/**
 * Created by sahaj on 6/15/2017.
 */


public class NoteFactory {

    public static Note buildNote(String type, int start, int end, int instrument, int pitch, int volume) {
        switch (type) {
            case "MusicNote":
                return new MusicNote(
                        Pitch.pitchMap.get(Math.floorMod(pitch - 24, 12) + 1),
                        (pitch - 24) / 12 + 1,
                        start + 1,
                        end - start);
            default:
                throw new IllegalArgumentException("invalid note type");
        }
    }
}
