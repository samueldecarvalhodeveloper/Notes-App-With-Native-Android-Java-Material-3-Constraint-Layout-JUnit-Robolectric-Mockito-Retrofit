package com.example.notesapp.unitaries.user_interface.infrastructure.observer_configurators;

import static com.example.notesapp.constants.data.NoteDataConstants.NOTE_ID;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import android.view.Menu;
import android.view.MenuItem;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.MutableLiveData;

import com.example.notesapp.R;
import com.example.notesapp.user_interface.activities.NoteActivity;
import com.example.notesapp.user_interface.fragments.NoteVisualizingFragment;
import com.example.notesapp.user_interface.infrastructure.observer_configurators.NoteActivityObserverConfigurator;
import com.example.notesapp.user_interface.view_models.NoteEditingViewModel;
import com.google.android.material.appbar.MaterialToolbar;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

@RunWith(RobolectricTestRunner.class)
public class NoteActivityObserverConfiguratorTest {
    private MaterialToolbar noteActivityToolbar;
    private NoteEditingViewModel noteEditingViewModel;
    private NoteActivity noteActivity;

    @Before
    public void beforeEach() {
        noteActivityToolbar = mock(MaterialToolbar.class);
        noteEditingViewModel = mock(NoteEditingViewModel.class);
        noteActivity = mock(NoteActivity.class);
    }

    @Test
    public void testIfMethodSetObserversSetsAllViewModelObservers() {
        when(noteActivity.getLifecycle())
                .thenReturn(mock(Lifecycle.class));
        when(noteEditingViewModel.getIsNoteLoaded())
                .thenReturn(new MutableLiveData<>(true));
        when(noteEditingViewModel.getIsNoteDeleted())
                .thenReturn(new MutableLiveData<>(false));
        when(noteEditingViewModel.getIsNoteBeingManipulated())
                .thenReturn(new MutableLiveData<>(false));
        when(noteEditingViewModel.getIsNoteManipulationUnable())
                .thenReturn(new MutableLiveData<>(false));
        when(noteActivity.getSupportFragmentManager())
                .thenReturn(mock(FragmentManager.class));
        when(noteActivity
                .getSupportFragmentManager()
                .findFragmentById(R.id.note_handling_fragment)
        ).thenReturn(mock(NoteVisualizingFragment.class));
        when(noteActivityToolbar.getMenu())
                .thenReturn(mock(Menu.class));
        when(noteActivityToolbar.getMenu().getItem(0))
                .thenReturn(mock(MenuItem.class));
        when(noteActivityToolbar.getMenu().getItem(1))
                .thenReturn(mock(MenuItem.class));
        when(noteActivityToolbar.getMenu().getItem(2))
                .thenReturn(mock(MenuItem.class));
        when(noteActivityToolbar.getMenu().getItem(0).isVisible())
                .thenReturn(false);
        when(noteActivityToolbar.getMenu().getItem(1).isVisible())
                .thenReturn(true);
        when(noteActivityToolbar.getMenu().getItem(2).isVisible())
                .thenReturn(true);

        NoteActivityObserverConfigurator.setObservers(
                NOTE_ID,
                noteActivityToolbar,
                noteEditingViewModel,
                noteActivity
        );

        Fragment activityFragmentContainer = noteActivity
                .getSupportFragmentManager()
                .findFragmentById(R.id.note_handling_fragment);

        assertTrue(activityFragmentContainer instanceof NoteVisualizingFragment);
        assertFalse(noteActivityToolbar.getMenu().getItem(0).isVisible());
        assertTrue(noteActivityToolbar.getMenu().getItem(1).isVisible());
        assertTrue(noteActivityToolbar.getMenu().getItem(2).isVisible());
    }
}
