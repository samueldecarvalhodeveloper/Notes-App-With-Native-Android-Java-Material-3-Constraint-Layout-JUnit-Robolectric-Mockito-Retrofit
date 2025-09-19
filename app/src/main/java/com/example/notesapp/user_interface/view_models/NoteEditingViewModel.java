package com.example.notesapp.user_interface.view_models;

import android.annotation.SuppressLint;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.notesapp.data.external_models.Note;
import com.example.notesapp.data.repositories.NoteRepository;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;

@SuppressLint("CheckResult")
@HiltViewModel
public class NoteEditingViewModel extends ViewModel {
    private final NoteRepository noteRepository;
    private final MutableLiveData<Note> note = new MutableLiveData<>();
    private MutableLiveData<Boolean> isNoteManipulationUnable = new MutableLiveData<>(false);
    private final MutableLiveData<Boolean> isNoteLoaded = new MutableLiveData<>(false);
    private final MutableLiveData<Boolean> isNoteBeingManipulated = new MutableLiveData<>(false);
    private final MutableLiveData<Boolean> isNoteDeleted = new MutableLiveData<>(false);
    private String noteTitleBeingManipulated;
    private String noteBodyBeingManipulated;

    @Inject
    public NoteEditingViewModel(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    public LiveData<Note> getNote(int noteId) {
        if (!note.isInitialized()) {
            noteRepository.getNote(noteId)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(note -> {
                        this.note.setValue(note);

                        noteTitleBeingManipulated = note.title;
                        noteBodyBeingManipulated = note.body;

                        isNoteLoaded.setValue(true);
                    });
        }

        return note;
    }

    public void updateNoteTitle(String title) {
        noteTitleBeingManipulated = title;
    }

    public void updateNoteBody(String body) {
        noteBodyBeingManipulated = body;
    }

    public void manipulateNote() {
        if (isNoteManipulationUnable.getValue()) {
            isNoteBeingManipulated.setValue(false);
        } else {
            isNoteBeingManipulated.setValue(true);
        }
    }

    public void concludeNoteEditing() {
        noteRepository.getUpdatedNote(
                        note.getValue().id,
                        noteTitleBeingManipulated,
                        noteBodyBeingManipulated,
                        note.getValue().userId
                )
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        note -> {
                            isNoteBeingManipulated.setValue(false);

                            this.note.setValue(note);
                        },
                        throwable -> {
                            isNoteManipulationUnable.setValue(true);

                            isNoteBeingManipulated.setValue(false);
                        }
                );
    }

    public void deleteNote() {
        noteRepository.deleteNote(note.getValue().id, note.getValue().userId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        () -> isNoteDeleted.setValue(true),
                        unused -> {
                            isNoteBeingManipulated.setValue(false);

                            isNoteManipulationUnable.setValue(true);
                        }
                );
    }

    public LiveData<Boolean> getIsNoteManipulationUnable() {
        return isNoteManipulationUnable;
    }

    public LiveData<Boolean> getIsNoteDeleted() {
        return isNoteDeleted;
    }

    public LiveData<Boolean> getIsNoteLoaded() {
        return isNoteLoaded;
    }

    public LiveData<Boolean> getIsNoteBeingManipulated() {
        return isNoteBeingManipulated;
    }
}
