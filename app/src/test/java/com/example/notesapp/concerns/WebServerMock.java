package com.example.notesapp.concerns;

import static com.example.notesapp.constants.data.DomainAgnosticConstants.JSON_CONTENT_TYPE_HEADER;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;

public class WebServerMock {
    private static final MockWebServer webServerMock = new MockWebServer();

    public static String WEB_SERVER_MOCK_URL;

    private WebServerMock() {
    }

    public static void startServer() {
        try {
            webServerMock.start();

            WEB_SERVER_MOCK_URL = webServerMock.url("").toString();
        } catch (Exception ignored) {
        }
    }

    public static void stopServer() {
        try {
            webServerMock.shutdown();
        } catch (Exception ignored) {
        }
    }

    public static void enqueueResponse(String body, int responseCode) {
        MockResponse response = new MockResponse()
                .addHeader(JSON_CONTENT_TYPE_HEADER)
                .setResponseCode(responseCode)
                .setBody(body);

        webServerMock.enqueue(response);
    }
}
