package com.said.inscripciones.api;

import com.google.gson.Gson;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;

public class ApiConfig {
    public static final String BASE_URL = "https://products-api-c6e5debfdrd9dba7.westus3-01.azurewebsites.net/api/";
    public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");
    private static final OkHttpClient client = new OkHttpClient();
    private static final Gson GSON = new Gson();

    public static Gson getGson() {
        return GSON;
    }
    public static OkHttpClient getClient() {
        return client;
    }
}
