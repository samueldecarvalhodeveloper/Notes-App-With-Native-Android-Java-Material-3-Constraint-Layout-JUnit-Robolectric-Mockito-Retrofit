package com.example.notesapp.unitaries.user_interface.events_handlers.menu_click_event_handlers;

import static com.example.notesapp.constants.data.NoteDataConstants.NOTE_BODY;
import static com.example.notesapp.constants.data.NoteDataConstants.NOTE_ID;
import static com.example.notesapp.constants.data.NoteDataConstants.NOTE_OBJECT;
import static com.example.notesapp.constants.data.NoteDataConstants.NOTE_TITLE;
import static com.example.notesapp.constants.data.UserDataConstants.USER_ID;
import static com.example.notesapp.constants.user_interface.UserInterfaceConstants.NOTE_BODY_BEING_MANIPULATED_FIELD_NAME;
import static com.example.notesapp.constants.user_interface.UserInterfaceConstants.NOTE_FIELD_NAME;
import static com.example.notesapp.constants.user_interface.UserInterfaceConstants.NOTE_TITLE_BEING_MANIPULATED_FIELD_NAME;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.robolectric.Shadows.shadowOf;

import android.content.Intent;
import android.os.Looper;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;

import com.example.notesapp.R;
import com.example.notesapp.concerns.WebServerMock;
import com.example.notesapp.data.repositories.NoteRepository;
import com.example.notesapp.user_interface.activities.MainActivity;
import com.example.notesapp.user_interface.events_handlers.menu_click_event_handlers.NoteActivityMenuClickEventHandler;
import com.example.notesapp.user_interface.view_models.NoteEditingViewModel;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.android.controller.ActivityController;

import java.lang.reflect.Field;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

@RunWith(RobolectricTestRunner.class)
public class NoteActivityMenuClickEventHandlerTest {
    private View view;
    private MenuItem item;
    private NoteEditingViewModel noteEditingViewModel;
    private NoteRepository noteRepository;
    private static Field noteField;
    private static Field noteTitleBeingManipulatedField;
    private static Field noteBodyBeingManipulatedField;

    @BeforeClass
    public static void beforeAll() throws Exception {
        noteTitleBeingManipulatedField =
                NoteEditingViewModel.class.getDeclaredField(NOTE_TITLE_BEING_MANIPULATED_FIELD_NAME);
        noteBodyBeingManipulatedField =
                NoteEditingViewModel.class.getDeclaredField(NOTE_BODY_BEING_MANIPULATED_FIELD_NAME);
        noteField = NoteEditingViewModel.class.getDeclaredField(NOTE_FIELD_NAME);

        noteTitleBeingManipulatedField.setAccessible(true);
        noteBodyBeingManipulatedField.setAccessible(true);
        noteField.setAccessible(true);
    }

    @AfterClass
    public static void afterAll() {
        WebServerMock.stopServer();
    }

    @Before
    public void beforeEach() {
        view = mock(View.class);
        item = mock(MenuItem.class);
        noteRepository = mock(NoteRepository.class);

        noteEditingViewModel = new NoteEditingViewModel(noteRepository);
    }

    @Test
    public void testIfMethodOnMenuItemClickConcludesNoteEditingIfMenuItemIdEqualsToConcludeNoteMenuItem()
            throws Exception {
        when(item.getItemId()).thenReturn(R.id.conclude_note_menu_item);

        when(noteRepository.getUpdatedNote(NOTE_ID, NOTE_TITLE, NOTE_BODY, USER_ID))
                .thenReturn(Single.just(NOTE_OBJECT));

        noteTitleBeingManipulatedField.set(noteEditingViewModel, NOTE_TITLE);
        noteBodyBeingManipulatedField.set(noteEditingViewModel, NOTE_BODY);
        noteField.set(noteEditingViewModel, new MutableLiveData<>(NOTE_OBJECT));

        try (ActivityController<AppCompatActivity> controller = Robolectric.buildActivity(AppCompatActivity.class)) {
            AppCompatActivity mockActivity = controller.create().get();

            NoteActivityMenuClickEventHandler noteActivityMenuClickEventHandler =
                    new NoteActivityMenuClickEventHandler(noteEditingViewModel, mockActivity);

            noteActivityMenuClickEventHandler.onMenuItemClick(item);

            boolean IsNoteBeingManipulated =
                    noteEditingViewModel.getIsNoteBeingManipulated().getValue();

            assertFalse(IsNoteBeingManipulated);
        }
    }

    @Test
    public void testIfMethodOnMenuItemClickTurnsIsNoteBeingManipulatedToTrueIfMenuItemIdEqualsToEditNoteMenuItem() {
        when(item.getItemId()).thenReturn(R.id.edit_note_menu_item);

        try (ActivityController<AppCompatActivity> controller = Robolectric.buildActivity(AppCompatActivity.class)) {
            AppCompatActivity mockActivity = controller.create().get();

            NoteActivityMenuClickEventHandler noteActivityMenuClickEventHandler =
                    new NoteActivityMenuClickEventHandler(noteEditingViewModel, mockActivity);

            noteActivityMenuClickEventHandler.onMenuItemClick(item);

            assertTrue(noteEditingViewModel.getIsNoteBeingManipulated().getValue());
        }
    }

    @Test
    public void testIfMethodOnMenuItemClickDeletesNoteIfMenuItemIdEqualsToDeleteNoteMenuItem()
            throws Exception {
        when(item.getItemId()).thenReturn(R.id.delete_note_menu_item);
        when(noteRepository.deleteNote(NOTE_ID, USER_ID))
                .thenReturn(Completable.complete());

        noteField.set(noteEditingViewModel, new MutableLiveData<>(NOTE_OBJECT));

        try (ActivityController<AppCompatActivity> controller = Robolectric.buildActivity(AppCompatActivity.class)) {
            AppCompatActivity mockActivity = controller.create().get();

            NoteActivityMenuClickEventHandler noteActivityMenuClickEventHandler =
                    new NoteActivityMenuClickEventHandler(noteEditingViewModel, mockActivity);

            noteActivityMenuClickEventHandler.onMenuItemClick(item);

            shadowOf(Looper.getMainLooper()).idle();

            boolean IsNoteDeleted = noteEditingViewModel.getIsNoteDeleted().getValue();

            assertTrue(IsNoteDeleted);
        }
    }

    @Test
    public void testIfMethodOnClickStartsMainActivity() {
        try (ActivityController<AppCompatActivity> controller = Robolectric.buildActivity(AppCompatActivity.class)) {
            AppCompatActivity mockActivity = controller.create().get();

            NoteActivityMenuClickEventHandler noteActivityMenuClickEventHandler =
                    new NoteActivityMenuClickEventHandler(noteEditingViewModel, mockActivity);

            noteActivityMenuClickEventHandler.onClick(view);

            Intent mainActivityIntent = new Intent(mockActivity, MainActivity.class);

            Intent calledActivityIntent = shadowOf(mockActivity).getNextStartedActivity();

            assertEquals(mainActivityIntent.getComponent(), calledActivityIntent.getComponent());

            assertTrue(mockActivity.isFinishing());
        }
    }
}
