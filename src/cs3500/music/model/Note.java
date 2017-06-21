package cs3500.music.model;

/**
 * Created by sahaj on 6/4/2017.
 */

// TODO BETTER JAVA DOC

/**
 * Represents a model for a note played in music.
 * Should be able to get and set its octave, pitch, starting point, each should
 * have a unique note index such that lower notes have lower indices and higher
 * notes have higher indices.
 */
public interface Note {

  /**
   * get pitch of note.
   *
   * @return pitch
   */
  Pitch getPitch();

  /**
   * get octave of note.
   *
   * @return octave
   */
  int getOctave();

  /**
   * get starting point of note.
   *
   * @return starting point
   */
  int getStartPoint();

  /**
   * get duration of note.
   *
   * @return duration
   */
  int getDuration();

  /**
   * updates pitch of note with given.
   *
   * @param pitch new pitch
   */
  void setPitch(Pitch pitch);

  /**
   * updates octave of note with given.
   *
   * @param octave new octave
   */
  void setOctave(int octave);

  /**
   * updates starting point with given.
   *
   * @param startPoint new starting point
   */
  void setStartPoint(int startPoint);

  /**
   * updates duration with given.
   *
   * @param duration new duration
   */
  void setDuration(int duration);


  /**
   * Returns unique index for note.
   * If note is lower than another, index is lower than the other.
   *
   * @return music note index
   */
  int noteIndex();

  /**
   * returns a copied, NON-REFERENCE note.
   *
   * @return copy of note
   */
  Note copy();

}

