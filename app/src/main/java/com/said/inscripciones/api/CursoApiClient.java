package com.said.inscripciones.api;

import com.google.gson.reflect.TypeToken;
import com.said.inscripciones.models.Curso;

import java.util.List;

import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class CursoApiClient {
    private static final String ENDPOINT = ApiConfig.BASE_URL + "cursos";

    public static List<Curso> getCursos() throws Exception {
        final List<Curso>[] result = new List[]{null};

        Thread t = new Thread(() -> {
            try {
                Request request = new Request.Builder()
                        .url(ENDPOINT)
                        .build();
                Response response = ApiConfig.getClient().newCall(request).execute();
                String json = response.body() != null ? response.body().string() : "[]";
                result[0] = ApiConfig.getGson().fromJson(json, new TypeToken<List<Curso>>(){}.getType());
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

    public static Curso getCurso(int id) throws Exception {
        final Curso[] result = new Curso[]{null};

        Thread t = new Thread(() -> {
            try {
                Request request = new Request.Builder()
                        .url(ENDPOINT + "/" + id)
                        .build();
                Response response = ApiConfig.getClient().newCall(request).execute();
                String responseJson = response.body() != null ? response.body().string() : "{}";
                result[0] = ApiConfig.getGson().fromJson(responseJson, Curso.class);
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

    public static Curso createCurso(Curso curso) throws Exception {
        final Curso[] result = new Curso[]{null};

        Thread t = new Thread(() -> {
            try {
                Request request = new Request.Builder()
                        .url(ENDPOINT)
                        .post(RequestBody.create(ApiConfig.getGson().toJson(curso), ApiConfig.JSON))
                        .build();
                Response response = ApiConfig.getClient().newCall(request).execute();
                String json = response.body() != null ? response.body().string() : "{}";
                result[0] = ApiConfig.getGson().fromJson(json, Curso.class);
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

    public static Curso updateCurso(Curso curso) throws Exception {
        final Curso[] result = new Curso[]{null};

        Thread t = new Thread(() -> {
            try {
                Request request = new Request.Builder()
                        .url(ENDPOINT + "/" + curso.id_curso)
                        .put(RequestBody.create(ApiConfig.getGson().toJson(curso), ApiConfig.JSON))
                        .build();
                Response response = ApiConfig.getClient().newCall(request).execute();
                String json = response.body() != null ? response.body().string() : "{}";
                result[0] = ApiConfig.getGson().fromJson(json, Curso.class);
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

    public static boolean deleteCurso(int id_curso) throws Exception {
        final boolean[] success = {false};

        Thread t = new Thread(() -> {
            try {
                Request request = new Request.Builder()
                        .url(ENDPOINT + "/" + id_curso)
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
