package cs3500.music.controller;

import cs3500.music.MusicEditor;
import cs3500.music.model.MusicEditorModel;
import cs3500.music.model.MusicNote;
import cs3500.music.model.Pitch;
import cs3500.music.view.IViewMain;
import cs3500.music.view.PianoGuiViewPanel;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Created by sahaj on 6/20/2017.
 */
public class MouseHandler implements MouseListener {

    MusicEditorModel model;
    IViewMain view;

    public MouseHandler(MusicEditorModel model, IViewMain view) {
        this.model = model;
        this.view = view;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        double xPos = e.getX();
        double yPos = e.getY();
        if (e.getComponent() instanceof PianoGuiViewPanel) {
            System.out.print("in piano panel");
            this.drawNote();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    public void drawNote() {
        this.model.placeNote(new MusicNote(Pitch.C, 9, 4, 4));
        this.view.reDrawScene();
    }

}
