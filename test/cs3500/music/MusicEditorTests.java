package cs3500.music;

/**
 * Created by sahaj on 6/7/2017.
 */

import cs3500.music.model.EditorModel;
import cs3500.music.model.MusicEditorModel;
import cs3500.music.model.MusicNote;
import cs3500.music.model.Note;
import cs3500.music.model.Pitch;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Tests for music editor model.
 */
public class MusicEditorTests {

  private MusicEditorModel m1;
  private MusicEditorModel m2;
  private Note cOne;
  private Note cFourA;
  private Note cFourB;
  private Note cFourC;
  private Note cSharpFour;
  private Note bTen;

  private void initCond() {
    m1 = new EditorModel();
    m2 = new EditorModel();
    cOne = new MusicNote(Pitch.C, 1, 1, 1);
    cFourA = new MusicNote(Pitch.C, 4, 4, 4);
    cFourB = new MusicNote(Pitch.C, 4, 6, 4);
    cFourC = new MusicNote(Pitch.C, 4, 8, 4);
    cSharpFour = new MusicNote(Pitch.Cx, 4, 4, 4);
    bTen = new MusicNote(Pitch.B, 10, 8, 4);
  }

  // tests that initial instance of song has zero length, and null lowest and highest note
  @Test
  public void testMusicEditor1() {
    this.initCond();
    assertEquals(m1.getLength(), 0);
    assertEquals(m1.lowestNote(), null);
    assertEquals(m1.highestNote(), null);
  }

  // simple test that ensures getters and setters work properly
  @Test
  public void testMusicEditor2() {
    this.initCond();
    assertEquals(m1.getLength(), 0);
    assertEquals(m1.getBeatsPerMeasure(), 4);
    m1.setBeatsPerMeasure(3);
    assertEquals(m1.getBeatsPerMeasure(), 3);
    assertEquals(m1.getTempo(), 60);
    m1.setTempo(40);
    assertEquals(m1.getTempo(), 40);
  }

  // tests that tempo setter throws exception when appropriate
  @Test (expected = IllegalArgumentException.class)
  public void testMusicEditor4() {
    this.initCond();
    m1.setTempo(-1);
  }

  // tests beats per measure setter throws exception when appropriate
  @Test (expected = IllegalArgumentException.class)
  public void testMusicEditor5() {
    this.initCond();
    m1.setBeatsPerMeasure(-1);
  }

  // Test that makes sure valid note placements increase length of music when
  // necessary, and appropriately update the lowest and highest note of the music.
  // In the end also tests two notes playing at the same time.
  @Test
  public void testMusicEditor6() {
    this.initCond();
    m1.placeNote(cOne);
    assertEquals(m1.getLength(), 1);
    assertEquals(m1.lowestNote(), cOne);
    assertEquals(m1.highestNote(), cOne);
    m1.placeNote(cFourA);
    assertEquals(m1.getLength(), 7);
    assertEquals(m1.lowestNote(), cOne);
    assertEquals(m1.highestNote(), cFourA);
    m1.placeNote(bTen);
    assertEquals(m1.getLength(), 11);
    assertEquals(m1.lowestNote(), cOne);
    assertEquals(m1.highestNote(), bTen);
  }

  // Special test that tests placing of notes. Tests that if note to be added will overlap,
  // will not be added. Also tests successful adding of adjacent notes by pitch and position
  // and other notes.
  @Test
  public void testMusicEditor7() {
    this.initCond();
    m1.placeNote(cFourA);
    assertEquals(m1.getLength(), 7);
    assertEquals(m1.getNotes().size(), 1);
    //placing overlapping note
    m1.placeNote(cFourB);
    assertEquals(m1.getLength(), 7);
    assertEquals(m1.getNotes().size(), 1);
    //placing note that is directly after previous
    m1.placeNote(cFourC);
    assertEquals(m1.getLength(), 11);
    assertEquals(m1.getNotes().size(), 2);
    m1.placeNote(cSharpFour);
    assertEquals(m1.getLength(), 11);
    assertEquals(m1.getNotes().size(), 3);
    m1.placeNote(cOne);
    assertEquals(m1.getNotes().size(), 4);
    m1.placeNote(bTen);
    assertEquals(m1.getNotes().size(), 5);
  }

  // important test that shoes that getNotes method returns a list of NON-REFERENCE
  // notes in the music editor, so you cannot add notes through it, nor edit notes
  // through it.
  @Test
  public void testMusicEditor8() {
    this.initCond();
    m1.placeNote(cFourA);
    assertEquals(m1.getLength(), 7);
    assertEquals(m1.getNotes().size(), 1);
    m1.getNotes().add(cFourC);
    assertEquals(m1.getLength(), 7);
    assertEquals(m1.getNotes().size(), 1);
    m1.getNotes().get(0).setDuration(100);
    assertEquals(m1.getLength(), 7);
  }

