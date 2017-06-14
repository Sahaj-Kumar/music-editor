package cs3500.music.view;

import cs3500.music.model.MusicEditorModel;
import cs3500.music.model.Note;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sahaj on 6/13/2017.
 */
public class PianoGuiViewPanel extends JPanel {

    final static int UNIT = 20;

    private MusicEditorModel model;
    private List<Integer> activeKeys;
    private int lowestIndex;
    private int highestIndex;
    private int margin;

    public PianoGuiViewPanel(MusicEditorModel model) {
        this.model = model;
        this.activeKeys = new ArrayList<Integer>();
        for (Note n : model.currentNotes()) {
            this.activeKeys.add(n.copy().noteIndex());
        }
        this.lowestIndex = model.lowestNote().noteIndex();
        this.highestIndex = model.highestNote().noteIndex();
        this.margin = UNIT * 5;
    }

    @Override
    public void paintComponent(Graphics g){
        // Handle the default painting
        super.paintComponent(g);
        // Look for more documentation about the Graphics class,
        // and methods on it that may be useful
        //g.drawString("Hello World", 25, 25);
        this.drawPiano(g);
    }

    public void drawPiano(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        int counter = 0;

        for (int i = 1; i <= 120; i++) {
            if (this.isWhiteKey(i)) {
                counter++;
                if (this.activeKeys.contains(i)) {
                  g2.setPaint(Color.ORANGE);
                }
                else {
                  g2.setPaint(Color.WHITE);
                }

                g2.fill(new Rectangle2D.Double(this.margin + (counter - 1) * UNIT, this.margin,
                        UNIT, 10 * UNIT));
                g2.setPaint(Color.BLACK);
                g2.drawRect(this.margin + (counter - 1) * UNIT, this.margin,
                        UNIT, 10 * UNIT);
            }
        }
        counter = 0;
        for (int i = 1; i <= 120; i++) {
            if (this.isWhiteKey(i)) {
                counter++;
            }
            else {
                if (this.activeKeys.contains(i)) {
                    g2.setPaint(Color.CYAN);
                } else {
                    g2.setPaint(Color.BLACK);
                }

                g2.fill(new Rectangle2D.Double(this.margin + counter * UNIT - (UNIT / 4), this.margin,
                        UNIT / 2, 5 * UNIT));
                g2.setPaint(Color.BLACK);
                g2.drawRect(this.margin + counter * UNIT - (UNIT / 4), this.margin,
                        UNIT / 2, 5 * UNIT);            }
        }
    }

    private boolean isWhiteKey(int i) {
        int pitchInd = Math.floorMod(i, 12);
        return pitchInd != 2 && pitchInd != 4 &&
                pitchInd != 7 && pitchInd != 9 && pitchInd != 11;
    }

}
