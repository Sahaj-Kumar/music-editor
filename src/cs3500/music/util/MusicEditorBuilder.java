package cs3500.music.util;

import cs3500.music.model.EditorModel;
import cs3500.music.model.MusicEditorModel;
import cs3500.music.model.Note;

/**
 * Created by sahaj on 6/13/2017.
 */

/**
 * Implementation of composition builder that models of type music editor model. Has
 * a model input and type of note to be added. Can set tempo, add notes, and build the
 * model so it can be used.
 */
public class MusicEditorBuilder implements CompositionBuilder<MusicEditorModel> {

  // model to be built
  MusicEditorModel model;
  // name of type of note to be used
  String noteType;

  /**
   * Constructs a music editor builder given the model, and name of note type.
   * This contructor defaults to two types implemented thus far.
   */
  public MusicEditorBuilder() {
    this.model = new EditorModel();
    this.noteType = "MusicNote";
  }

  @Override
  public MusicEditorModel build() {
    return this.model;
  }

  @Override
  public CompositionBuilder<MusicEditorModel> setTempo(int tempo) {
    int newTempo = (int) Math.floor(1.0 / ((tempo / 1000000.0) / 60.0));
    model.setTempo(newTempo);
    return this;
  }

  @Override
  public CompositionBuilder<MusicEditorModel> addNote(
          int start, int end,int instrument, int pitch, int volume) {
    try {
      Note n = NoteFactory.buildNote(this.noteType, start, end, instrument, pitch, volume);
      model.placeNote(n);
    }
    catch (IllegalArgumentException x) {
      x.printStackTrace();
    }
    return this;
  }
}
