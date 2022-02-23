package com.johnv.flixster

import android.util.Log
import org.json.JSONArray

private const val TAG = "Movie"

data class Movie(
    val movieId: Int,
    private val posterPath: String,
    private val backdropPath: String,
    val title: String,
    val overview: String,

    ) {
        val posterImageUrl = "https://image.tmdb.org/t/p/w342/$posterPath"
        val backdropImageUrl = "https://image.tmdb.org/t/p/w780/$backdropPath"
        companion object {
            fun fromJsonArray(movieJsonArray: JSONArray): List<Movie> {
                val movies = mutableListOf<Movie>()
                for (i in 0 until movieJsonArray.length()){
                    val movieJson = movieJsonArray.getJSONObject(i)
                    movies.add(
                        Movie(
                            movieJson.getInt("id"),
                            movieJson.getString("poster_path"),
                            movieJson.getString("backdrop_path"),
                            movieJson.getString("title"),
                            movieJson.getString("overview")
                        )
                    )

                    //val movieBackdropURL = movieJson.getString("backdrop_path")
                    //Log.i(TAG, "backdrop path: $movieBackdropURL")
                }
                return movies
            }
        }
}