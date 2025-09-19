package com.example.notesapp.user_interface.view_models;

import android.annotation.SuppressLint;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.notesapp.data.external_models.Note;
import com.example.notesapp.data.repositories.NoteRepository;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;

@SuppressLint("CheckResult")
@HiltViewModel
public class NotesListingViewModel extends ViewModel {
    private final NoteRepository noteRepository;
    private final MutableLiveData<List<Note>> listOfNotes = new MutableLiveData<>();
    private final MutableLiveData<Note> createdNote = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isListOfNotesLoaded = new MutableLiveData<>(false);
    private final MutableLiveData<Boolean> isNoteCreationCurrentlyUnable = new MutableLiveData<>(false);

    @Inject
    public NotesListingViewModel(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    public void fetchNotesFromServer(int userId) {
        isListOfNotesLoaded.setValue(false);

        noteRepository.fetchNotesFromService(userId)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnTerminate(() -> noteRepository.getNotes()
                        .subscribe(notes -> {
                            listOfNotes.postValue(notes);

                            isListOfNotesLoaded.postValue(true);
                        }))
                .onErrorResumeWith(observer -> {
                })
                .subscribe();
    }

    public LiveData<List<Note>> getListOfNotes() {
        return listOfNotes;
    }

    public void createNote(int userId) {
        noteRepository.getCreatedNote("", "", userId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        createdNote::postValue,
                        throwable -> isNoteCreationCurrentlyUnable.postValue(true)
                );
    }

    public LiveData<Note> getCreatedNote() {
        return createdNote;
    }

    public LiveData<Boolean> getIsListOfNotesLoaded() {
        return isListOfNotesLoaded;
    }

    public MutableLiveData<Boolean> getIsNoteCreationCurrentlyUnable() {
        return isNoteCreationCurrentlyUnable;
    }
}