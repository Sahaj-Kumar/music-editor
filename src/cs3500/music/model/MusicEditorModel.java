package cs3500.music.model;

/**
 * Created by sahaj on 6/4/2017.
 */

import java.util.List;

/**
 * represents a model for a music editor.
 */
public interface MusicEditorModel {


  /**
   * Returns a list of COPIES of notes in the current music editor.
   * @return all notes.
   */
  List<Note> getNotes();

  /**
   * Get current length of music.
   * @return length of music
   */

  // TODO documentation
  List<Note> currentNotes();


  int getLength();

  /**
   * Returns tempo of music.
   * @return tempo of music
   */
  int getTempo();

  /**
   * Returns beats per measure of music.
   * @return beats per measure of music
   */
  int getBeatsPerMeasure();

  /**
   * Return lowest note in the music, null if does not exist. Useful for display.
   * @return lowest note
   */
  Note lowestNote();

  /**
   * Return highest note in the music, null if does not exist. Useful for display.
   * @return highest note
   */
  Note highestNote();

  /**
   * Sets tempo of music to given.
   * @param tempo new tempo
   */
  void setTempo(int tempo);

  /**
   * Sets beats per measure to given.
   * @param beatsPerMeasure new beats per measure
   */
  void setBeatsPerMeasure(int beatsPerMeasure);

  /**
   * Returns current beat of model.
   * @return current beat
   */
  int getCurrentBeat();

  /**
   * Sets new current beat to the model.
   * @param currentBeat
   */
  void setCurrentBeat(int currentBeat);

  /**
   * Places note onto music interface.
   * If there is an already placed note in the way, does NOT add the note.
   * Will extend length of music if necessary.
   * @param note note being placed
   */
  void placeNote(Note note);

  /**
   * Removes note referenced from model.
   * If removing note causes causes last note to end sooner, length will STILL be same as it was.
   * @param note note being removed
   */
  void removeNote(Note note);

  /**
   * Shifts given notes starting point.
   * Positive shift means to the right, negative to the left.
   * NOTE: This should ensure that if the note to be moved was removed, that the
   * new shifted note can be placed without overlapping other notes (also if on
   * valid beat). If it cannot be placed, leave the original note the way it was.
   * @param note note being moved
   * @param shift offset of note.
   */
  void shiftNote(Note note, int shift);

  /**
   * Updates pitch and or octave of note to given pitch
   * NOTE: This should ensure that if the note trying to be changed was removed, that the
   * new note of different pitch can be placed without overlapping other notes (also if of
   * valid pitch). If it cannot be placed, leave the original note the way it was.
   * @param note note being changed
   * @param pitch new pitch
   * @param octave new octave
   */
  void changeNote(Note note, Pitch pitch, int octave);

  /**
   * Updates length of given note.
   * Makes length that given as parameter.
   * NOTE: This should ensure that if the note trying to be changed was removed, that the
   * new note of different length can be placed without overlapping other notes (also if of
   * valid length). If it cannot be placed, leave the original note the way it was.
   * @param note note being changed
   * @param length new length of note
   */
  void lengthenNote(Note note, int length);

  /**
   * Adds given music directly after the end of this model.
   * This means that following the last note of the music, it begins adding
   * notes from the given music model.
   * Assumes the tempo and beats per measure of initial model.
   * @param musicEditorModel model being appended
   */
  void appendMusic(MusicEditorModel musicEditorModel);

  /**
   * Overlays given music right on top of given music.
   * This means that starting from zero, notes from given model are placed
   * onto this model. If there are particular notes overlapping, the ones on
   * the model parameter will not be added.
   * Assumes the tempo and beats per measure of initial model.
   * @param musicEditorModel model being overlayed
   */
  void overlayMusic(MusicEditorModel musicEditorModel);

  /**
   * Returns string form of current state of music editor.
   * @return string format
   */
  String getMusicState();





}
