package com.said.inscripciones.api;

import com.google.gson.reflect.TypeToken;
import com.said.inscripciones.models.Estudiante;

import java.util.List;

import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class EstudianteApiClient {
    private static final String ENDPOINT = ApiConfig.BASE_URL + "estudiantes";
    public static List<Estudiante> getEstudiantes() throws Exception {
        final List<Estudiante>[] result = new List[]{null};

        Thread t = new Thread(() -> {
            try {
                Request request = new Request.Builder()
                        .url(ENDPOINT)
                        .build();
                Response response = ApiConfig.getClient().newCall(request).execute();
                String json = response.body() != null ? response.body().string() : "[]";
                result[0] = ApiConfig.getGson().fromJson(json, new TypeToken<List<Estudiante>>(){}.getType());
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

    public static Estudiante getEstudiante(String cedula) throws Exception {
        final Estudiante[] result = new Estudiante[]{null};

        Thread t = new Thread(() -> {
            try {
                Request request = new Request.Builder()
                        .url(ENDPOINT + "/" + cedula)
                        .build();
                Response response = ApiConfig.getClient().newCall(request).execute();
                String json = response.body() != null ? response.body().string() : "{}";
                result[0] = ApiConfig.getGson().fromJson(json, Estudiante.class);
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

    public static Estudiante createEstudiante(Estudiante estudiante) throws Exception{
        final Estudiante[] result = new Estudiante[]{null};

        Thread t = new Thread(() -> {
            try {
                Request request = new Request.Builder()
                        .url(ENDPOINT)
                        .post(RequestBody.create(ApiConfig.getGson().toJson(estudiante), ApiConfig.JSON))
                        .build();
                Response response = ApiConfig.getClient().newCall(request).execute();
                String json = response.body() != null ? response.body().string() : "{}";
                result[0] = ApiConfig.getGson().fromJson(json, Estudiante.class);
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

    public static Estudiante updateEstudiante(Estudiante estudiante) throws Exception{
        final Estudiante[] result = new Estudiante[]{null};

        Thread t = new Thread(() -> {
            try {
                Request request = new Request.Builder()
                        .url(ENDPOINT + "/" + estudiante.cedula)
                        .put(RequestBody.create(ApiConfig.getGson().toJson(estudiante), ApiConfig.JSON))
                        .build();
                Response response = ApiConfig.getClient().newCall(request).execute();
                String json = response.body() != null ? response.body().string() : "{}";
                result[0] = ApiConfig.getGson().fromJson(json, Estudiante.class);
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

    public static boolean deleteEstudiante(String cedula) throws Exception {
        final boolean[] success = {false};

        Thread t = new Thread(() -> {
            try {
                Request request = new Request.Builder()
                        .url(ENDPOINT + "/" + cedula)
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
