package cs3500.music.view;

import cs3500.music.model.MusicEditorModel;
import cs3500.music.model.Note;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

/**
 * Created by sahaj on 6/13/2017.
 */

/**
 * JPanel view for piano. Draws piano, and highlightes key that ar currently
 * being played based off information in the model.
 */
public class PianoGuiViewPanel extends JPanel {

  final static int UNIT = 20;

  private MusicEditorModel model;
  //private List<Integer> activeKeys;

  private int margin;

  /**
   * Constructes a piano guiview panel. Based off current state of given model.
   * @param model model used.
   */
  public PianoGuiViewPanel(MusicEditorModel model) {
    this.model = model;
    this.margin = UNIT * 2;
    this.setPreferredSize(new Dimension(this.margin * 14, this.margin * 2));
  }

  @Override
  protected void paintComponent(Graphics g) {
    // Handle the default painting
    super.paintComponent(g);
    this.drawPiano(g);
  }

  private void drawPiano(Graphics g) {
    ArrayList<Integer> curNotes = new ArrayList<Integer>();
    for (Note n : model.currentNotes()) {
      curNotes.add(n.copy().noteIndex());
    }
    Graphics2D g2 = (Graphics2D) g;
    int counter = 0;
    for (int i = 1; i <= 120; i++) {
      if (this.isWhiteKey(i)) {
        counter++;
        if (curNotes.contains(i)) {
          g2.setPaint(Color.ORANGE);
        }
        else {
          g2.setPaint(Color.WHITE);
        }
        this.drawNote(g2, counter, i);
      }
    }
    counter = 0;
    for (int i = 1; i <= 120; i++) {
      if (this.isWhiteKey(i)) {
        counter++;
      }
      else {
        if (curNotes.contains(i)) {
          g2.setPaint(Color.CYAN);
        }
        else {
          g2.setPaint(Color.BLACK);
        }
        this.drawNote(g2, counter, i);
      }
    }
  }

  protected void drawNote(Graphics2D g2, int counter, int i) {
    if (this.isWhiteKey(i)) {
      g2.fill(new Rectangle2D.Double(this.margin + (counter - 1) * UNIT, this.margin,
               UNIT, 10 * UNIT));
      g2.setPaint(Color.BLACK);
      g2.drawRect(this.margin + (counter - 1) * UNIT, this.margin,
               UNIT, 10 * UNIT);
    }
    else {
      g2.fill(new Rectangle2D.Double(this.margin + counter * UNIT - (UNIT / 4), this.margin,
            UNIT / 2, 5 * UNIT));
      g2.setPaint(Color.BLACK);
      g2.drawRect(this.margin + counter * UNIT - (UNIT / 4), this.margin,
          UNIT / 2, 5 * UNIT);
    }
  }

  private boolean isWhiteKey(int i) {
    int pitchInd = Math.floorMod(i, 12);
    return pitchInd != 2 && pitchInd != 4 &&
            pitchInd != 7 && pitchInd != 9 && pitchInd != 11;
  }

  protected String log() {
    return "";
  }
}
