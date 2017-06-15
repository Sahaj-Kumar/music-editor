package cs3500.music.model;

/**
 * Created by sahaj on 6/4/2017.
 */

// TODO BETTER JAVA DOC

/**
 * represents a implementation of note: a traditional western music note.
 */
public class MusicNote implements Note {

  // pitch of note
  private Pitch pitch;

  // octave of note, anything from 1 to 10 inclusive.
  private int octave;

  // starting point of note, from 0 onwards.
  private int startPoint;

  // duration of note, 1 or greater
  private int duration;

  /**
   * constructs an instance of music note with all given parameters.
   * @param pitch pitch of music note
   * @param octave octave of music note
   * @param startPoint starting point of music note
   * @param duration duration of music note
   * @throws IllegalArgumentException if any parameter is invalid
   */
  public MusicNote(Pitch pitch, int octave, int startPoint, int duration) {
    this.pitch = pitch;
    if (octave <= 0 || octave >= 11) {
      throw new IllegalArgumentException("invalid octave");
    }
    this.octave = octave;
    if (startPoint <= -1) {
      throw new IllegalArgumentException("invalid starting point");
    }
    this.startPoint = startPoint;
    if (duration <= 0) {
      throw new IllegalArgumentException("invalid duration");
    }
    this.duration = duration;
  }

  /**
   * constructs a copy of the music note given.
   * @param mn music note being copied
   * @throws IllegalArgumentException if music note is null
   */
  public MusicNote(MusicNote mn) {
    if (mn == null) {
      throw new IllegalArgumentException("cannot have null music note");
    }
    this.pitch = mn.pitch;
    this.octave = mn.octave;
    this.startPoint = mn.startPoint;
    this.duration = mn.duration;
  }

  @Override
  public Pitch getPitch() {
    return this.pitch;
  }

  @Override
  public int getOctave() {
    return this.octave;
  }

  @Override
  public int getStartPoint() {
    return this.startPoint;
  }

  @Override
  public int getDuration() {
    return this.duration;
  }

  @Override
  public void setPitch(Pitch pitch) {
    if (pitch == null) {
      throw new IllegalArgumentException("invalid pitch");
    }
    this.pitch = pitch;
  }

  @Override
  public void setOctave(int octave) {
    if (octave <= 0 || octave >= 11) {
      throw new IllegalArgumentException("invalid octave");
    }
    this.octave = octave;
  }

  @Override
  public void setStartPoint(int startPoint) {
    if (startPoint <= 0) {
      throw new IllegalArgumentException("invalid start point");
    }
    this.startPoint = startPoint;
  }

  @Override
  public void setDuration(int duration) {
    if (duration <= 0) {
      throw new IllegalArgumentException("invalid duration");
    }
    this.duration = duration;
  }

  @Override
  public int noteIndex() {
    return this.pitch.index + (this.octave - 1) * 12;
  }

  @Override
  public Note copy() {
    return new MusicNote(this);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Note)) {
      return false;
    }
    Note other = (Note) o;
    return this.getDuration() == other.getDuration() &&
            this.getOctave() == other.getOctave() &&
            this.getPitch().equals(other.getPitch()) &&
            this.getStartPoint() == other.getStartPoint();
  }

  @Override
  public int hashCode() {
    return (this.pitch.toString() + Integer.toString(this.octave)
            + Integer.toString(this.startPoint) + Integer.toString(this.duration)).hashCode();
  }

}