  // tests removing of a note. Also checks that length of music length remains same
  // after even after removal. Also makes sure attempting to remove non-existant note
  // does nothing to current state.
  @Test
  public void testMusicEditor9() {
    this.initCond();
    m1.placeNote(cFourA);
    assertEquals(m1.getLength(), 7);
    assertEquals(m1.getNotes().size(), 1);
    m1.removeNote(cFourA);
    assertEquals(m1.getLength(), 7);
    assertEquals(m1.getNotes().size(), 0);
    // important test that shows removing any note that does not exist does nothing
    m1.removeNote(cSharpFour);
    assertEquals(m1.getLength(), 7);
    assertEquals(m1.getNotes().size(), 0);
  }

  // tests shifting of notes, and that if there is note in the way, shift does
  // not take place, or if shift would cause negative beat.
  @Test
  public void testMusicEditor10() {
    this.initCond();
    m1.placeNote(cFourA);
    m1.placeNote(cFourC);
    assertEquals(m1.getNotes().get(0).getStartPoint(), 4);
    // shift overlaps
    m1.shiftNote(cFourA, 2);
    assertEquals(m1.getNotes().get(0).getStartPoint(), 4);
    // shift barely overlaps
    m1.shiftNote(cFourA, 7);
    assertEquals(m1.getNotes().get(0).getStartPoint(), 4);
    // shift barely does NOT overlap
    m1.shiftNote(cFourA, 8);
    assertEquals(m1.getNotes().get(1).getStartPoint(), 12);
    // shift backwards overlaps
    m1.shiftNote(m1.getNotes().get(1), -2);
    assertEquals(m1.getNotes().get(1).getStartPoint(), 12);
    // shift barely overlaps
    m1.shiftNote(m1.getNotes().get(1), -7);
    assertEquals(m1.getNotes().get(1).getStartPoint(), 12);
    // shift barely does NOT overlap
    m1.shiftNote(m1.getNotes().get(1), -8);
    assertEquals(m1.getNotes().get(0).getStartPoint(), 4);
    // shift backwards is invalid because it would become negative beat
    m1.shiftNote(m1.getNotes().get(0), -4);
    assertEquals(m1.getNotes().get(0).getStartPoint(), 4);
  }

  // tests change duration of notes, makes sure if change would cause overlap,
  // of if it is an invalid duration, does not perform change.
  @Test
  public void testMusicEditor11() {
    this.initCond();
    m1.placeNote(cFourA);
    m1.placeNote(cFourC);
    assertEquals(cFourA.getDuration(), 4);
    // cannot extend note
    m1.lengthenNote(cFourA, 5);
    assertEquals(m1.getNotes().get(0).getDuration(), 4);
    assertEquals(m1.getNotes().size(), 2);
    // can decrease length of note
    m1.lengthenNote(cFourA, 3);
    assertEquals(m1.getNotes().get(0).getDuration(), 3);
    assertEquals(m1.getNotes().size(), 2);
    // cannot decrease length to 0
    m1.lengthenNote(m1.getNotes().get(0), 0);
    assertEquals(m1.getNotes().get(0).getDuration(), 3);
    assertEquals(m1.getNotes().size(), 2);
    // cannot decrease length to negative
    m1.lengthenNote(m1.getNotes().get(0), -1);
    assertEquals(m1.getNotes().get(0).getDuration(), 3);
    assertEquals(m1.getNotes().size(), 2);
  }

  // tests changeNote method. Makes sure if pitch and octave change do not
  // ensure note can be moved, else keeps current music state.
  @Test
  public void testMusicEditor12() {
    this.initCond();
    m1.placeNote(cFourA);
    m1.placeNote(cSharpFour);
    assertEquals(m1.getNotes().get(0).getPitch(), Pitch.C);
    assertEquals(m1.getNotes().size(), 2);
    // cannot place note due to overlap
    m1.changeNote(cFourA, Pitch.Cx, 4);
    assertEquals(m1.getNotes().get(1).getPitch(), Pitch.C);
    assertEquals(m1.getNotes().size(), 2);
    // note can be placed
    m1.changeNote(m1.getNotes().get(1), Pitch.Cx, 5);
    assertEquals(m1.getNotes().size(), 2);
    assertEquals(m1.getNotes().get(1).getPitch(), Pitch.Cx);
    assertEquals(m1.getNotes().get(1).getOctave(), 5);
  }

