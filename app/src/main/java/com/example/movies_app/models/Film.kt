package com.example.movies_app.models

import java.io.Serializable

data class Film(
    val countries: List<Country>,
    val filmId: Int,
    val genres: List<Genre>,
    val nameRu: String,
    val posterUrl: String,
    val posterUrlPreview: String,
    val rating: String,
    val year: String
) : Serializable