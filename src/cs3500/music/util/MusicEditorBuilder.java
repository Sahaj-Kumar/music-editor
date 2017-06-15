package cs3500.music.util;

import cs3500.music.model.*;

/**
 * Created by sahaj on 6/13/2017.
 */
public class MusicEditorBuilder implements CompositionBuilder<MusicEditorModel> {

  MusicEditorModel model;
  String noteType;

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
    int newTempo = (int) Math.floor((tempo / 100000.0) * 500);
    model.setTempo(newTempo);
    return this;
   }

  @Override
  public CompositionBuilder<MusicEditorModel> addNote(
          int start, int end,int instrument, int pitch, int volume) {
    Note n = NoteFactory.buildNote(this.noteType, start, end, instrument, pitch, volume);
    model.placeNote(n);
    return this;
  }
}
