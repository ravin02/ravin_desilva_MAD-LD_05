package com.example.movieappmad23.repositories

import com.example.movieappmad23.data.MovieDao
import com.example.movieappmad23.models.Movie
import kotlinx.coroutines.flow.Flow

class MovieRepository(private val movieDao: MovieDao) {
    fun add(movie: Movie) = movieDao.add(movie)
    fun delete(movie: Movie) = movieDao.delete(movie)
    fun update(movie: Movie) = movieDao.update(movie)
    fun getAllMovies() = movieDao.readAll()



}