package cs3500.music.util;

import cs3500.music.model.MusicNote;
import cs3500.music.model.Note;
import cs3500.music.model.Pitch;

/**
 * Created by sahaj on 6/15/2017.
 */

/**
 * A builder class that contructs a note. As of now, currently can contruct the only
 * implementation of note, a Music Note.
 */
public class NoteFactory {

  /**
   * Contructs a note with given parameters of the given note type.
   * In some cases, all parameters may not be used.
   * @param type name of note type to be contructed
   * @param start starting point of note
   * @param end end point of note
   * @param instrument instriment playing note
   * @param pitch pitch of note
   * @param volume volume of note
   * @return note og type name and parameters
   */
  public static Note buildNote(String type, int start, int end, int instrument,
                               int pitch, int volume) {
    if (type.equals("MusicNote")) {
      return new MusicNote(
              Pitch.pitchMap.get(Math.floorMod(pitch - 24, 12) + 1),
              (pitch - 24) / 12 + 1,
              start + 1,
              end - start);
    }
    else {
      throw new IllegalArgumentException("invalid note type");
    }
  }
}
