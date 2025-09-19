package com.example.notesapp.user_interface.view_adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView.Adapter;

import com.example.notesapp.data.external_models.Note;
import com.example.notesapp.databinding.NoteItemLayoutBinding;
import com.example.notesapp.user_interface.events_handlers.click_event_handlers.NotesRecyclerViewNoteItemClickEventHandler;
import com.example.notesapp.user_interface.view_holders.NoteItemViewHolder;

import java.util.List;

public class NotesRecyclerViewAdapter extends Adapter<NoteItemViewHolder> {
    private final List<Note> notes;

    public NotesRecyclerViewAdapter(List<Note> notes) {
        this.notes = notes;
    }

    @NonNull
    @Override
    public NoteItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        NoteItemLayoutBinding binding = NoteItemLayoutBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false
        );

        return new NoteItemViewHolder(
                binding.getRoot(),
                binding.noteItemBodyText,
                binding.noteItemTitleText
        );
    }

    @Override
    public void onBindViewHolder(@NonNull NoteItemViewHolder holder, int position) {
        Note note = notes.get(position);

        NotesRecyclerViewNoteItemClickEventHandler notesRecyclerViewNoteItemClickEventHandler =
                new NotesRecyclerViewNoteItemClickEventHandler(note.id);

        holder.setTitle(note.title);
        holder.setBody(note.body);

        holder.setOnClickListener(notesRecyclerViewNoteItemClickEventHandler);
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }
}
