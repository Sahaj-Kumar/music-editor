package cs3500.music.view;

/**
 * Created by sahaj on 6/12/2017.
 */

import java.awt.event.KeyListener;
import java.awt.event.MouseListener;

/**
 * The interface for a GuiView, which means a visual view. It should
 * basically be able to initialize the scene given a particular model,
 * and given the music editor model, perform right and left controls.
 */
public interface GuiView extends IView {

  /**
   * Initializes the scene. Basically means make it visible for our
   * lovely eyes to see.
   */
  void initialize();

  /**
   * Perform the action of moving right. This means, moving the current beat
   * marker one beat to the right as long as the model permits it. Should
   * update scrollbar if it is relevant or necessary.
   */
  void moveRight();

  /**
   * Perform the action of moving left. This means, moving the current beat
   * marker one beat to the left as long as the model permits it. Should update
   * scrollbar if it is relevant or necessary.
   */
  void moveLeft();

  void goToStart();

  void rePaintScene();

  void pack();

  void setKeyListener(KeyListener listener);

  void setMouseListener(MouseListener listner);
}
