package cs3500.music.controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Map;

/**
 * Created by sahaj on 6/18/2017.
 */

/**
 * The KeyHandler for Music Model. Uses the Map design, and thie relies on
 * a Controller to set all its potential commands associated to particular
 * key presses.
 */
public class KeyHandler implements KeyListener {

  // the maps for keys to commands. (Honestly, only uses key presses)
  Map<Integer, Runnable> keyTypedMap;
  Map<Integer, Runnable> keyPressedMap;
  Map<Integer, Runnable> keyReleasedMap;

  /**
   * Empty constructor. The Controller should do all the work.
   */
  public KeyHandler() {
    // does nothing
  }

  /**
   * Sets the key type map to the given.
   * @param map given keytyped map
   */
  void setKeyTypeMap(Map<Integer, Runnable> map) {
    keyTypedMap = map;
  }

  /**
   * Sets the key press map to the given.
   * @param map given keypressed map
   */
  void setKeyPressedMap(Map<Integer, Runnable> map) {
    keyPressedMap = map;
  }

  /**
   * Sets the key release map to the given.
   * @param map given keyrelease map
   */
  void setKeyReleaseMap(Map<Integer, Runnable> map) {
    keyReleasedMap = map;
  }

  // the overridden methods from key listener simply call the appropriate runnable
  // from the map if it exists.

  @Override
  public void keyTyped(KeyEvent e) {
    if (keyTypedMap.containsKey(e.getKeyCode())) {
      keyTypedMap.get(e.getKeyCode()).run();
    }
  }

  @Override
  public void keyPressed(KeyEvent e) {
    if (keyPressedMap.containsKey(e.getKeyCode())) {
      keyPressedMap.get(e.getKeyCode()).run();
    }
  }

  @Override
  public void keyReleased(KeyEvent e) {
    if (keyReleasedMap.containsKey(e.getKeyCode())) {
      keyReleasedMap.get(e.getKeyCode()).run();
    }
  }
}
