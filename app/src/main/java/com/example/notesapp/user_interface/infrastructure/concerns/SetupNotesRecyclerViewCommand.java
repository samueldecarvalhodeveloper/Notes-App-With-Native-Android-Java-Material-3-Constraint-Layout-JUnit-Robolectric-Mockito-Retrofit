package com.example.notesapp.user_interface.infrastructure.concerns;

import android.content.Context;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notesapp.data.external_models.Note;
import com.example.notesapp.user_interface.view_adapters.NotesRecyclerViewAdapter;

import java.util.List;

public class SetupNotesRecyclerViewCommand {
    private SetupNotesRecyclerViewCommand() {
    }

    public static void execute(RecyclerView notesRecyclerView, List<Note> listOfNotes, Context context) {
        LinearLayoutManager notesRecyclerViewLinearLayoutManager = new LinearLayoutManager(context);

        notesRecyclerView.setLayoutManager(notesRecyclerViewLinearLayoutManager);

        NotesRecyclerViewAdapter notesRecyclerViewAdapter = new NotesRecyclerViewAdapter(listOfNotes);

        notesRecyclerView.setAdapter(notesRecyclerViewAdapter);
    }
}
