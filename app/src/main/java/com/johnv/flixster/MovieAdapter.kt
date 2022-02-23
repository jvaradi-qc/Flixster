package com.johnv.flixster


import android.content.Context
import android.content.res.Configuration
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import java.security.AccessController.getContext

private const val TAG = "MovieAdapter"
var orientation = 0
class MovieAdapter(private val context: Context, private val movies: List<Movie>) :
    RecyclerView.Adapter<MovieAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        Log.i(TAG,"onCreateViewHolder")

        val view = LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false)
        orientation = view.getContext().getResources().getConfiguration().orientation
        Log.i("MovieAdapter", "view orientation: $orientation")
        return ViewHolder(view)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.i(TAG, "onBindViewHolder position $position")
        val movie = movies[position]
        holder.bind(movie)
    }

    override fun getItemCount() = movies.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        private val ivPoster = itemView.findViewById<ImageView>(R.id.ivPoster)
        //private val ivBackdrop = itemView.findViewById<ImageView>(R.id.ivBackdropimage)
        private val tvTitle = itemView.findViewById<TextView>(R.id.tvTitle)
        private val tvOverview = itemView.findViewById<TextView>(R.id.tvOverview)


        fun bind(movie: Movie) {
            //Log.i(TAG, "ivBackdrop $ivBackdrop")
            Log.i(TAG, "ivPoster $ivPoster")
            tvTitle.text = movie.title
            tvOverview.text = movie.overview
            val orientPortFlag = Configuration.ORIENTATION_PORTRAIT
            Log.i(TAG, "orientPortFlag $orientPortFlag")

            val orientLandFlag = Configuration.ORIENTATION_LANDSCAPE
            Log.i(TAG, "orientLandscapeFlag $orientLandFlag")
            //val orient = ivPoster.resources.configuration.orientation
            //Log.i(TAG, "orientVariable: $orient")
            if(orientation == Configuration.ORIENTATION_PORTRAIT){
                Glide.with(context).load(movie.posterImageUrl).into(ivPoster)
            }
            else if (orientation == Configuration.ORIENTATION_LANDSCAPE){
                Glide.with(context).load(movie.backdropImageUrl).into(ivPoster)
            }
        }
    }

}
