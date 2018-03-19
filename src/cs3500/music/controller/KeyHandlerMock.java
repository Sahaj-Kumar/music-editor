package cs3500.music.controller;

import java.awt.event.KeyEvent;

/**
 * Created by sahaj on 6/23/2017.
 */

/**
 * Serves as a mock for KeyHandler. ONLY USED FOR TESTS. Requires the mock controller
 * to build its maps, and then had a dummy method that returns the name of the runnable
 * class corresponding to the key. This is for testing correct wiring.
 */
public class KeyHandlerMock extends KeyHandler {

  // Log of all runnable called from key presses
  StringBuilder log;

  /**
   * Similar to non-mock KeyHandler, but has a log for testing.
   */
  public KeyHandlerMock() {
    this.log = new StringBuilder();
  }

  @Override
  public void keyTyped(KeyEvent e) {
    if (keyTypedMap.containsKey(e.getKeyCode())) {
      log.append(keyTypedMap.get(e.getKeyCode())).append("\n");
    }
  }

  @Override
  public void keyPressed(KeyEvent e) {
    if (keyPressedMap.containsKey(e.getKeyCode())) {
      keyPressedMap.get(e.getKeyCode()).run();
    }
  }

  /**
   * Behaves similar to keyPresses, but instead of a KeyEvent as parameter, it takes
   * in the int code of the keyEvent. This provides more convenience while testing.
   * @param e int value of key event.
   */
  public void keyPressedMock(int e) {
    if (keyPressedMap.containsKey(e)) {
      log.append(keyPressedMap.get(e)).append("\n");
    }
  }

  @Override
  public void keyReleased(KeyEvent e) {
    if (keyReleasedMap.containsKey(e.getKeyCode())) {
      keyReleasedMap.get(e.getKeyCode()).run();
    }
  }

  /**
   * Returns log of names of all runnables called.
   * @return log of all runnables called.
   */
  public String log() {
    return this.log.toString();
  }
}
