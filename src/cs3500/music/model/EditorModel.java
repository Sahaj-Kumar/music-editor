package cs3500.music.model;

/**
 * Created by sahaj on 6/5/2017.
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * represents implementation of music editor model.
 */
public class EditorModel implements MusicEditorModel {

    // represents maps of all notes assigned to the beats they start and are continued through
    private TreeMap<Integer, List<Note>> notes;

    // represents current tempo of music, in beats per minute
    private int tempo;

    // represents the beats per measure
    private int beatsPerMeasure;

    private int currentBeat;
    /**
     * contructs instance of music editor, with no music notes, no established beats,
     * a default tempo of 60bpm, and a default beats per measure of 4.
     */
    public EditorModel() {

        this.notes = new TreeMap<Integer, List<Note>>();
        this.tempo = 60;
        this.beatsPerMeasure = 4;
        this.currentBeat = 1;
    }

    @Override
    public List<Note> getNotes() {
        List<Note> allNotes = new ArrayList<Note>();
        if (this.getLength() == 0) {
            return allNotes;
        }
        for (int i = 1; i <= this.getLength(); i++) {
            for (Note n : this.notes.get(i)) {
                if (!allNotes.contains(n)) {
                    allNotes.add(n.copy());
                }
            }
        }
        return allNotes;
    }

    @Override
    public List<Integer> currentNodeIndexes() {
        List<Integer> ints = new ArrayList<Integer>();
        for (Note n: this.notes.get(this.currentBeat)) {
            ints.add(n.copy().noteIndex());
        }
        return ints;
    }

    @Override
    public int getLength() {
        if (this.notes.isEmpty()) {
            return 0;
        }
        else {
            return this.notes.lastKey();
        }
    }

    @Override
    public int getTempo() {
        return this.tempo;
    }

    @Override
    public int getBeatsPerMeasure() {
        return this.beatsPerMeasure;
    }

    @Override
    public Note lowestNote() {
        return overallLowestOrHighestNote(false);
    }

    @Override
    public Note highestNote() {
        return overallLowestOrHighestNote(true);
    }

    /**
     * Return either the lowest or highest note in the music.
     * Return null if note does not exist.
     * @param highest if true returns heighest, lowest if false.
     * @return either highest or lowest note
     */
    private Note overallLowestOrHighestNote(boolean highest) {
        Note extreme = null;
        for (Map.Entry<Integer, List<Note>> notes : this.notes.entrySet()) {
            Note extremeOfBeat = this.lowestOrHighestNoteInList(notes.getValue(), true);
            if (extreme == null) {
                extreme = extremeOfBeat;
            }
            if (extremeOfBeat != null) {
                if (highest) {
                    if (extremeOfBeat.noteIndex() > extreme.noteIndex()) {
                        extreme = extremeOfBeat;
                    }
                }
                else {
                    if (extremeOfBeat.noteIndex() < extreme.noteIndex()) {
                        extreme = extremeOfBeat;
                    }
                }
            }
        }
        return extreme;
    }

    /**
     * Returns lowest or highest note of given beat.
     * null if it does not exist.
     * @param notes notes of the beat
     * @param highest if true returns highest, lowest if false.
     * @return either highest or lowest note of the beat
     */
    private Note lowestOrHighestNoteInList(List<Note> notes, boolean highest) {
        Note extreme = null;
        for (Note n : notes) {
            if (extreme == null) {
                extreme = n;
            }
            if (highest) {
                if (n.noteIndex() > extreme.noteIndex()) {
                    extreme = n;
                }
            }
            else {
                if (n.noteIndex() < extreme.noteIndex()) {
                    extreme = n;
                }
            }
        }
        return extreme;
    }

    @Override
    public void setTempo(int tempo) {
        if (tempo <= 0) {
            throw new IllegalArgumentException("invalid tempo");
        }
        this.tempo = tempo;
    }

    @Override
    public void setBeatsPerMeasure(int beatsPerMeasure) {
        if (beatsPerMeasure <= 0) {
            throw new IllegalArgumentException("invalid beats per measure");
        }
        this.beatsPerMeasure = beatsPerMeasure;
    }

    @Override
    public int getCurrentBeat() {
        return this.currentBeat;
    }

    @Override
    public void setCurrentBeat(int currentBeat) {
        if (currentBeat <= 0 || currentBeat > this.getLength()) {
            throw new IllegalArgumentException("invalid beat");
        }
        this.currentBeat = currentBeat;
    }

    @Override
    public void placeNote(Note note) {
        if (note.getStartPoint() > this.getLength()) {
            for (int i = this.getLength() + 1; i < note.getStartPoint(); i++) {
                this.notes.put(i, new ArrayList<Note>());
            }
        }
        if (!this.doesNoteOverlap(note)) {
            for (int i = note.getStartPoint(); i < note.getStartPoint() + note.getDuration(); i++) {
                if (!this.notes.containsKey(i)) {
                    this.notes.put(i, new ArrayList<Note>());
                }
                this.notes.get(i).add(note);
            }
        }
    }

