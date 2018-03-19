package cs3500.music.view;

import cs3500.music.model.MusicEditorModel;
import cs3500.music.model.Note;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;

/**
 * The JFrame and GuiFrame that displays the visual for the music editor. This renders
 * both the music sheet, on top, and the piano on the bottom. Includes scrollbars on the
 * top JPanel. Can also control moving of current beat, left and right.
 */
public class GuiViewFrame extends JFrame implements GuiView, KeyListener {

  // model for view
  private MusicEditorModel model;
  // displays music sheet
  private ConcreteGuiViewPanel displayPanel;
  // displays piano
  private JPanel pianoPanel;
  // scroller of sheet
  private JScrollPane scroll;


  /**
   * Creates a GuiView as described above on the class description. Takes in
   * a model to base the view off of.
   * @param model model view is based off of
   */
  public GuiViewFrame(MusicEditorModel model)
          throws InvalidMidiDataException, MidiUnavailableException {
    this.model = model;
    this.displayPanel = new cs3500.music.view.ConcreteGuiViewPanel(this.model);
    this.scroll = new JScrollPane(this.displayPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
            JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    this.pianoPanel = new cs3500.music.view.PianoGuiViewPanel(this.model);
    JSplitPane split = new JSplitPane(JSplitPane.VERTICAL_SPLIT, this.scroll, this.pianoPanel);
    split.setAutoscrolls(true);
    this.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
    this.getContentPane().add(split);
    this.displayPanel.updateParams();


    //this.addKeyListener(this);
    //this.setFocusable(true);
    //this.requestFocus();
    this.pack();
  }

  @Override
  public void initialize() {
    this.setVisible(true);
  }

  // All key interactions are made only through key presses, hence this is empty
  @Override
  public void keyTyped(KeyEvent e) {
    // does nothing, only key presses
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

  // All key interactions are made only through key presses, hence this is empty
  @Override
  public void keyReleased(KeyEvent e) {
    // does nothing only key presses
  }

  @Override
  public void moveRight() {
    try {
      this.model.setCurrentBeat(this.model.getCurrentBeat() + 1);
      if (this.model.getCurrentBeat() * 20 + 140 > this.pianoPanel.getSize().width +
              this.scroll.getHorizontalScrollBar().getValue()) {
        this.scroll.getHorizontalScrollBar().setValue(
                this.scroll.getHorizontalScrollBar().getValue()
                        + this.pianoPanel.getSize().width - 20);
      }
      this.rePaintScene();
    }
    catch (IllegalArgumentException x) {
      x.printStackTrace();
    }
  }

  @Override
  public void moveLeft() {
    try {
      this.model.setCurrentBeat(this.model.getCurrentBeat() - 1);
      if (this.model.getCurrentBeat() * 20
              + 140 < this.scroll.getHorizontalScrollBar().getValue() + 60) {
        this.scroll.getHorizontalScrollBar().setValue(
                this.scroll.getHorizontalScrollBar().getValue()
                        - this.pianoPanel.getSize().width + 20);
      }
      this.rePaintScene();
    }
    catch (IllegalArgumentException x) {
      x.printStackTrace();
    }
  }

  @Override
  public void goToStart() {
    //this.model.setCurrentBeat(1);
    this.scroll.getHorizontalScrollBar().setValue(0);
  }

  @Override
  public void goToEnd() {
    this.scroll.getHorizontalScrollBar()
            .setValue(this.scroll.getHorizontalScrollBar().getMaximum());
  }

  /**
   * Repaints scene after interaction has changed the state of the gui view
   * or the model.
   */
  public void rePaintScene() {

    this.displayPanel.repaint();
    this.pianoPanel.repaint();

  }

  @Override
  public void setKeyListener(KeyListener listener) {
    this.addKeyListener(listener);
    this.setFocusable(true);
    this.requestFocus();
  }

  @Override
  public void setMouseListener(MouseListener listener) {
    this.pianoPanel.addMouseListener(listener);
    this.setFocusable(true);
    this.requestFocus();
  }

  @Override
  public void placeNote(Note n) {
    this.model.placeNote(n);
    this.displayPanel.updateParams();
    this.rePaintScene();
  }


  @Override
  public void activate() {
    this.initialize();
  }
}
