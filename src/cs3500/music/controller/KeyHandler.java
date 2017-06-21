package cs3500.music.controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Map;

/**
 * Created by sahaj on 6/18/2017.
 */
public class KeyHandler implements KeyListener {

    private Map<Integer, Runnable> keyTypedMap;
    private Map<Integer, Runnable> keyPressedMap;
    private Map<Integer, Runnable> keyReleasedMap;

    public KeyHandler() {

    }

    public void setKeyTypeMap(Map<Integer, Runnable> map) {
        keyTypedMap = map;
    }

    public void setKeyPressedMap(Map<Integer, Runnable> map) {
        keyPressedMap = map;
    }

    public void setKeyReleaseMap(Map<Integer, Runnable> map) {
        keyReleasedMap = map;
    }

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
