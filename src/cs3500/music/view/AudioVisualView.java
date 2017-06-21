package cs3500.music.view;

import cs3500.music.controller.KeyHandler;
import cs3500.music.controller.MusicController;
import cs3500.music.controller.MusicControllerImpl;
import cs3500.music.model.MusicEditorModel;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;
import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by sahaj on 6/18/2017.
 */
public class AudioVisualView extends JPanel implements IViewMain {

    private MusicEditorModel model;
    //private KeyListener keyListener;
    public GuiView visual;
    private MidiView midi;
    private boolean playing;
    private Timer timer;

    public AudioVisualView(MusicEditorModel model)
            throws InvalidMidiDataException, MidiUnavailableException {
        //super();
        this.model = model;
        this.visual = new GuiViewFrame(model);
        this.midi = new MidiViewImpl(model);
        this.playing = false;
        this.timer = new Timer();
        //MusicController c = new MusicControllerImpl(this.model, this);
        //KeyListener listener = c.buildKeyHandler();
        //this.addKeyListener(listener);
        //this.setFocusable(true);
        //this.requestFocus();
        //System.out.print(this.getKeyListeners()[0]);
        //this.pack();
        //System.out.print(this.getKeyListeners()[0]);
        //this.activate();
    }

    @Override
    public void activate() throws InvalidMidiDataException, MidiUnavailableException {
        //Timer timer = new Timer();
        this.visual.initialize();
        this.requestFocusInWindow();
        // FIX TEMPO
        //timer.schedule(new playMusic(), 0, (long) model.getTempo() / 10);
        //this.midi.play();
    }

    class playMusic extends TimerTask {
        @Override
        public void run() {
            int musicBeat = (int) Math.floor(midi.getSequenceBeat());
            if (model.getCurrentBeat() < musicBeat) {
              visual.moveRight();
            }
            //model.setCurrentBeat((int) Math.floor(midi.getSequenceBeat()));
            visual.rePaintScene();
        }

    }

    /*
    @Override
    public void moveRight() {
        if (!model.isPlaying()) {
            try {
                this.model.setCurrentBeat(this.model.getCurrentBeat() + 1);

                if (this.model.getCurrentBeat() * 20 + 140 > this.pianoPanel.getSize().width +
                        this.scroll.getHorizontalScrollBar().getValue()) {
                    this.scroll.getHorizontalScrollBar().setValue(
                            this.scroll.getHorizontalScrollBar().getValue()
                                    + this.pianoPanel.getSize().width - 20);
                }

                visual.rePaintScene();
            }
            catch (IllegalArgumentException x) {
                x.printStackTrace();
            }
            midi.moveRight();
        }
    }
    */

    @Override
    public void resetFocus() {
        this.setFocusable(true);
        this.requestFocus();
    }

    public void windowFocus() {
        this.requestFocusInWindow();
    }

    @Override
    public boolean isPlaying() {
        return this.playing;
    }

    @Override
    public void pack() {
        this.visual.pack();
    }
    @Override
    public void moveRight() {
        this.midi.moveRight();
        this.visual.moveRight();
    }

    @Override
    public void moveLeft() {
        this.midi.moveLeft();
        this.visual.moveLeft();
    }

    @Override
    public void goToStart() {
        try {
            this.visual.goToStart();
            this.midi.goToStart();
        }
        catch (IllegalArgumentException x) {
            x.printStackTrace();
        }
    }

    @Override
    public void play() throws InvalidMidiDataException, MidiUnavailableException {

        timer.schedule(new playMusic(), 0, model.getTempo() /  10);
        midi.play();
        this.playing = true;
    }


    @Override
    public void pause() {
        this.playing = false;
        timer.purge();
        midi.pause();
    }

    @Override
    public void reDrawScene() {
        this.visual.rePaintScene();
    }

    @Override
    public void setKeyListener(KeyListener listener) {
        this.visual.setKeyListener(listener);
        //this.addKeyListener(listener);
        //this.setFocusable(true);
        //this.requestFocus();
        this.pack();
    }

    @Override
    public void setMouseListener(MouseListener listener) {
        this.visual.setMouseListener(listener);
        this.pack();
    }

}
