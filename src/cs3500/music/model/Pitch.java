package cs3500.music.model;

/**
 * Created by sahaj on 6/4/2017.
 */

import java.util.HashMap;
import java.util.Map;

/**
 * Represents enumeration of standard pitches. These pitches follow the
 * conventions of traditional western music.
 */
public enum Pitch {

  // x means sharp
  C(1), Cx(2), D(3), Dx(4), E(5), F(6), Fx(7), G(8), Gx(9), A(10), Ax(11), B(12);

  // since notes are sequential, they are assigned an index
  int index;

  /**
   * Contructs pitch given its index.
   * @param index of pitch
   */
  private Pitch(int index) {
    this.index = index;
  }

  /**
   * Return string format of pitch.
   * @return string format.
   */
  @Override
  public String toString() {
    return this.name().replace("x", "#");
  }


  // Map of indexes assoicated to their pitches
  public static Map<Integer, Pitch> pitchMap = new HashMap<Integer, Pitch>();

  static {
    for (Pitch pitchEnum : Pitch.values()) {
      pitchMap.put(pitchEnum.index, pitchEnum);
    }
  }

  /**
   * Gets pitch given index.
   * @param index of pitch
   * @return pitch of given index
   */
  private Pitch getPitchFromIndex(int index) {
    return pitchMap.get(index);
  }

  /**
   * Return next pitch from previous. Is purely for convenience.
   * Does wrap around.
   * @return next Pitch
   */
  public Pitch nextPitch() {
    if (this.index == 12) {
      return Pitch.C;
    }
    else {
      return this.getPitchFromIndex(this.index + 1);
    }
  }



}
