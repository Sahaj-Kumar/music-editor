package cs3500.music.view;

import cs3500.music.model.MusicEditorModel;
import cs3500.music.model.Pitch;

import java.awt.*;

/**
 * Created by sahaj on 6/15/2017.
 */
public class MockPianoPanel extends PianoGuiViewPanel {

    private StringBuilder log;

    public MockPianoPanel(MusicEditorModel model) {
        super(model);
        this.log = new StringBuilder();
    }

    @Override
    protected void drawNote(Graphics2D g2, int counter, int i) {
        if (g2.getPaint().equals(Color.ORANGE) || g2.getPaint().equals(Color.CYAN)) {
            Pitch pitch = Pitch.pitchMap.get((i % 12) + 1);
            int octave = i / 12 + 1;
            this.log.append("playing note: ")
                    .append(pitch.toString())
                    .append(octave).append("\n");
            System.out.println(this.log.toString());
        }
    }

    @Override
    public String log() {
        return this.log.toString();
    }
}
