package cs3500.music.view;

import cs3500.music.model.MusicEditorModel;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseListener; // Possibly of interest for handling mouse events

import javax.swing.*;

/**
 * A skeleton Frame (i.e., a window) in Swing
 */
public class GuiViewFrame extends javax.swing.JFrame implements GuiView {

  private final MusicEditorModel model;
  private final JPanel displayPanel; // You may want to refine this to a subtype of JPanel

  //private final JScrollPane scrollPanel;

  /**
   * Creates new GuiView
   */
  public GuiViewFrame(MusicEditorModel model) {
    this.model = model;
    this.displayPanel = new cs3500.music.view.ConcreteGuiViewPanel(model);

    this.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
    this.getContentPane().add(displayPanel);

    //this.setSize(new Dimension(500, 500));
    //this.scrollPanel = new JScrollPane(this.displayPanel);
    //this.getContentPane().add(scrollPanel, BorderLayout.CENTER);
    //this.scrollPanel.createHorizontalScrollBar();
    //this.scrollPanel.setVisible(true);
    //this.setLocationRelativeTo(null);

    this.pack();
  }

  @Override
  public void initialize(){
    this.setVisible(true);
  }


  @Override
  public void moveRight() {
    try {
      this.model.setCurrentBeat(this.model.getCurrentBeat() + 1);
    }
    catch (IllegalArgumentException x) {
      x.printStackTrace();
    }
  }

  @Override
  public Dimension getPreferredSize(){
    return new Dimension(100, 100);
  }

}
