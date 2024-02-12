package com.example.movies_app.data.retrofit

import com.example.movies_app.data.retrofit.api.RetrofitInstance
import com.example.movies_app.models.Film
import com.example.movies_app.models.FilmDescriptionModel
import com.example.movies_app.models.MoviesModel
import retrofit2.Response

class RetrofitRepository(private val apiKey: String) {
    private val apiService = RetrofitInstance.api

    suspend fun getMovies(): Response<MoviesModel> {
        return apiService.getPopularMovie(apiKey)
    }

    suspend fun getFilmDescription(filmId: Int): Response<FilmDescriptionModel> {
        return apiService.getFilmDescription(filmId, apiKey)
    }
}