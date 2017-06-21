package cs3500.music.controller;

import cs3500.music.model.MusicEditorModel;
import cs3500.music.view.IView;
import cs3500.music.view.IViewMain;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by sahaj on 6/18/2017.
 */


public class MusicControllerImpl implements MusicController {

    MusicEditorModel model;
    IViewMain view;

    public MusicControllerImpl(MusicEditorModel model, IViewMain view)
            throws InvalidMidiDataException, MidiUnavailableException {
        this.model = model;
        this.view = view;
        this.buildAndAssignMouseHandler();
        this.buildAndAssignKeyHandler();
        //this.view.activate();
        //this.view.windowFocus();
    }

    public void buildAndAssignKeyHandler() throws InvalidMidiDataException, MidiUnavailableException {
        Map<Integer, Runnable> keyTypes = new HashMap<>();
        Map<Integer, Runnable> keyPresses = new HashMap<>();
        Map<Integer, Runnable> keyReleases = new HashMap<>();

        keyPresses.put(KeyEvent.VK_SPACE, new PlayPause());
        keyPresses.put(KeyEvent.VK_RIGHT, new MoveRight());
        keyPresses.put(KeyEvent.VK_LEFT, new MoveLeft());
        keyPresses.put(KeyEvent.VK_BACK_SPACE, new GoToStart());

        KeyHandler keyHandler = new KeyHandler();
        keyHandler.setKeyTypeMap(keyTypes);
        keyHandler.setKeyPressedMap(keyPresses);
        keyHandler.setKeyReleaseMap(keyReleases);


        view.setKeyListener(keyHandler);

    }

    @Override
    public void buildAndAssignMouseHandler() {
        MouseListener listener = new MouseHandler(this.model, this.view);
        view.setMouseListener(listener);
    }

    // FIXME LOL NAH JUST REMEMBER THIS DOESN'T DO ANYTHING
    @Override
    public KeyListener buildKeyHandler() {
        Map<Integer, Runnable> keyTypes = new HashMap<>();
        Map<Integer, Runnable> keyPresses = new HashMap<>();
        Map<Integer, Runnable> keyReleases = new HashMap<>();

        keyPresses.put(KeyEvent.VK_SPACE, new PlayPause());
        keyPresses.put(KeyEvent.VK_RIGHT, new MoveRight());
        keyPresses.put(KeyEvent.VK_LEFT, new MoveLeft());

        KeyHandler keyHandler = new KeyHandler();
        keyHandler.setKeyTypeMap(keyTypes);
        keyHandler.setKeyPressedMap(keyPresses);
        keyHandler.setKeyReleaseMap(keyReleases);

        return keyHandler;
    }

    @Override
    public void manageKey(KeyEvent ke) {
        
    }

    @Override
    public void manageMouseClick(MouseEvent me) {

    }

    class PlayPause implements Runnable {
        @Override
        public void run() {
            if (view.isPlaying()) {
                try {
                    view.pause();
                    view.reDrawScene();
                }
                catch (IllegalArgumentException x) {
                    x.printStackTrace();
                }
                view.resetFocus();
            }
            else {
                try {
                    view.play();
                    view.reDrawScene();
                } catch (IllegalArgumentException | MidiUnavailableException | InvalidMidiDataException e) {
                    e.printStackTrace();
                }
                //view.resetFocus();
            }
        }
    }

    class MoveRight implements Runnable {
        @Override
        public void run() {
            if (!view.isPlaying()) {
                try {
                    //model.setCurrentBeat(model.getCurrentBeat() + 1);
                    view.moveRight();
                    view.reDrawScene();
                }
                catch (IllegalArgumentException x) {
                    x.printStackTrace();
                }
            }
            //view.resetFocus();
        }
    }

    class MoveLeft implements Runnable {
        @Override
        public void run() {
            if (!view.isPlaying()) {
                try {
                    //model.setCurrentBeat(model.getCurrentBeat() - 1);
                    view.moveLeft();
                    view.reDrawScene();
                }
                catch (IllegalArgumentException x) {
                    x.printStackTrace();
                }
            }
            //view.resetFocus();
        }
    }

    class GoToStart implements Runnable {

        @Override
        public void run() {
            if (!view.isPlaying()) {
                view.goToStart();
                view.reDrawScene();
            }
            //view.resetFocus();
        }
    }
}
