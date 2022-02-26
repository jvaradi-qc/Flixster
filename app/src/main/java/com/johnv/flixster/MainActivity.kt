package com.johnv.flixster

import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import okhttp3.Headers
import org.json.JSONException

//import com.johnv.flixster.EndlessRecyclerViewScrollListener

private const val NOW_PLAYING_URL = "https://api.themoviedb.org/3/movie/now_playing?api_key="
private const val API_KEY = "50e2e2a36e44b96f775a10d2aedb6063"
private const val PAGE = "&page="
private const val TAG = "MainActivity"


class MainActivity : AppCompatActivity() {

    //private lateinit var scrollListener: EndlessRecyclerViewScrollListener
    //public var orient = getResources().getConfiguration().orientation

    private val movies = mutableListOf<Movie>()
    private lateinit var rvMovies: RecyclerView
    private var scrollListener: EndlessRecyclerViewScrollListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
                super.onCreate(savedInstanceState)
                setContentView(R.layout.activity_main)
                rvMovies = findViewById(R.id.rvMovies)

                val movieAdapter = MovieAdapter(this, movies)
                rvMovies.adapter = movieAdapter
                rvMovies.layoutManager = LinearLayoutManager(this)
                scrollListener = object : EndlessRecyclerViewScrollListener(rvMovies.layoutManager as LinearLayoutManager) {
                    override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to the bottom of the list
                    loadNextDataFromApi(page)
            }

                    private fun loadNextDataFromApi(page: Int) {

                        val client = AsyncHttpClient()
                        client.get(NOW_PLAYING_URL + API_KEY + PAGE + page, object: JsonHttpResponseHandler() {
                            override fun onFailure(statusCode: Int, headers: Headers?, response: String?, throwable: Throwable?
                            ) {
                                Log.e(TAG, "onFailure: $statusCode")
                            }

                            override fun onSuccess(statusCode: Int, headers: Headers?, json: JSON) {
                                //Log.i(TAG, "onSuccess: JSON data $json")
                                //new code for endless scrolling
                                //movies.clear() ???
                                try {
                                    val movieJsonArray = json.jsonObject.getJSONArray("results")
                                    movies.addAll(Movie.fromJsonArray(movieJsonArray))
                                    movieAdapter.notifyDataSetChanged()
                                    //Log.i(TAG, "Movie List $movies")
                                } catch (e: JSONException){
                                    Log.e(TAG, "Encountered exception $e")
                                }
                            }

                        })
                    }
        }

                // Adds the scroll listener to RecyclerView
                rvMovies.addOnScrollListener(scrollListener as EndlessRecyclerViewScrollListener)


                val client = AsyncHttpClient()
                client.get(NOW_PLAYING_URL + API_KEY, object: JsonHttpResponseHandler() {
                    override fun onFailure(statusCode: Int, headers: Headers?, response: String?, throwable: Throwable?
                    ) {
                        Log.e(TAG, "onFailure: $statusCode")
                    }

                    override fun onSuccess(statusCode: Int, headers: Headers?, json: JSON) {
                        //Log.i(TAG, "onSuccess: JSON data $json")
                        //new code for endless scrolling
                        //movies.clear() ???
                        try {
                            val movieJsonArray = json.jsonObject.getJSONArray("results")
                            movies.addAll(Movie.fromJsonArray(movieJsonArray))
                            movieAdapter.notifyDataSetChanged()
                            //Log.i(TAG, "Movie List $movies")
                        } catch (e: JSONException){
                            Log.e(TAG, "Encountered exception $e")
                        }
                    }

                })

    }
}