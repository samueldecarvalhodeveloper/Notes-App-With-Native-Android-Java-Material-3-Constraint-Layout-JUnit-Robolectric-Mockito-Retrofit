package com.example.notesapp.unitaries.user_interface.events_handlers.click_event_handlers;

import static com.example.notesapp.constants.data.NoteDataConstants.NOTE_BODY;
import static com.example.notesapp.constants.data.NoteDataConstants.NOTE_CREATED_AT;
import static com.example.notesapp.constants.data.NoteDataConstants.NOTE_ID;
import static com.example.notesapp.constants.data.NoteDataConstants.NOTE_OBJECT;
import static com.example.notesapp.constants.data.NoteDataConstants.NOTE_TITLE;
import static com.example.notesapp.constants.data.NoteDataConstants.NOTE_UPDATED_AT;
import static com.example.notesapp.constants.data.UserDataConstants.USER_EXTERNAL_MODEL_OBJECT;
import static com.example.notesapp.constants.data.UserDataConstants.USER_ID;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;

import com.example.notesapp.concerns.WebServerMock;
import com.example.notesapp.data.external_models.Note;
import com.example.notesapp.user_interface.events_handlers.click_event_handlers.MainActivityClickEventHandler;
import com.example.notesapp.user_interface.view_models.NotesListingViewModel;
import com.example.notesapp.user_interface.view_models.UserViewModel;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.android.controller.ActivityController;

@RunWith(RobolectricTestRunner.class)
public class MainActivityClickEventHandlerTest {
    private NotesListingViewModel notesListingViewModel;
    private UserViewModel userViewModel;
    private View view;

    @Before
    public void beforeEach() {
        WebServerMock.startServer();

        view = mock(View.class);
        userViewModel = mock(UserViewModel.class);

        notesListingViewModel = mock(NotesListingViewModel.class);
    }

    @Test
    public void testIfMethodOnClickCreatesNoteAndStartsNoteActivityIfViewIdEqualsToCreateNoteFloatingActionButton() {
        when(userViewModel.getUser())
                .thenReturn(new MutableLiveData<>(USER_EXTERNAL_MODEL_OBJECT));

        when(notesListingViewModel.getCreatedNote())
                .thenReturn(new MutableLiveData<>(NOTE_OBJECT));

        try (ActivityController<AppCompatActivity> controller = Robolectric.buildActivity(AppCompatActivity.class)) {
            AppCompatActivity mockActivity = controller.create().get();

            MainActivityClickEventHandler clickEventHandler = new MainActivityClickEventHandler(
                    notesListingViewModel,
                    userViewModel,
                    mockActivity
            );

            clickEventHandler.onClick(view);

            Note createNote = notesListingViewModel.getCreatedNote().getValue();

            assertEquals(NOTE_ID, createNote.id);
            assertEquals(NOTE_TITLE, createNote.title);
            assertEquals(NOTE_BODY, createNote.body);
            assertEquals(NOTE_CREATED_AT, createNote.createdAt);
            assertEquals(NOTE_UPDATED_AT, createNote.updatedAt);
            assertEquals(USER_ID, createNote.userId);
        }
    }
}
