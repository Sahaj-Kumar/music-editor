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
  private final Receiver receiver;


  int resolution;
  private Sequencer sequencer;
  private Sequence sequence;
  private Track track;


  private final MusicEditorModel model;
  private int midiTempo;

  public MidiViewImpl(MusicEditorModel model) throws MidiUnavailableException, InvalidMidiDataException {
    this.model = model;
    this.synth = MidiSystem.getSynthesizer();
    this.receiver = synth.getReceiver();
    this.synth.open();

    this.resolution = 1;
    this.sequencer = MidiSystem.getSequencer();
    this.sequencer.setTempoInBPM(model.getTempo());
    this.sequence = new Sequence(Sequence.PPQ, this.resolution);
    this.track = sequence.createTrack();
    this.makeTrack();
    this.sequencer.setSequence(this.sequence);




    this.midiTempo = (int) (Math.floor((1 / (double) (model.getTempo() / 60)) * 1000000));
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

  public void playNote() throws InvalidMidiDataException {
    MidiMessage start = new ShortMessage(ShortMessage.NOTE_ON, 0, 60, 64);
    MidiMessage stop = new ShortMessage(ShortMessage.NOTE_OFF, 0, 60, 64);
    this.receiver.send(start, -1);
    this.receiver.send(stop, this.synth.getMicrosecondPosition() + 200000);
    MidiMessage start2 = new ShortMessage(ShortMessage.NOTE_ON, 0, 64, 64);
    MidiMessage stop2 = new ShortMessage(ShortMessage.NOTE_OFF, 0, 64, 64);
    this.receiver.send(start2, -1);
    this.receiver.send(stop2, this.synth.getMicrosecondPosition() + 200000);
    MidiMessage start3 = new ShortMessage(ShortMessage.NOTE_ON, 0, 67, 64);
    MidiMessage stop3 = new ShortMessage(ShortMessage.NOTE_OFF, 0, 67, 64);
    this.receiver.send(start3, -1);
    this.receiver.send(stop3, this.synth.getMicrosecondPosition() + 200000);


    /* 
    The receiver does not "block", i.e. this method
    immediately moves to the next line and closes the 
    receiver without waiting for the synthesizer to 
    finish playing. 
    
    You can make the program artificially "wait" using
    Thread.sleep. A better solution will be forthcoming
    in the subsequent assignments.
    */
    this.receiver.close(); // Only call this once you're done playing *all* notes
  }

  private void makeTrack() throws InvalidMidiDataException, MidiUnavailableException {
    this.sequencer.open();
    for (Note n : model.getNotes()) {
      MidiMessage startMessage = new ShortMessage(ShortMessage.NOTE_ON, 0, n.noteIndex(), 64);
      MidiMessage stopMessage = new ShortMessage(ShortMessage.NOTE_OFF, 0, n.noteIndex(), 64);
      MidiEvent startEvent = new MidiEvent(startMessage, n.getStartPoint() * 500);
      MidiEvent stopEvent = new MidiEvent(stopMessage, (n.getStartPoint() + n.getDuration() - 1) * 500);
      this.track.add(startEvent);
      this.track.add(stopEvent);
    }
  }

  private long toTick(int timeStamp) {
    return 0;

  }

  @Override
  public void play() throws MidiUnavailableException {
    this.sequencer.start();
  }

  @Override
  public void pause() {
    this.sequencer.stop();
  }

  @Override
  public void rewind() {
    this.sequencer.setTickPosition(this.sequencer.getTickPosition() - 1);
  }

  @Override
  public void fastForward() {

  }
}
