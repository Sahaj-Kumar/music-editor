package cs3500.music.view;

import cs3500.music.model.MusicEditorModel;
import cs3500.music.model.Note;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Receiver;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Synthesizer;
import javax.sound.midi.Track;

/**
 * A skeleton for MIDI playback.
 */
public class MidiViewImpl implements MidiView {

  private Sequencer sequencer;
  private Track track;
  private final MusicEditorModel model;
  StringBuilder log;

  /**
   * Constructor for midi view implementation. Takes a sequencer, and builds a track
   * given the model's notes.
   * @param model model used
   * @throws MidiUnavailableException midi error
   * @throws InvalidMidiDataException midi error
   */
  public MidiViewImpl(MusicEditorModel model)
          throws MidiUnavailableException, InvalidMidiDataException {
    this.model = model;
    Synthesizer synth = MidiSystem.getSynthesizer();
    synth.open();
    int resolution = 1;
    this.sequencer = MidiSystem.getSequencer();
    this.sequencer.setTempoInBPM(model.getTempo());
    Sequence sequence = new Sequence(Sequence.PPQ, resolution);
    this.track = sequence.createTrack();
    this.log = new StringBuilder();
    this.makeTrack();
    this.sequencer.setSequence(sequence);
    int midiTempo = (int) (Math.floor((1 / (double) (model.getTempo() / 60)) * 1000000));



  }

  /**
   * Relevant classes and methods from the javax.sound.midi library:
   * <ul>
   *  <li>{@link MidiSystem#getSynthesizer()}</li>
   *  <li>{@link Synthesizer}
   *    <ul>
   *      <li>{@link Synthesizer#open()}</li>
   *      <li>{@link Synthesizer#getReceiver()}</li>
   *      <li>{@link Synthesizer#getChannels()}</li>
   *    </ul>
   *  </li>
   *  <li>{@link Receiver}
   *    <ul>
   *      <li>{@link Receiver#send(MidiMessage, long)}</li>
   *      <li>{@link Receiver#close()}</li>
   *    </ul>
   *  </li>
   *  <li>{@link MidiMessage}</li>
   *  <li>{@link ShortMessage}</li>
   *  <li>{@link MidiChannel}
   *    <ul>
   *      <li>{@link MidiChannel#getProgram()}</li>
   *      <li>{@link MidiChannel#programChange(int)}</li>
   *    </ul>
   *  </li>
   * </ul>
   * @see <a href="https://en.wikipedia.org/wiki/General_MIDI">
   *   https://en.wikipedia.org/wiki/General_MIDI
   *   </a>
   */


  private void makeTrack() throws MidiUnavailableException, InvalidMidiDataException {
    this.sequencer.open();
    for (Note n : model.getNotes()) {
      this.addToTrack(n);
    }
  }

  protected void addToTrack(Note n) throws InvalidMidiDataException {
    MidiMessage startMessage = new ShortMessage(ShortMessage.NOTE_ON, 0, n.noteIndex()  + 24, 64);
    MidiMessage stopMessage = new ShortMessage(ShortMessage.NOTE_OFF, 0, n.noteIndex() + 24, 64);
    MidiEvent startEvent = new MidiEvent(startMessage, n.getStartPoint());
    MidiEvent stopEvent = new MidiEvent(stopMessage, (n.getStartPoint() + n.getDuration() - 1));
    this.track.add(startEvent);
    this.track.add(stopEvent);
  }

  private long toTick(int timeStamp) {
    return 0;

  }

  @Override
  public void play() throws MidiUnavailableException {
    this.sequencer.setTempoInBPM(this.model.getTempo());
    this.sequencer.start();

  }

  @Override
  public void pause() {
    this.sequencer.stop();
    this.sequencer.setTickPosition(this.model.getCurrentBeat());
  }

  @Override
  public void moveRight() {
    this.sequencer.setTickPosition(this.sequencer.getTickPosition() + 1);
  }

  @Override
  public void moveLeft() {
    this.sequencer.setTickPosition(this.sequencer.getTickPosition() - 1);
  }

  @Override
  public void goToStart() {
    this.sequencer.setTickPosition(0);
  }

  @Override
  public long getSequenceBeat() {
    return this.sequencer.getTickPosition() + 1;

  }

  @Override
  public void activate() {
    try {
      this.play();
    }
    catch (MidiUnavailableException x) {
      x.printStackTrace();
    }
  }

  @Override
  public String log() {
    return "";
  }
}
