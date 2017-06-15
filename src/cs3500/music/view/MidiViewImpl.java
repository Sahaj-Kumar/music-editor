package cs3500.music.view;

import cs3500.music.MusicEditor;
import cs3500.music.model.MusicEditorModel;
import cs3500.music.model.Note;

import javax.sound.midi.*;

/**
 * A skeleton for MIDI playback
 */
public class MidiViewImpl implements MidiView {
  private final Synthesizer synth;
//  private final Receiver receiver;

  int resolution;
  private Sequencer sequencer;
  private Sequence sequence;
  private Track track;
  private final MusicEditorModel model;
  private int midiTempo;
  protected StringBuilder log;

  public MidiViewImpl(MusicEditorModel model) throws MidiUnavailableException, InvalidMidiDataException {
    this.model = model;
    this.synth = MidiSystem.getSynthesizer();
    this.synth.open();
    this.resolution = 1;
    this.sequencer = MidiSystem.getSequencer();
    this.sequencer.setTempoInBPM(model.getTempo());
    this.sequence = new Sequence(Sequence.PPQ, this.resolution);
    this.track = sequence.createTrack();
    this.log = new StringBuilder();
    this.makeTrack();
    this.sequencer.setSequence(this.sequence);
    this.midiTempo = (int) (Math.floor((1 / (double) (model.getTempo() / 60)) * 1000000));
  }

  public MidiViewImpl(MusicEditorModel model, boolean test) throws MidiUnavailableException {
    this.model = model;
    this.synth = MidiSystem.getSynthesizer();
    //this.track = new MockTrack();
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
    MidiMessage startMessage = new ShortMessage(ShortMessage.NOTE_ON, 0, n.noteIndex(), 64);
    MidiMessage stopMessage = new ShortMessage(ShortMessage.NOTE_OFF, 0, n.noteIndex(), 64);
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
    this.sequencer.start();
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
