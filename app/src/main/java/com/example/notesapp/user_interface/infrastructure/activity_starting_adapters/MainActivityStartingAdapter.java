package com.example.notesapp.user_interface.infrastructure.activity_starting_adapters;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import com.example.notesapp.user_interface.activities.MainActivity;

public class MainActivityStartingAdapter {
    private MainActivityStartingAdapter() {
    }

    public static void startMainActivity(AppCompatActivity currentActivity) {
        Intent mainActivityIntent = new Intent(currentActivity, MainActivity.class);

        currentActivity.startActivity(mainActivityIntent);

        currentActivity.finish();
    }
}
