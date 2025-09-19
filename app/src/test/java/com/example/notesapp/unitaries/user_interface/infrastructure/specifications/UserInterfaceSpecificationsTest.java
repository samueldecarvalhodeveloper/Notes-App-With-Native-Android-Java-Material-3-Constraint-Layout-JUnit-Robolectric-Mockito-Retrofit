package com.example.notesapp.unitaries.user_interface.infrastructure.specifications;

import static com.example.notesapp.user_interface.infrastructure.specifications.UserInterfaceSpecifications.isMenuViewIdEqualsToConcludeNoteMenuItem;
import static com.example.notesapp.user_interface.infrastructure.specifications.UserInterfaceSpecifications.isMenuViewIdEqualsToDeleteNoteMenuItem;
import static com.example.notesapp.user_interface.infrastructure.specifications.UserInterfaceSpecifications.isMenuViewIdEqualsToEditNoteMenuItem;
import static com.example.notesapp.user_interface.infrastructure.specifications.UserInterfaceSpecifications.isViewIdEqualsToCreateNoteFloatingActionButton;
import static com.example.notesapp.user_interface.infrastructure.specifications.UserInterfaceSpecifications.isViewIdEqualsToCreateUserButton;
import static com.example.notesapp.user_interface.infrastructure.specifications.UserInterfaceSpecifications.isViewIdEqualsToNoteItemContainer;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import android.view.MenuItem;
import android.view.View;

import com.example.notesapp.R;

import org.junit.Test;

public class UserInterfaceSpecificationsTest {
    @Test
    public void testIfMethodIsViewIdEqualsToCreateNoteFloatingActionButtonReturnsTrueIfViewIdIsEqualToCreateNoteFloatingActionButton() {
        View viewWithIdEqualToCreateNoteFloatingActionButton = mock(View.class);
        View viewWithIdNotEqualToCreateNoteFloatingActionButton = mock(View.class);

        when(viewWithIdEqualToCreateNoteFloatingActionButton.getId())
                .thenReturn(R.id.create_note_floating_action_button);
        when(viewWithIdNotEqualToCreateNoteFloatingActionButton.getId())
                .thenReturn(R.id.edit_note_menu_item);

        boolean viewIdIsEqualToCreateNoteFloatingActionButton =
                isViewIdEqualsToCreateNoteFloatingActionButton(viewWithIdEqualToCreateNoteFloatingActionButton);

        boolean viewIdIsNotEqualToCreateNoteFloatingActionButton =
                isViewIdEqualsToCreateNoteFloatingActionButton(viewWithIdNotEqualToCreateNoteFloatingActionButton);

        assertTrue(viewIdIsEqualToCreateNoteFloatingActionButton);
        assertFalse(viewIdIsNotEqualToCreateNoteFloatingActionButton);
    }

    @Test
    public void testIfMethodIsViewIdEqualsToUserCreationButtonReturnsTrueIfViewIdIsEqualToCreateUserButton() {
        View viewWithIdEqualToUserCreationButton = mock(View.class);
        View viewWithIdNotEqualToUserCreationButton = mock(View.class);

        when(viewWithIdEqualToUserCreationButton.getId())
                .thenReturn(R.id.create_user_button);
        when(viewWithIdNotEqualToUserCreationButton.getId())
                .thenReturn(R.id.create_note_floating_action_button);

        boolean viewIdIsEqualToUserCreationButton =
                isViewIdEqualsToCreateUserButton(viewWithIdEqualToUserCreationButton);

        boolean viewIdIsNotEqualToUserCreationButton =
                isViewIdEqualsToCreateUserButton(viewWithIdNotEqualToUserCreationButton);

        assertTrue(viewIdIsEqualToUserCreationButton);
        assertFalse(viewIdIsNotEqualToUserCreationButton);
    }

