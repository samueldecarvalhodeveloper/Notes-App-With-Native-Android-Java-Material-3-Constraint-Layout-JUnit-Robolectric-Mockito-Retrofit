package com.example.notesapp.user_interface.events_handlers.text_change_event_handlers;

import android.text.Editable;
import android.text.TextWatcher;

import com.example.notesapp.user_interface.view_models.NoteEditingViewModel;

public class NoteTitleTextInputTextChangeEventHandler implements TextWatcher {
    private final NoteEditingViewModel noteEditingViewModel;

    public NoteTitleTextInputTextChangeEventHandler(NoteEditingViewModel noteEditingViewModel) {
        this.noteEditingViewModel = noteEditingViewModel;
    }

    @Override
    public void beforeTextChanged(
            CharSequence textInputCurrentValue,
            int startCharacterIndex,
            int textInputTextLength,
            int textInputTextUpdatedLength
    ) {
    }

    @Override
    public void onTextChanged(
            CharSequence textInputCurrentValue,
            int startCharacterIndex,
            int oldTextInputTextLength,
            int textInputTextLength
    ) {
        noteEditingViewModel.updateNoteTitle(textInputCurrentValue.toString());
    }

    @Override
    public void afterTextChanged(Editable textInputCurrentValue) {
    }
}
