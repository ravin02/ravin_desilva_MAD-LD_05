package com.example.movieappmad23.utils

import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.example.movieappmad23.models.Genre

class CustomConverters {

        @TypeConverter
        fun fromGenreList(genreList: List<Genre>) = genreList.joinToString(separator = "#")


        @TypeConverter
        fun toGenreList(genreString: String) = genreString.split("#").map { it.trim() }

    @TypeConverter
    fun fromString(string: String?): List<String>? {
        return string?.split(",")
    }

    @TypeConverter
    fun toString(list: List<String>?): String? {
        return list?.joinToString(",")
    }


}