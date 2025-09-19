package com.example.notesapp.data.remote_data_source.services;

import com.example.notesapp.data.remote_data_source.gateways.UserGateway;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserService {
    private static UserGateway instance = null;

    private UserService() {
    }

    public static UserGateway getInstance(String baseUrl) {
        if (instance == null) {
            instance = new Retrofit.Builder().baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                    .build()
                    .create(UserGateway.class);
        }

        return instance;
    }
}
