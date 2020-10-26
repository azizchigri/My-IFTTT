package com.example.area.API;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIClient {

    private static Retrofit retrofit = null;
    private static String authorization = "";
    private static String server = "";

    public static void setAuthorization(String newAuthorization) {
        authorization = newAuthorization;
    }

    public static void setServer(String newServer) {
        server = newServer;
        createClient();
    }
    
    private static void createClient() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder oktHttpClient = new OkHttpClient.Builder();
        oktHttpClient.addInterceptor(logging);
        oktHttpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Interceptor.Chain chain) throws IOException {

                if (!authorization.equals("")) {
                    Request original = chain.request();
                    Request.Builder requestBuilder = original.newBuilder()
                            .header("Authorization", authorization);
                    Request request = requestBuilder.build();
                    return chain.proceed(request);
                }
                return chain.proceed(chain.request());
            }
        });

        try {
            retrofit = new Retrofit.Builder()
                    .baseUrl(server)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(oktHttpClient.build())
                    .build();
        } catch (Exception e) {
            retrofit = null;
        }
    }

    public static Retrofit getClient() {
        if (retrofit == null) {
            createClient();
        }
        return retrofit;
    }
}