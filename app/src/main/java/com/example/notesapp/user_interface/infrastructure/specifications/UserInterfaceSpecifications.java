package com.example.notesapp.user_interface.infrastructure.specifications;

import android.view.MenuItem;
import android.view.View;

import com.example.notesapp.R;

public class UserInterfaceSpecifications {
    private UserInterfaceSpecifications() {
    }

    public static boolean isViewIdEqualsToCreateNoteFloatingActionButton(View view) {
        return view.getId() == R.id.create_note_floating_action_button;
    }

    public static boolean isViewIdEqualsToCreateUserButton(View view) {
        return view.getId() == R.id.create_user_button;
    }

    public static boolean isMenuViewIdEqualsToConcludeNoteMenuItem(MenuItem item) {
        return item.getItemId() == R.id.conclude_note_menu_item;
    }

    public static boolean isMenuViewIdEqualsToEditNoteMenuItem(MenuItem item) {
        return item.getItemId() == R.id.edit_note_menu_item;
    }

    public static boolean isMenuViewIdEqualsToDeleteNoteMenuItem(MenuItem item) {
        return item.getItemId() == R.id.delete_note_menu_item;
    }

    public static boolean isViewIdEqualsToNoteItemContainer(View view) {
        return view.getId() == R.id.note_item_container;
    }
}
