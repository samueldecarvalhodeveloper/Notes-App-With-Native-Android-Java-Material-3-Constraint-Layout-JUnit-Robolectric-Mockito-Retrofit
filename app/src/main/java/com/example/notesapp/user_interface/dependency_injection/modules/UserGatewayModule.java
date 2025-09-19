package com.example.notesapp.user_interface.dependency_injection.modules;

import static com.example.notesapp.constants.data.DomainAgnosticConstants.SERVICE_URL;

import com.example.notesapp.data.remote_data_source.gateways.UserGateway;
import com.example.notesapp.data.remote_data_source.services.UserService;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class UserGatewayModule {
    @Provides
    public UserGateway provideUserGateway() {
        return UserService.getInstance(SERVICE_URL);
    }
}
