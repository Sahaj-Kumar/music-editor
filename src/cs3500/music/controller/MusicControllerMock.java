package cs3500.music.controller;

import cs3500.music.model.MusicEditorModel;
import cs3500.music.view.IViewMain;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by sahaj on 6/21/2017.
 */

/**
 * Mock Music Controller. USED ONLY FOR TESTING. Can call runnable classes through method to test,
 * and build a mock key handler to test wiring.
 */
public class MusicControllerMock extends MusicControllerImpl {

  // log of all runnable hat have ran
  StringBuilder log;

  /**
   * Constructor for mock music controller. Does same things, but has a log.
   * @param model the model
   * @param view the view
   * @throws InvalidMidiDataException error during midi processes
   * @throws MidiUnavailableException error during midi processes
   */
  public MusicControllerMock(MusicEditorModel model, IViewMain view)
          throws InvalidMidiDataException, MidiUnavailableException {
    super();
    this.model = model;
    this.view = view;
    log = new StringBuilder();
  }

  /**
   * Performs command given int code of keyEvent. Used in testing.
   * @param keyEvent int code of key event
   */
  public void performCommand(int keyEvent) {
    if (keyEvent == KeyEvent.VK_RIGHT) {
      new MoveRight().run();
    }
    else if (keyEvent == KeyEvent.VK_SPACE) {
      new PlayPause().run();
    }
    else if (keyEvent == KeyEvent.VK_LEFT) {
      new MoveLeft().run();
    }
    else if (keyEvent == KeyEvent.VK_HOME) {
      new GoToStart().run();
    }
    else {
      new GoToEnd().run();
    }
  }

  /**
   * Method simulating mouse clicks. Allows to test if particular mouse clicks do what we want.
   * @param x mouse x coordinate.
   * @param y mouse y coordinate.
   * @throws InvalidMidiDataException error during midi processes.
   */
  public void mousePress(double x, double y) throws InvalidMidiDataException {
    new NotePlacer().run(x, y);
  }

  /**
   * Builds a mock handler using the given. This helps for testing the wiring of key handling.
   * @param listener mock key handler.
   */
  public void assignKeyHandler(KeyHandlerMock listener) {
    Map<Integer, Runnable> keyTypes = new HashMap<>();
    Map<Integer, Runnable> keyPresses = new HashMap<>();
    Map<Integer, Runnable> keyReleases = new HashMap<>();

    keyPresses.put(KeyEvent.VK_SPACE, new PlayPause());
    keyPresses.put(KeyEvent.VK_RIGHT, new MoveRight());
    keyPresses.put(KeyEvent.VK_LEFT, new MoveLeft());
    keyPresses.put(KeyEvent.VK_HOME, new GoToStart());
    keyPresses.put(KeyEvent.VK_END, new GoToEnd());

    listener.setKeyTypeMap(keyTypes);
    listener.setKeyPressedMap(keyPresses);
    listener.setKeyReleaseMap(keyReleases);
    view.setKeyListener(listener);
  }
}