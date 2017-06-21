package cs3500.music.view;

import cs3500.music.model.MusicEditorModel;
import cs3500.music.model.Note;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;

/**
 * Created by sahaj on 6/14/2017.
 */

/**
 * A mock of the MidiView implementation. has all the exact same functionality,
 * expcept that it adds notes to log, rather than the music track. This class's
 * purpose is solely for testing.
 */
public class MidiViewMock extends MidiViewImpl {

  /**
   * Constructs a MidiView exactly the same as the class it extends.
   * @param model model view is based off of
   * @throws MidiUnavailableException error if problem during midi processes
   * @throws InvalidMidiDataException error if problem during midi processes
   */
  public MidiViewMock(MusicEditorModel model)
              throws MidiUnavailableException, InvalidMidiDataException {
    super(model);
  }


  /**
   * Overriding of add to track method. Instead of adding notes to music track, as the
   * name suggests, it add messages to the log of what notes it would add. This is for
   * the sake of testing.
   * @param n note being added
   */
  @Override
  protected void addToTrack(Note n) {
    this.log.append("Start: ").append(n.getPitch())
            .append(n.getOctave()).append(" at beat ")
            .append(n.getStartPoint())
            .append("|");
    this.log.append("Stop: ").append(n.getPitch())
            .append(n.getOctave()).append(" at beat ")
            .append(n.getStartPoint() + n.getDuration() - 1)
            .append("\n");
  }

  @Override
  public String log() {
    return this.log.toString();
  }
}
