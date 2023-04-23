package com.example.movieappmad23.data

import androidx.room.*
import com.example.movieappmad23.models.Movie
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {
    // CRUD
    @Insert
    fun add(movie: Movie)

    @Update
    fun update(movie: Movie)

    @Delete
    fun delete(movie: Movie)

    @Query("SELECT * from movie")
    fun readAll(): Flow<List<Movie>>

    @Query("SELECT * from movie WHERE isFavorite = 1")
    fun readAllChecked(): Flow<List<Movie>>

    @Query("SELECT * from movie WHERE id=:movieId")
    fun getMovieById(movieId: Int): Movie

    @Query("DELETE from movie")
    fun deleteAll()
}