package cs3500.music.view;

import cs3500.music.model.MusicEditorModel;
import cs3500.music.model.Note;
import cs3500.music.model.Pitch;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

import javax.swing.JPanel;

/**
 * A dummy view that simply draws a string. PSYCH, nah this does stuff now.
 * This class, which extends JPanel, constructs a visual scene of the SHEET
 * of music. This means a graph with notes organized by pitch and starting
 * points, with duration, and a beat marker for current beat.
 */
public class ConcreteGuiViewPanel extends JPanel {

  // static unit that provides constant for size
  // make it bigger or smaller and it scales the whole Jpanel
  private final static int UNIT = 20;

  // the editor model
  private MusicEditorModel model;
  private int length;
  private Note lowestNote;
  private int noteRange;
  private int highestNoteIndex;
  // margin to shift sheet away from edges
  private int margin;

  /**
   * Main contructor for Concrete Gui View Panel.
   * Takes the model, harvests some of its parameters for convenience.
   * @param model the model being rendered
   */
  ConcreteGuiViewPanel(MusicEditorModel model) {
    this.model = model;
    this.margin = UNIT * 5;

    /*
    this.setSize(new Dimension(
            this.margin * 2 + (this.length / this.model.getBeatsPerMeasure()) *
                    this.model.getBeatsPerMeasure() *
                    UNIT,
            this.margin * 2 + this.noteRange * UNIT));
            */
    this.setPreferredSize(new Dimension(
            this.margin * 2 + (this.length / this.model.getBeatsPerMeasure()) *
                    this.model.getBeatsPerMeasure() *
                    UNIT,
            this.margin * 2 + this.noteRange * UNIT));

    if (model.lowestNote() != null) {
      this.length = model.getLength();
      this.lowestNote = model.lowestNote();
      Note highestNote = model.highestNote();
      this.noteRange = highestNote.noteIndex() - this.lowestNote.noteIndex() + 1;
      this.highestNoteIndex = model.highestNote().noteIndex();

    }
    else {
      this.length = 0;
      this.noteRange = 0;
    }
  }

  @Override
  public void paintComponent(Graphics g) {
    // Handle the default painting
    super.paintComponent(g);
    // Look for more documentation about the Graphics class,
    // and methods on it that may be useful
    //g.drawString("Hello World", 25, 25);
    if (model.lowestNote() != null) {
      this.drawNotes(g);
      this.drawSheet(g);
      this.drawNotesAxisAndOctaveLines(g);
      this.drawMeasureNumbers(g);
      this.drawCurrentBeatMarker(g);
      this.setVisible(true);
    }
    else {
      Graphics2D g2 = (Graphics2D) g;
      g2.setFont(new Font("TimesRoman", Font.PLAIN, 100));
      g2.drawString("Place a Note!", 100, 100);
    }

  }

  /**
   * Draws all notes onto the scene.
   * Distinguishes the starting point beat from sustained beats, AND
   * distinguishes currently playing beats from ones that are not.
   * @param g the graphics used
   */
  public void drawNotes(Graphics g) {
    Graphics2D g2 = (Graphics2D) g;
    boolean onBeat = false;
    Color startNotPlaying = Color.BLUE;
    Color sustainNotPlaying = Color.CYAN;
    Color startPlaying = new Color(0, 180, 60);
    Color sustainPlaying = new Color(0, 255, 128);
    for (Note n : model.getNotes()) {
      onBeat = this.isOnBeat(n);
      for (int i = 0; i < n.getDuration(); i++) {
        if (i == 0) {
          if (isOnBeat(n)) {
            g2.setPaint(startPlaying);
          }
          else {
            g2.setPaint(startNotPlaying);
          }
          g2.fill(new Rectangle2D.Double(n.getStartPoint() * UNIT + this.margin,
                  (this.highestNoteIndex - n.noteIndex() + 1) * UNIT + this.margin, UNIT, UNIT));
        }
        else {
          if (isOnBeat(n)) {
            g2.setPaint(sustainPlaying);
          }
          else {
            g2.setPaint(sustainNotPlaying);
          }
          g2.fill(new Rectangle2D.Double((n.getStartPoint() + i) * UNIT + this.margin,
                  (this.highestNoteIndex - n.noteIndex() + 1) * UNIT + this.margin, UNIT, UNIT));
        }
      }
    }
  }

