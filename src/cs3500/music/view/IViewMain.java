package cs3500.music.view;

import cs3500.music.controller.KeyHandler;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;
import javax.swing.*;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;

/**
 * Created by sahaj on 6/18/2017.
 */
public interface IViewMain extends IView {

    void resetFocus();

    void play() throws InvalidMidiDataException, MidiUnavailableException;

    void pause();

    void reDrawScene();

    void setKeyListener(KeyListener listener);

    void setMouseListener(MouseListener listener);

    boolean isPlaying();

    void pack();

    void moveRight();

    void moveLeft();

    void goToStart();
}
