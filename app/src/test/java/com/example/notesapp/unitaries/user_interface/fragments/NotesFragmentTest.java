package com.example.notesapp.unitaries.user_interface.fragments;

import static androidx.appcompat.R.style.Theme_AppCompat;
import static com.example.notesapp.constants.data.NoteDataConstants.NOTE_OBJECT;
import static org.junit.Assert.assertEquals;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentFactory;
import androidx.fragment.app.testing.FragmentScenario;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notesapp.R;
import com.example.notesapp.data.external_models.Note;
import com.example.notesapp.user_interface.fragments.NotesFragment;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import java.util.List;

@RunWith(RobolectricTestRunner.class)
public class NotesFragmentTest {
    private FragmentScenario<NotesFragment> fragmentScenario;
    private List<Note> listOfNotes;

    @Before
    public void beforeEach() {
        listOfNotes = List.of(NOTE_OBJECT);

        Bundle fragmentArguments = new Bundle();

        FragmentFactory fragmentFactory = new FragmentFactory() {
            @NonNull
            @Override
            public Fragment instantiate(@NonNull ClassLoader classLoader, @NonNull String className) {
                if (className.equals(NotesFragment.class.getName())) {
                    return new NotesFragment(listOfNotes);
                }
                return super.instantiate(classLoader, className);
            }
        };

        fragmentScenario = FragmentScenario.launchInContainer(
                NotesFragment.class,
                fragmentArguments,
                Theme_AppCompat,
                fragmentFactory
        );
    }

    @Test
    public void testIfFragmentIsRendered() {
        fragmentScenario.onFragment(noteEditingFragment -> {
            RecyclerView notesRecyclerView =
                    noteEditingFragment.getView().findViewById(R.id.notes_recycler_view);

            assertEquals(listOfNotes.size(), notesRecyclerView.getLayoutManager().getChildCount());
        });
    }
}