  /**
   * Determines if particular note is on the current beat.
   * @param n note being checked
   * @return true if note is on current beat, false otherwise
   */
  private boolean isOnBeat(Note n) {
    for (int i = n.getStartPoint(); i < n.getStartPoint() + n.getDuration(); i++) {
      if (i == model.getCurrentBeat()) {
        return true;
      }
    }
    return false;
  }

  /**
   * Draws the grid of music.
   * This grid should expand from the lowest to the highest note in the
   * current music, and expand from beat one up to the length of music divided
   * by
   * @param g graphics used
   */
  private void drawSheet(Graphics g) {
    Graphics2D g2 = (Graphics2D) g;
    g2.setPaint(Color.BLACK);

    for (int i = 1; i <= this.noteRange; i++) {
      for (int i2 = 0; i2 < Math.ceil(this.model.getLength() / (double) this.model
              .getBeatsPerMeasure()); i2++) {
        g2.drawRect(i2 * UNIT * this.model.getBeatsPerMeasure() + this.margin + UNIT,
                (i * UNIT) + this.margin, this.model.getBeatsPerMeasure() * UNIT, UNIT);
      }
    }
  }

  /**
   * Draws notes axis on left side of sheet, from current lowest note, to highest
   * note. Also draws thicker horizontal lines for each octave change.
   * @param g graphics used
   */
  private void drawNotesAxisAndOctaveLines(Graphics g) {
    Graphics2D g2 = (Graphics2D) g;
    Pitch curPitch = this.lowestNote.getPitch();
    int curOctave = this.lowestNote.getOctave();
    int measuresLength = (int) Math.ceil(this.length / (double) this.model.getBeatsPerMeasure())
            * this.model.getBeatsPerMeasure();
    g2.setFont(new Font("TimesRoman", Font.PLAIN, UNIT));
    for (int i = this.noteRange; i > 0; i--) {
      g2.drawString(curPitch.toString() + Integer.toString(curOctave),
              this.margin - 2 * UNIT,
              (i + 1) * UNIT + this.margin);
      curPitch = curPitch.nextPitch();
      if (curPitch == Pitch.C) {
        curOctave++;
        g2.setStroke(new BasicStroke(3));
        g2.drawLine(this.margin + UNIT, (i + 1) * UNIT + this.margin - UNIT,
                this.margin + measuresLength * UNIT + UNIT,
                (i + 1) * UNIT + this.margin - UNIT);
      }
    }
  }

  /**
   * Draws the measure numbers above the music sheet. These are based off the model's
   * beat per measure.
   * @param g graphics used
   */
  private void drawMeasureNumbers(Graphics g) {
    Graphics2D g2 = (Graphics2D) g;
    g2.setFont(new Font("TimesRoman", Font.PLAIN, UNIT));
    for (int i = 0; i <= Math.ceil(this.length / (double) this.model.getBeatsPerMeasure()); i++) {
      g2.drawString(Integer.toString(i * this.model.getBeatsPerMeasure()),
              i * this.model.getBeatsPerMeasure() * UNIT + this.margin + UNIT,
              this.margin);
    }
  }

  /**
   * Draws the red line for the current beat. This is based off of the model's
   * current beat.
   * @param g graphics used
   */
  private void drawCurrentBeatMarker(Graphics g) {
    Graphics2D g2 = (Graphics2D) g;
    g2.setPaint(Color.RED);
    g2.setStroke(new BasicStroke(2));
    g2.drawLine(this.model.getCurrentBeat() * UNIT + this.margin, this.margin + UNIT,
            this.model.getCurrentBeat() * UNIT + this.margin,
            this.margin + (this.noteRange + 1) * UNIT);
  }

  /**
   * Updates fields when a new note was placed.
   */
  public void updateParams() {
    this.length = model.getLength();
    this.lowestNote = model.lowestNote();
    Note highestNote = model.highestNote();
    int beatsPerMeasure = model.getBeatsPerMeasure();
    this.noteRange = highestNote.noteIndex() - this.lowestNote.noteIndex() + 1;
    this.highestNoteIndex = model.highestNote().noteIndex();
    this.setSize(new Dimension(
            this.margin * 2 + (this.length / beatsPerMeasure)
                    * beatsPerMeasure * UNIT,
            this.margin * 2 + this.noteRange * UNIT));

    this.setPreferredSize(new Dimension(
            this.margin * 2 + (this.length / beatsPerMeasure)
                    * beatsPerMeasure * UNIT,
            this.margin * 2 + this.noteRange * UNIT));
  }



}
