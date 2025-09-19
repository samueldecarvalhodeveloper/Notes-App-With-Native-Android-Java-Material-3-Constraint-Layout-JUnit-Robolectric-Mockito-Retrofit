package com.example.notesapp.unitaries.user_interface.infrastructure.observer_configurators;

import static android.view.View.VISIBLE;
import static com.example.notesapp.constants.data.NoteDataConstants.NOTE_ENTITY_OBJECT;
import static com.example.notesapp.constants.data.UserDataConstants.USER_EXTERNAL_MODEL_OBJECT;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.MutableLiveData;

import com.example.notesapp.R;
import com.example.notesapp.data.local_data_source.data_access_objects.NoteDataAccessObject;
import com.example.notesapp.data.local_data_source.entities.NoteEntity;
import com.example.notesapp.user_interface.activities.MainActivity;
import com.example.notesapp.user_interface.fragments.NotesFragment;
import com.example.notesapp.user_interface.infrastructure.observer_configurators.MainActivityObserverConfigurator;
import com.example.notesapp.user_interface.view_models.NotesListingViewModel;
import com.example.notesapp.user_interface.view_models.UserViewModel;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import java.util.List;

import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.observers.TestObserver;

@RunWith(RobolectricTestRunner.class)
public class MainActivityObserverConfiguratorTest {
    private ExtendedFloatingActionButton createNoteFloatingActionButton;
    private MainActivity mainActivity;
    private NoteDataAccessObject noteDataAccessObject;
    private UserViewModel userViewModel;
    private NotesListingViewModel notesListingViewModel;

    @Before
    public void beforeEach() {
        createNoteFloatingActionButton = mock(ExtendedFloatingActionButton.class);

        mainActivity = mock(MainActivity.class);

        noteDataAccessObject = mock(NoteDataAccessObject.class);

        userViewModel = mock(UserViewModel.class);
        notesListingViewModel = mock(NotesListingViewModel.class);

    }

    @Test
    public void testIfMethodSetObserversSetsAllViewModelObservers() {
        when(mainActivity.getLifecycle())
                .thenReturn(mock(Lifecycle.class));
        when(userViewModel.getUser())
                .thenReturn(new MutableLiveData<>(USER_EXTERNAL_MODEL_OBJECT));
        when(noteDataAccessObject.getNotes())
                .thenReturn(Single.just(List.of(NOTE_ENTITY_OBJECT)));
        when(mainActivity.getSupportFragmentManager())
                .thenReturn(mock(FragmentManager.class));
        when(mainActivity
                .getSupportFragmentManager()
                .findFragmentById(R.id.notes_fragment_layout)
        )
                .thenReturn(mock(NotesFragment.class));
        when(notesListingViewModel.getIsListOfNotesLoaded())
                .thenReturn(new MutableLiveData<>(true));
        when(notesListingViewModel.getIsNoteCreationCurrentlyUnable())
                .thenReturn(new MutableLiveData<>(true));
        when(createNoteFloatingActionButton.getVisibility()).thenReturn(VISIBLE);

        MainActivityObserverConfigurator.setObservers(
                createNoteFloatingActionButton,
                userViewModel,
                notesListingViewModel,
                mainActivity
        );

        TestObserver<List<NoteEntity>> listOfNotesFromDatabase =
                noteDataAccessObject.getNotes().test();

        listOfNotesFromDatabase.assertValue(noteEntities -> !noteEntities.isEmpty());

        Fragment mainActivityFragmentContainer =
                mainActivity.getSupportFragmentManager().findFragmentById(R.id.notes_fragment_layout);

        assertTrue(mainActivityFragmentContainer instanceof NotesFragment);

        assertEquals(VISIBLE, createNoteFloatingActionButton.getVisibility());
    }
}