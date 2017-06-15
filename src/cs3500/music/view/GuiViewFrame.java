package cs3500.music.view;

import cs3500.music.model.MusicEditorModel;
import org.w3c.dom.css.Rect;

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
  private Rectangle visibleRect;


  /**
   * Creates new GuiView
   */
  public GuiViewFrame(MusicEditorModel model) {
    this.model = model;
    this.displayPanel = new cs3500.music.view.ConcreteGuiViewPanel(this.model);
    this.scroll = new JScrollPane(this.displayPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
            JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    this.visibleRect = this.scroll.getVisibleRect();
    this.pianoPanel = new cs3500.music.view.PianoGuiViewPanel(this.model);
    this.split = new JSplitPane(JSplitPane.VERTICAL_SPLIT, this.scroll, this.pianoPanel);
    this.split.setAutoscrolls(true);

    this.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
    this.getContentPane().add(this.split);
    this.addKeyListener(this);
    this.setFocusable(true);
    this.requestFocus();
    this.pack();
  }

  public GuiViewFrame(MusicEditorModel model, boolean mock) {
    this.model = model;
    this.displayPanel = new cs3500.music.view.ConcreteGuiViewPanel(this.model);
    this.scroll = new JScrollPane(this.displayPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
            JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    this.visibleRect = this.scroll.getVisibleRect();
    this.pianoPanel = new cs3500.music.view.MockPianoPanel(this.model);
    this.split = new JSplitPane(JSplitPane.VERTICAL_SPLIT, this.scroll, this.pianoPanel);
    this.split.setAutoscrolls(true);

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

  /**
   * Handle the key-pressed event from the text field.
   */
  @Override
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
      if (this.model.getCurrentBeat() * 20 + 140 > this.pianoPanel.getSize().width +
              this.scroll.getHorizontalScrollBar().getValue()) {
        this.scroll.getHorizontalScrollBar().setValue(
                this.scroll.getHorizontalScrollBar().getValue() + this.pianoPanel.getSize().width - 20);
      }
      this.rePaintScene();
    }
    catch (IllegalArgumentException x) {
      x.printStackTrace();
    }
  }


  public void moveLeft() {
    try {
      this.model.setCurrentBeat(this.model.getCurrentBeat() - 1);
      if (this.model.getCurrentBeat() * 20 + 140 < this.scroll.getHorizontalScrollBar().getValue() + 60) {
        this.scroll.getHorizontalScrollBar().setValue(
                this.scroll.getHorizontalScrollBar().getValue() - this.pianoPanel.getSize().width + 20);
      }
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
