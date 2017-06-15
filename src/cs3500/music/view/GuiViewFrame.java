package cs3500.music.view;

import cs3500.music.model.MusicEditorModel;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

/**
 * A skeleton Frame (i.e., a window) in Swing
 */
public class GuiViewFrame extends JFrame implements GuiView, KeyListener {

  private MusicEditorModel model;
  private JPanel displayPanel; // You may want to refine this to a subtype of JPanel
  private JPanel pianoPanel;
  private JSplitPane split;
  private JScrollPane scroll;
  private ActionListener al;


  /**
   * Creates new GuiView
   */
  public GuiViewFrame(MusicEditorModel model) {
    this.model = model;
    this.displayPanel = new cs3500.music.view.ConcreteGuiViewPanel(this.model);
    this.scroll = new JScrollPane(this.displayPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
            JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    this.pianoPanel = new cs3500.music.view.PianoGuiViewPanel(this.model);
    this.split = new JSplitPane(JSplitPane.VERTICAL_SPLIT, this.scroll, this.pianoPanel);

    this.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
    this.getContentPane().add(this.split);
    this.addKeyListener(this);
    this.setFocusable(true);
    this.requestFocus();
    this.pack();
  }

  @Override
  public void initialize(){
    this.setVisible(true);
  }

  @Override
  public void keyTyped(KeyEvent e) {

  }

  @Override
  /** Handle the key-pressed event from the text field. */
  public void keyPressed(KeyEvent e) {
    switch (e.getKeyCode()) {
      case KeyEvent.VK_RIGHT:
        this.moveRight();
        break;
      case KeyEvent.VK_LEFT:
        this.moveLeft();
        break;
      default:
        // do nothing
    }
  }

  @Override
  public void keyReleased(KeyEvent e) {

  }

  @Override
  public void moveRight() {

    try {
      this.model.setCurrentBeat(this.model.getCurrentBeat() + 1);
      this.rePaintScene();
    }
    catch (IllegalArgumentException x) {
      x.printStackTrace();
    }
  }


  public void moveLeft() {
    try {
      this.model.setCurrentBeat(this.model.getCurrentBeat() - 1);
      this.rePaintScene();
    }
    catch (IllegalArgumentException x) {
      x.printStackTrace();
    }
  }

  private void rePaintScene() {
    this.displayPanel.repaint();
    this.pianoPanel.repaint();
  }

  @Override
  public void activate() {
    this.initialize();
  }
}
