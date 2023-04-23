package com.example.movieappmad23.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieappmad23.common.Validator
import com.example.movieappmad23.models.Movie
import com.example.movieappmad23.models.getMovies
import com.example.movieappmad23.repositories.MovieRepository
import com.example.movieappmad23.screens.AddMovieUIEvent
import com.example.movieappmad23.screens.AddMovieUiState
import com.example.movieappmad23.screens.hasError
import com.example.movieappmad23.screens.toMovie
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

// inherit from ViewModel class
class MoviesViewModel(private val repository: MovieRepository): ViewModel() {
    private val _movieListState = MutableStateFlow(listOf<Movie>())
    val movieListState: StateFlow<List<Movie>> = _movieListState.asStateFlow()

    init {
        viewModelScope.launch {
            repository.getAllMovies().collect{ movieList ->
                if (!movieList.isNullOrEmpty()){
                    _movieListState.value= movieList

                }
            }
        }
    }

    var movieUiState by mutableStateOf(AddMovieUiState())
        private set

    val favoriteMovies: List<Movie>
        get() = _movieListState.value.filter { it.isFavorite }

    init {
        _movieListState.value = getMovies()

    }

    fun updateUIState(newMovieUiState: AddMovieUiState, event: AddMovieUIEvent){
        var state = AddMovieUiState()   // this is needed because copy always creates a new instance

        when (event) {
            is AddMovieUIEvent.TitleChanged -> {
                val titleResult = Validator.validateMovieTitle(newMovieUiState.title)
                state = if(!titleResult.successful) newMovieUiState.copy(titleErr = true) else newMovieUiState.copy(titleErr = false)
            }
            is AddMovieUIEvent.YearChanged -> {
                val yearResult = Validator.validateMovieYear(newMovieUiState.year)
                state = if(!yearResult.successful) newMovieUiState.copy(yearErr = true) else newMovieUiState.copy(yearErr = false)
            }

            is AddMovieUIEvent.DirectorChanged -> {
                val directorResult = Validator.validateMovieDirector(newMovieUiState.director)
                state = if(!directorResult.successful) newMovieUiState.copy(directorErr = true) else newMovieUiState.copy(directorErr = false)
            }

            is AddMovieUIEvent.ActorsChanged -> {
                val actorsResult = Validator.validateMovieActors(newMovieUiState.actors)
                state = if(!actorsResult.successful) newMovieUiState.copy(actorsErr = true) else newMovieUiState.copy(actorsErr = false)
            }

            is AddMovieUIEvent.RatingChanged -> {
                val ratingResult = Validator.validateMovieRating(newMovieUiState.rating)
                state = if(!ratingResult.successful) newMovieUiState.copy(ratingErr = true) else newMovieUiState.copy(ratingErr = false)
            }

            is AddMovieUIEvent.GenresChanged -> {
                val genreResult = Validator.validateMovieGenres(newMovieUiState.genre)
                state = if(!genreResult.successful) newMovieUiState.copy(genreErr = true) else newMovieUiState.copy(genreErr = false)
            }
            else -> {}
        }

        movieUiState = state.copy(actionEnabled = !newMovieUiState.hasError())
    }

    fun updateFavoriteMovies(movie: Movie) {
        movie.isFavorite = !movie.isFavorite
        repository.update(movie)
    }


    fun saveMovie(movie: Movie) {
        repository.add(movie)
        /*val movie = movieUiState.toMovie()

        _movieListState.update {
            val list: MutableList<Movie> = _movieListState.value.toMutableList()
            list.add(movie)
            list*/
        }

    fun deleteMovie(movie: Movie){
        repository.delete(movie)
    }
    }

