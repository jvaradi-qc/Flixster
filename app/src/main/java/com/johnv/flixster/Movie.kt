package com.johnv.flixster

import android.os.Parcelable
import android.util.Log
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize
import org.json.JSONArray

private const val TAG = "Movie"

@Parcelize
data class Movie(
    val movieId: Int,
    val voteAverage: Double,
    private val posterPath: String,
    private val backdropPath: String,
    val title: String,
    val overview: String,

    ) : Parcelable {
        @IgnoredOnParcel
        val posterImageUrl = "https://image.tmdb.org/t/p/w342/$posterPath"
        @IgnoredOnParcel
        val backdropImageUrl = "https://image.tmdb.org/t/p/w780/$backdropPath"
        companion object {
            fun fromJsonArray(movieJsonArray: JSONArray): List<Movie> {
                val movies = mutableListOf<Movie>()
                for (i in 0 until movieJsonArray.length()){
                    val movieJson = movieJsonArray.getJSONObject(i)
                    movies.add(
                        Movie(
                            movieJson.getInt("id"),
                            movieJson.getDouble("vote_average"),
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