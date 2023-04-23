package com.example.movieappmad23

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.movieappmad23.repositories.MovieRepository
import com.example.movieappmad23.viewmodels.MoviesViewModel

class MovieViewModelFactory (private val repository: MovieRepository): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(MoviesViewModel::class.java)){
            return MoviesViewModel(repository) as T

        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }


}