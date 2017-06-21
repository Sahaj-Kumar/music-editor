package cs3500.music.controller;

/**
 * Created by sahaj on 6/18/2017.
 */

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;

/**
 * Represents a model for a controller for the music editor.
 */
public interface MusicController {

    /**
     * Manages the key event, and tell the model what to do based on
     * the event.
     * @param ke the key event.
     */
    void manageKey(KeyEvent ke);

    /**
     * Manages the mouse event, and tells the model what to do base on what
     * is being pressed.
     * @param me the mouse event.
     */
    void manageMouseClick(MouseEvent me);

    void buildAndAssignKeyHandler() throws InvalidMidiDataException, MidiUnavailableException;

    void buildAndAssignMouseHandler();

    KeyListener buildKeyHandler();
}
