package cs3500.music.controller;

import cs3500.music.MusicEditor;
import cs3500.music.view.GuiView;
import cs3500.music.view.MidiView;

import java.util.Scanner;

/**
 * Created by sahaj on 6/12/2017.
 */
public class MusicControllerImpl implements MusicController {

    private MusicEditor model;
    private MidiView midiView;
    private GuiView guiView;

    @Override
    public void processCommand(String commands) {
        Scanner scan = new Scanner(commands);
        while (scan.hasNext()) {
            String comm = scan.next();
            switch (comm) {
                case "play":
                    this.playMusic();
                    break;
                case "pause":
                    this.pauseMusic();
                    break;
                case "left":
                    this.moveLeft();
                    break;
                case "right":
                    this.moveRight();
                    break;
                default:
                    throw new IllegalArgumentException("invalid input");
            }
        }

    }

    public void playMusic() {

    }

    public void pauseMusic() {

    }

    public void moveLeft() {

    }

    public void moveRight() {

    }
}
