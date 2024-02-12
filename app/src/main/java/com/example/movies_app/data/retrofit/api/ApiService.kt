package com.example.movies_app.data.retrofit.api

import com.example.movies_app.models.Film
import com.example.movies_app.models.FilmDescriptionModel
import com.example.movies_app.models.MoviesModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface ApiService {
    @GET("api/v2.2/films/top?type=TOP_100_POPULAR_FILMS")
    suspend fun getPopularMovie(@Header("X-API-KEY") apiKey: String): Response<MoviesModel>

    @GET("/api/v2.2/films/{filmId}")
    suspend fun getFilmDescription(
        @Path("filmId") filmId: Int,
        @Header("X-API-KEY") apiKey: String
    ): Response<FilmDescriptionModel>

}