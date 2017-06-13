package cs3500.music;

/**
 * Created by sahaj on 6/8/2017.
 */

import cs3500.music.model.MusicNote;
import cs3500.music.model.Note;
import cs3500.music.model.Pitch;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Tests for music note.
 */
public class MusicNoteTests {

  private Note n1;
  private Note n2;
  private Note n3;
  private Note n4;

  private void initCond() {
    n1 = new MusicNote(Pitch.C, 4, 1, 4);
    n2 = new MusicNote(Pitch.Cx, 4, 1, 4);
    n3 = new MusicNote(Pitch.C, 1, 1, 4);
    n4 = new MusicNote(Pitch.B, 10, 1, 4);
  }

  // simple test that covers all getter and setter methods.
  @Test
  public void testMusicNote1() {
    this.initCond();
    assertEquals(n1.getPitch(), Pitch.C);
    n1.setPitch(Pitch.Cx);
    assertEquals(n1.getPitch(), Pitch.Cx);
    assertEquals(n1.getOctave(), 4);
    n1.setOctave(5);
    assertEquals(n1.getOctave(), 5);
    assertEquals(n1.getStartPoint(), 1);
    n1.setStartPoint(4);
    assertEquals(n1.getStartPoint(), 4);
    assertEquals(n1.getDuration(), 4);
    n1.setDuration(10);
    assertEquals(n1.getDuration(), 10);
  }

  // test that copy method return a SEPERATE music note instance with same parameters
  @Test
  public void testMusicNote2() {
    this.initCond();
    Note n1Copy = n1.copy();
    assertEquals(n1Copy == n1, false);
    assertEquals(n1Copy.getDuration(), n1.getDuration());
    assertEquals(n1Copy.getStartPoint(), n1.getStartPoint());
    assertEquals(n1Copy.getOctave(), n1.getOctave());
    assertEquals(n1Copy.getPitch(), n1.getPitch());
  }

  // test for getting note index, testing two adjacent notes and both extremes
  @Test
  public void testMusicNote3() {
    this.initCond();
    assertEquals(n1.noteIndex(), 37);
    assertEquals(n2.noteIndex(), 38);
    assertEquals(n3.noteIndex(), 1);
    assertEquals(n4.noteIndex(), 120);
  }

  // tests for bad start point setting
  @Test (expected = IllegalArgumentException.class)
  public void testMusicNote4() {
    this.initCond();
    n1.setStartPoint(0);
  }

  // tests for bad start point setting
  @Test (expected = IllegalArgumentException.class)
  public void testMusicNote5() {
    this.initCond();
    n1.setStartPoint(-1);
  }

  // tests for bad octave setting
  @Test (expected = IllegalArgumentException.class)
  public void testMusicNote6() {
    this.initCond();
    n1.setOctave(0);
  }

  // tests for bad octave setting
  @Test (expected = IllegalArgumentException.class)
  public void testMusicNote7() {
    this.initCond();
    n1.setOctave(11);
  }

  // tests for bad pitch setting
  @Test (expected = IllegalArgumentException.class)
  public void testMusicNote8() {
    this.initCond();
    n1.setPitch(null);
  }

  // tests for bad duration setting
  @Test (expected = IllegalArgumentException.class)
  public void testMusicNote9() {
    this.initCond();
    n1.setDuration(0);
  }

  // tests for bad duration setting
  @Test (expected = IllegalArgumentException.class)
  public void testMusicNote10() {
    this.initCond();
    n1.setDuration(-1);
  }




}