    @Test
    public void testIfMethodIsMenuViewIdEqualsToConcludeNoteMenuItemReturnsTrueIfViewIdIsMenuEqualToConcludeNoteMenuItem() {
        MenuItem viewWithIdEqualToConcludeNoteMenuItem = mock(MenuItem.class);
        MenuItem viewWithIdNotEqualToConcludeNoteMenuItem = mock(MenuItem.class);

        when(viewWithIdEqualToConcludeNoteMenuItem.getItemId())
                .thenReturn(R.id.conclude_note_menu_item);
        when(viewWithIdNotEqualToConcludeNoteMenuItem.getItemId())
                .thenReturn(R.id.create_note_floating_action_button);

        boolean viewIdIsEqualToConcludeNoteMenuItem =
                isMenuViewIdEqualsToConcludeNoteMenuItem(viewWithIdEqualToConcludeNoteMenuItem);

        boolean viewIdIsNotEqualToConcludeNoteMenuItem =
                isMenuViewIdEqualsToConcludeNoteMenuItem(viewWithIdNotEqualToConcludeNoteMenuItem);

        assertTrue(viewIdIsEqualToConcludeNoteMenuItem);
        assertFalse(viewIdIsNotEqualToConcludeNoteMenuItem);
    }

    @Test
    public void testIfMethodIsMenuViewIdEqualsToEditNoteMenuItemReturnsTrueIfViewIdIsMenuEqualToEditNoteMenuItem() {
        MenuItem viewWithIdEqualToEditNoteMenuItem = mock(MenuItem.class);
        MenuItem viewWithIdNotEqualToEditNoteMenuItem = mock(MenuItem.class);

        when(viewWithIdEqualToEditNoteMenuItem.getItemId())
                .thenReturn(R.id.edit_note_menu_item);
        when(viewWithIdNotEqualToEditNoteMenuItem.getItemId())
                .thenReturn(R.id.create_note_floating_action_button);

        boolean viewIdIsEqualToEditNoteMenuItem =
                isMenuViewIdEqualsToEditNoteMenuItem(viewWithIdEqualToEditNoteMenuItem);

        boolean viewIdIsNotEqualToEditNoteMenuItem =
                isMenuViewIdEqualsToEditNoteMenuItem(viewWithIdNotEqualToEditNoteMenuItem);

        assertTrue(viewIdIsEqualToEditNoteMenuItem);
        assertFalse(viewIdIsNotEqualToEditNoteMenuItem);
    }

    @Test
    public void testIfMethodIsMenuViewIdEqualsToDeleteNoteMenuItemReturnsTrueIfViewIdIsMenuEqualToToDeleteNoteMenuItem() {
        MenuItem viewWithIdEqualToToDeleteNoteMenuItem = mock(MenuItem.class);
        MenuItem viewWithIdNotEqualToToDeleteNoteMenuItem = mock(MenuItem.class);

        when(viewWithIdEqualToToDeleteNoteMenuItem.getItemId())
                .thenReturn(R.id.delete_note_menu_item);
        when(viewWithIdNotEqualToToDeleteNoteMenuItem.getItemId())
                .thenReturn(R.id.create_note_floating_action_button);

        boolean viewIdIsEqualToToDeleteNoteMenuItem =
                isMenuViewIdEqualsToDeleteNoteMenuItem(viewWithIdEqualToToDeleteNoteMenuItem);

        boolean viewIdIsNotEqualToToDeleteNoteMenuItem =
                isMenuViewIdEqualsToDeleteNoteMenuItem(viewWithIdNotEqualToToDeleteNoteMenuItem);

        assertTrue(viewIdIsEqualToToDeleteNoteMenuItem);
        assertFalse(viewIdIsNotEqualToToDeleteNoteMenuItem);
    }

    @Test
    public void testIfMethodIsViewIdEqualsToNoteItemContainerReturnsTrueIfViewIdIsEqualToNoteItemContainer() {
        View viewWithIdEqualToNoteItemContainer = mock(View.class);
        View viewWithIdNotEqualToNoteItemContainer = mock(View.class);

        when(viewWithIdEqualToNoteItemContainer.getId())
                .thenReturn(R.id.note_item_container);
        when(viewWithIdNotEqualToNoteItemContainer.getId())
                .thenReturn(R.id.create_note_floating_action_button);

        boolean viewIdIsEqualToNoteItemContainer =
                isViewIdEqualsToNoteItemContainer(viewWithIdEqualToNoteItemContainer);

        boolean viewIdIsNotEqualToNoteItemContainer =
                isViewIdEqualsToNoteItemContainer(viewWithIdNotEqualToNoteItemContainer);

        assertTrue(viewIdIsEqualToNoteItemContainer);
        assertFalse(viewIdIsNotEqualToNoteItemContainer);
    }
}
