package com.example.notesapp.data.remote_data_source.services;

import com.example.notesapp.data.remote_data_source.gateways.NoteGateway;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class NoteService {
    private static NoteGateway instance = null;

    private NoteService() {
    }

    public static NoteGateway getInstance(String baseUrl) {
        if (instance == null) {
            instance = new Retrofit.Builder().baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                    .build()
                    .create(NoteGateway.class);
        }

        return instance;
    }
}
