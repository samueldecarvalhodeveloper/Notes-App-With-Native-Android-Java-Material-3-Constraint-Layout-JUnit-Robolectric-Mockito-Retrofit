package com.example.notesapp.constants.user_interface;

public class UserInterfaceConstants {
    public static final String NOTE_ID_INTENT_KEY = "NOTE_ID";

    public static final int NOT_EXISTING_NOTE_ID = 0;

    public static final String NOTE_TITLE_BEING_MANIPULATED_FIELD_NAME = "noteTitleBeingManipulated";

    public static final String NOTE_BODY_BEING_MANIPULATED_FIELD_NAME = "noteBodyBeingManipulated";

    public static final String IS_NOTE_MANIPULATION_UNABLE_FIELD_NAME = "isNoteManipulationUnable";

    public static final String NOTE_FIELD_NAME = "note";

    private UserInterfaceConstants() {
    }

    public static String TOOLBAR_TITLE(String toolbarTitleWithoutUserUsername, String userUsername) {
        return toolbarTitleWithoutUserUsername + " " + userUsername;
    }
}