  // super test that checks that append successfully adds given music model
  // directly after the original. (overlaps are impossible)
  @Test
  public void testMusicEditor13() {
    this.initCond();
    m1.placeNote(cSharpFour);
    m1.placeNote(cFourA);
    m1.placeNote(cFourC);
    assertEquals(m1.getNotes().size(), 3);
    assertEquals(m1.getLength(), 11);
    assertEquals(m1.getNotes().get(0).getStartPoint(), 4);
    assertEquals(m1.getNotes().get(0).getPitch(), Pitch.Cx);
    assertEquals(m1.getNotes().get(0).getOctave(), 4);
    assertEquals(m1.getNotes().get(0).getDuration(), 4);
    assertEquals(m1.getNotes().get(1).getStartPoint(), 4);
    assertEquals(m1.getNotes().get(1).getPitch(), Pitch.C);
    assertEquals(m1.getNotes().get(1).getOctave(), 4);
    assertEquals(m1.getNotes().get(1).getDuration(), 4);
    assertEquals(m1.getNotes().get(2).getStartPoint(), 8);
    assertEquals(m1.getNotes().get(2).getPitch(), Pitch.C);
    assertEquals(m1.getNotes().get(2).getOctave(), 4);
    assertEquals(m1.getNotes().get(2).getDuration(), 4);

    // this is appending the current music to itself
    m1.appendMusic(m1);
    assertEquals(m1.getNotes().size(), 6);
    assertEquals(m1.getLength(), 22);
    assertEquals(m1.getNotes().get(3).getStartPoint(), 15);
    assertEquals(m1.getNotes().get(3).getPitch(), Pitch.Cx);
    assertEquals(m1.getNotes().get(3).getOctave(), 4);
    assertEquals(m1.getNotes().get(3).getDuration(), 4);
    assertEquals(m1.getNotes().get(4).getStartPoint(), 15);
    assertEquals(m1.getNotes().get(4).getPitch(), Pitch.C);
    assertEquals(m1.getNotes().get(4).getOctave(), 4);
    assertEquals(m1.getNotes().get(4).getDuration(), 4);
    assertEquals(m1.getNotes().get(5).getStartPoint(), 19);
    assertEquals(m1.getNotes().get(5).getPitch(), Pitch.C);
    assertEquals(m1.getNotes().get(5).getOctave(), 4);
    assertEquals(m1.getNotes().get(5).getDuration(), 4);

    // appending a different model
    bTen.setStartPoint(10);
    m2.placeNote(bTen);
    m1.appendMusic(m2);
    assertEquals(m1.getNotes().size(), 7);
    assertEquals(m1.getNotes().get(6).getPitch(), Pitch.B);
    assertEquals(m1.getNotes().get(6).getOctave(), 10);
    assertEquals(m1.getNotes().get(6).getStartPoint(), 32);
    assertEquals(m1.getNotes().get(6).getDuration(), 4);
  }

  // test of overlay that proves overlaying music model onto itselg does nothing to model.
  @Test
  public void testMusicEditor14() {
    this.initCond();
    m1.placeNote(cFourA);
    m1.placeNote(cFourC);
    assertEquals(m1.getNotes().size(), 2);
    assertEquals(m1.getLength(), 11);
    assertEquals(m1.getNotes().get(0).getStartPoint(), 4);
    assertEquals(m1.getNotes().get(0).getPitch(), Pitch.C);
    assertEquals(m1.getNotes().get(0).getOctave(), 4);
    assertEquals(m1.getNotes().get(0).getDuration(), 4);
    assertEquals(m1.getNotes().get(1).getStartPoint(), 8);
    assertEquals(m1.getNotes().get(1).getPitch(), Pitch.C);
    assertEquals(m1.getNotes().get(1).getOctave(), 4);
    assertEquals(m1.getNotes().get(1).getDuration(), 4);
    // overlay onto same model should do nothing
    m1.overlayMusic(m1);
    assertEquals(m1.getNotes().size(), 2);
    assertEquals(m1.getLength(), 11);
    assertEquals(m1.getNotes().get(0).getStartPoint(), 4);
    assertEquals(m1.getNotes().get(0).getPitch(), Pitch.C);
    assertEquals(m1.getNotes().get(0).getOctave(), 4);
    assertEquals(m1.getNotes().get(0).getDuration(), 4);
    assertEquals(m1.getNotes().get(1).getStartPoint(), 8);
    assertEquals(m1.getNotes().get(1).getPitch(), Pitch.C);
    assertEquals(m1.getNotes().get(1).getOctave(), 4);
    assertEquals(m1.getNotes().get(1).getDuration(), 4);
  }

  // test that overlays two seperate models. Also tests that when overlaying,
  // only adds the notes that do not overlap with initial music
  @Test
  public void testMusicEditor15() {
    this.initCond();
    m1.placeNote(cFourA);
    m2.placeNote(cFourB);
    m2.placeNote(bTen);

    // one note overlaps, and will not be added, the other should
    m1.overlayMusic(m2);
    assertEquals(m1.getNotes().size(), 2);
    assertEquals(m1.getLength(), 11);
    assertEquals(m1.getNotes().get(0).getStartPoint(), 4);
    assertEquals(m1.getNotes().get(0).getPitch(), Pitch.C);
    assertEquals(m1.getNotes().get(0).getOctave(), 4);
    assertEquals(m1.getNotes().get(0).getDuration(), 4);
    assertEquals(m1.getNotes().get(1).getStartPoint(), 8);
    assertEquals(m1.getNotes().get(1).getPitch(), Pitch.B);
    assertEquals(m1.getNotes().get(1).getOctave(), 10);
    assertEquals(m1.getNotes().get(1).getDuration(), 4);
  }
}
