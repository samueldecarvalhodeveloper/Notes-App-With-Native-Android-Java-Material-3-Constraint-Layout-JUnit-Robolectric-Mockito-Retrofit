package com.example.notesapp.unitaries.user_interface.fragments;

import static androidx.appcompat.R.style.Theme_AppCompat;
import static com.example.notesapp.constants.data.DomainAgnosticConstants.SERVICE_URL;
import static com.example.notesapp.constants.data.NoteDataConstants.NOTE_BODY;
import static com.example.notesapp.constants.data.NoteDataConstants.NOTE_ENTITY_OBJECT;
import static com.example.notesapp.constants.data.NoteDataConstants.NOTE_ID;
import static com.example.notesapp.constants.data.NoteDataConstants.NOTE_OBJECT;
import static com.example.notesapp.constants.data.NoteDataConstants.NOTE_TITLE;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentFactory;
import androidx.fragment.app.testing.FragmentScenario;

import com.example.notesapp.R;
import com.example.notesapp.data.repositories.NoteRepository;
import com.example.notesapp.user_interface.fragments.NoteEditingFragment;
import com.example.notesapp.user_interface.view_models.NoteEditingViewModel;
import com.google.android.material.textfield.TextInputEditText;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import io.reactivex.rxjava3.core.Single;

@RunWith(RobolectricTestRunner.class)
public class NoteEditingFragmentTest {
    private NoteEditingViewModel noteEditingViewModel;
    private FragmentScenario<NoteEditingFragment> fragmentScenario;

    @Before
    public void beforeEach() {
        NoteRepository noteRepository = mock(NoteRepository.class);

        when(noteRepository.getNote(NOTE_ID))
                .thenReturn(Single.just(NOTE_OBJECT));

        noteEditingViewModel = new NoteEditingViewModel(noteRepository);

        Bundle fragmentArguments = new Bundle();

        FragmentFactory fragmentFactory = new FragmentFactory() {
            @NonNull
            @Override
            public Fragment instantiate(@NonNull ClassLoader classLoader, @NonNull String className) {
                if (className.equals(NoteEditingFragment.class.getName())) {
                    return new NoteEditingFragment(NOTE_ID, noteEditingViewModel);
                }
                return super.instantiate(classLoader, className);
            }
        };

        fragmentScenario = FragmentScenario.launchInContainer(
                NoteEditingFragment.class,
                fragmentArguments,
                Theme_AppCompat,
                fragmentFactory
        );
    }

    @Test
    public void testIfFragmentIsRendered() {
        fragmentScenario.onFragment(noteEditingFragment -> {
            TextInputEditText noteTitleTextInput = noteEditingFragment.getView().findViewById(R.id.note_title_text_input);
            TextInputEditText noteBodyTextInput = noteEditingFragment.getView().findViewById(R.id.note_body_text_input);

            assertEquals(NOTE_TITLE, noteTitleTextInput.getText().toString());
            assertEquals(NOTE_BODY, noteBodyTextInput.getText().toString());
        });
    }
}