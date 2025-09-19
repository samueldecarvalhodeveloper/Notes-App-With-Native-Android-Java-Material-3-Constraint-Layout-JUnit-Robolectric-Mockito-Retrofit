package com.example.notesapp.user_interface.view_holders;

import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

public class NoteItemViewHolder extends ViewHolder {
    private final LinearLayout root;
    private final TextView noteItemBodyText;
    private final TextView noteItemTitleText;

    public NoteItemViewHolder(
            LinearLayout root,
            @NonNull TextView noteItemBodyText,
            @NonNull TextView noteItemTitleText
    ) {
        super(root);

        this.root = root;

        this.noteItemBodyText = noteItemBodyText;
        this.noteItemTitleText = noteItemTitleText;
    }

    public void setTitle(String title) {
        noteItemTitleText.setText(title);
    }

    public void setBody(String body) {
        noteItemBodyText.setText(body);
    }

    public void setOnClickListener(OnClickListener event) {
        root.setOnClickListener(event);
    }
}
