package com.example.projectepeliculesandroid;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface preguntesAPI {


        @GET("/preguntes")
        Call<preguntasResponse> getPreguntes();

        @POST("/respostes")
        Call<preguntasResponse> getWeather();

}
