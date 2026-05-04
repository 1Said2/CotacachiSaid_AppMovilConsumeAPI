package com.said.inscripciones.api;

import com.google.gson.reflect.TypeToken;
import com.said.inscripciones.models.InscripcionRequest;
import com.said.inscripciones.models.InscripcionResponse;

import java.util.List;

import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class InscripcionApiClient {
    private static final String ENDPOINT = ApiConfig.BASE_URL + "inscripciones";

    public static List<InscripcionResponse> getInscripciones() throws Exception {
        final List<InscripcionResponse>[] result = new List[]{null};

        Thread t = new Thread(() -> {
            try {
                Request request = new Request.Builder()
                        .url(ENDPOINT)
                        .build();
                Response response = ApiConfig.getClient().newCall(request).execute();
                String json = response.body() != null ? response.body().string() : "[]";
                result[0] = ApiConfig.getGson().fromJson(json, new TypeToken<List<InscripcionResponse>>(){}.getType());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        t.start();
        try {
            t.join();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result[0];
    }

    public static InscripcionResponse getInscripcion(int id) throws Exception {
        final InscripcionResponse[] result = new InscripcionResponse[]{null};

        Thread t = new Thread(() -> {
            try {
                Request request = new Request.Builder()
                        .url(ENDPOINT + "/" + id)
                        .build();
                Response response = ApiConfig.getClient().newCall(request).execute();
                String json = response.body() != null ? response.body().string() : "{}";
                result[0] = ApiConfig.getGson().fromJson(json, InscripcionResponse.class);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        t.start();
        try {
            t.join();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result[0];
    }

    public static boolean createInscripcion(InscripcionRequest inscripcionRequest) throws Exception {
        final boolean[] success = {false};

        Thread t = new Thread(() -> {
            try {
                Request request = new Request.Builder()
                        .url(ENDPOINT)
                        .post(RequestBody.create(ApiConfig.getGson().toJson(inscripcionRequest), ApiConfig.JSON))
                        .build();
                Response response = ApiConfig.getClient().newCall(request).execute();
                success[0] = response.isSuccessful();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        t.start();
        try {
            t.join();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return success[0];
    }

    public static boolean updateInscripcion(int id, InscripcionRequest inscripcionRequest) throws Exception {
        final boolean[] success = {false};

        Thread t = new Thread(() -> {
            try {
                Request request = new Request.Builder()
                        .url(ENDPOINT + "/" + id)
                        .put(RequestBody.create(ApiConfig.getGson().toJson(inscripcionRequest), ApiConfig.JSON))
                        .build();
                Response response = ApiConfig.getClient().newCall(request).execute();
                success[0] = response.isSuccessful();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        t.start();

        try {
            t.join();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return success[0];
    }

    public static boolean deleteInscripcion(int id) throws Exception {
        final boolean[] success = {false};

        Thread t = new Thread(() -> {
            try {
                Request request = new Request.Builder()
                        .url(ENDPOINT + "/" + id)
                        .delete()
                        .build();
                Response response = ApiConfig.getClient().newCall(request).execute();
                success[0] = response.isSuccessful();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        t.start();
        try {
            t.join();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return success[0];
    }
}
