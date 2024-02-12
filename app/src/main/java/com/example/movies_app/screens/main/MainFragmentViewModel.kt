package com.example.movies_app.screens.main

import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movies_app.MAIN
import com.example.movies_app.data.retrofit.RetrofitRepository
import com.example.movies_app.models.MoviesModel
import kotlinx.coroutines.launch
import retrofit2.Response

class MainFragmentViewModel: ViewModel() {
    private val repository = RetrofitRepository("e30ffed0-76ab-4dd6-b41f-4c9da2b2735b")
    val myMovies: MutableLiveData<Response<MoviesModel>> = MutableLiveData()

    fun getMovies(){
        viewModelScope.launch {
                myMovies.value = repository.getMovies()
            }
        }
}