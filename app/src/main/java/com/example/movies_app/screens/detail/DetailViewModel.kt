package com.example.movies_app.screens.detail

import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movies_app.MAIN
import com.example.movies_app.data.retrofit.RetrofitRepository
import com.example.movies_app.models.Film
import com.example.movies_app.models.FilmDescriptionModel
import com.example.movies_app.models.MoviesModel
import kotlinx.coroutines.launch
import retrofit2.Response

class DetailViewModel: ViewModel() {
    private val repository = RetrofitRepository("e30ffed0-76ab-4dd6-b41f-4c9da2b2735b")
    val myDescription: MutableLiveData<Response<FilmDescriptionModel>> = MutableLiveData()
    val myGenres: MutableLiveData<List<String>> = MutableLiveData()
    val myCountries: MutableLiveData<List<String>> = MutableLiveData()
    fun getFilmDescriptionAndGenresAndCountry(filmId: Int) {
        viewModelScope.launch {
            val response = repository.getFilmDescription(filmId)
            if (response.isSuccessful) {
                myDescription.value = response
                myGenres.value = response.body()?.genres?.map { it.genre } ?: emptyList()
                myCountries.value = response.body()?.countries?.map { it.country } ?: emptyList()
            } else {
                // Обработка ошибки запроса
                Toast.makeText(
                    MAIN,
                    "Ошибка загрузки описания",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}