    /**
     * Return if note overlaps with any other in music.
     * This means if the starting point or its duration collides with the starting point
     * or duration of any other notes.
     * @param note being checked if it overlaps
     * @return true if note overlaps with another, false otherwise
     */
    private boolean doesNoteOverlap(Note note) {
        if (this.getLength() == 0) {
            return false;
        }
        int range = note.getDuration();
        for (int i = note.getStartPoint(); i < note.getStartPoint() + note.getDuration(); i++) {
            if (!this.notes.containsKey(i)) {
                return false;
            }
            for (Note n : this.notes.get(i)) {
                if (n.getPitch().equals(note.getPitch()) &&
                        n.getOctave() == note.getOctave()) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void removeNote(Note note) {
        if (this.notes.containsKey(note.getStartPoint())) {
            for (int i = note.getStartPoint(); i < note.getStartPoint() + note.getDuration(); i++) {
                notes.get(i).remove(note);
            }
        }

    }

    @Override
    public void shiftNote(Note note, int shift) {
        int newBeat = note.getStartPoint() + shift;
        if (newBeat > 0) {
            Note newNote = note.copy();
            newNote.setStartPoint(newBeat);
            this.removeNote(note);
            if (!this.doesNoteOverlap(newNote)) {
                this.placeNote(newNote);
            }
            else {
                this.placeNote(note);
            }
        }
    }

    @Override
    public void changeNote(Note note, Pitch pitch, int octave) {
        Note newNote = note.copy();
        newNote.setPitch(pitch);
        newNote.setOctave(octave);
        this.removeNote(note);
        if (pitch != null && !this.doesNoteOverlap(newNote) && octave >= 1 && octave <= 10) {
            this.placeNote(newNote);
        }
        else {
            this.placeNote(note);
        }
    }

    @Override
    public void lengthenNote(Note note, int length) {
        if (length >= 1) {
            Note newNote = note.copy();
            newNote.setDuration(length);
            this.removeNote(note);
            if (!this.doesNoteOverlap(newNote)) {
                this.placeNote(newNote);
            }
            else {
                this.placeNote(note);
            }
        }
    }

    @Override
    public void appendMusic(MusicEditorModel musicEditorModel) {
        int curLength = this.getLength();
        for (Note n : musicEditorModel.getNotes()) {
            n.setStartPoint(n.getStartPoint() + curLength);
            this.placeNote(n);
        }
    }

    @Override
    public void overlayMusic(MusicEditorModel musicEditorModel) {
        for (Note n : musicEditorModel.getNotes()) {
            this.placeNote(n);
        }
    }

    @Override
    public String getMusicState() {
        if (this.lowestNote() == null) {
            return "";
        }
        String musicState = "";
        String deadCorner = "";
        while (deadCorner.length() < Integer.toString(this.getLength()).length()) {
            deadCorner = deadCorner + " ";
        }
        musicState = deadCorner + " " + this.getMusicRange();
        for (int i = 1; i <= this.getLength(); i++) {
            String beatAsString = "";
            beatAsString = this.getBeatState(i, this.notes.get(i));
            musicState = musicState + "\n" + beatAsString;
        }
        return musicState;
    }

    /**
     * Returns a string of consecutive music notes (pitch and octave), from the lowest note in
     * the music, to the highest. There will always be 5 character-spaces allocated to each music.
     * @return string of current music notes
     */
    private String getMusicRange() {
        String musicRange = "";
        Note curNote = this.lowestNote();
        while (curNote.noteIndex() <= this.highestNote().noteIndex()) {
            String stringNote = curNote.getPitch().toString() + curNote.getOctave();
            while (stringNote.length() < 5) {
                stringNote = stringNote + " ";
            }
            musicRange = musicRange + stringNote;
            Note nextNote = curNote.copy();
            nextNote.setPitch(nextNote.getPitch().nextPitch());
            if (nextNote.getPitch().equals(Pitch.C)) {
                nextNote.setOctave(nextNote.getOctave() + 1);
            }
            curNote = nextNote;
        }
        return musicRange;
    }

    /**
     * Returns the beat state as a string.
     * Starts with number, of beat, followed by 3 instances for notes, lowest to highest:
     * - "     ", means no note playing on beat.
     * - "X    ", means a note just started on this beat.
     * - "|    ", mean a notes was started and endured during this beat.
     * @param beat beat being converted to string
     * @param notes notes that are either started or endured during this beat
     * @return beat as string
     */
    private String getBeatState(int beat, List<Note> notes) {
        String beatAsString = Integer.toString(beat);
        while (beatAsString.length() < Integer.toString(this.getLength()).length()) {
            beatAsString = beatAsString + " ";
        }
        beatAsString = beatAsString + " ";
        Note curNote = this.lowestNote();
        while (curNote.noteIndex() <= this.highestNote().noteIndex()) {
            Note note = this.retrieveNoteWithPitch(curNote, notes);
            if (note != null) {
                if (note.getStartPoint() == beat) {
                    beatAsString = beatAsString + "X    ";
                }
                else {
                    beatAsString = beatAsString + "|    ";
                }
            }
            else {
                beatAsString = beatAsString + "     ";
            }
            Note nextNote = curNote.copy();
            nextNote.setPitch(nextNote.getPitch().nextPitch());
            if (nextNote.getPitch().equals(Pitch.C)) {
                nextNote.setOctave(nextNote.getOctave() + 1);
            }
            curNote = nextNote;
        }
        return beatAsString;
    }

    private Note retrieveNoteWithPitch(Note note, List<Note> notes) {
        for (Note n : notes) {
            if (n.getPitch().equals(note.getPitch()) &&
                    n.getOctave() == note.getOctave()) {
                return n;
            }
        }
        return null;
    }


}
