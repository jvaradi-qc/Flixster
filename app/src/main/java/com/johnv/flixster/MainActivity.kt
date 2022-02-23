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
private const val TAG = "MainActivity"


class MainActivity : AppCompatActivity() {

    //private lateinit var scrollListener: EndlessRecyclerViewScrollListener
    //public var orient = getResources().getConfiguration().orientation

    private val movies = mutableListOf<Movie>()
    private lateinit var rvMovies: RecyclerView

    //override fun onConfigurationChanged(newConfig: Configuration) {
     //   super.onConfigurationChanged(newConfig)
    //    orient = getResources().getConfiguration().orientation
    //    Log.i("MainActivity", "orientVariableinOnConfigChanged: $orient")
    //}

    override fun onCreate(savedInstanceState: Bundle?) {
                super.onCreate(savedInstanceState)
                setContentView(R.layout.activity_main)
                rvMovies = findViewById(R.id.rvMovies)

                val movieAdapter = MovieAdapter(this, movies)
                rvMovies.adapter = movieAdapter
                rvMovies.layoutManager = LinearLayoutManager(this)
                //scrollListener = EndlessRecyclerViewScrollListener()
                val client = AsyncHttpClient()
                client.get(NOW_PLAYING_URL + API_KEY, object: JsonHttpResponseHandler() {
                    override fun onFailure(statusCode: Int, headers: Headers?, response: String?, throwable: Throwable?
                    ) {
                        Log.e(TAG, "onFailure: $statusCode")
                    }

                    override fun onSuccess(statusCode: Int, headers: Headers?, json: JSON) {
                        Log.i(TAG, "onSuccess: JSON data $json")
                        try {
                            val movieJsonArray = json.jsonObject.getJSONArray("results")
                            movies.addAll(Movie.fromJsonArray(movieJsonArray))
                            movieAdapter.notifyDataSetChanged()
                            Log.i(TAG, "Movie List $movies")
                        } catch (e: JSONException){
                            Log.e(TAG, "Encountered exception $e")
                        }
                    }

                })
    }
}