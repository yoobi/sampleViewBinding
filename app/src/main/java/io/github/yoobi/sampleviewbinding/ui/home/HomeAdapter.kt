package io.github.yoobi.sampleviewbinding.ui.home

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import io.github.yoobi.sampleviewbinding.R
import io.github.yoobi.sampleviewbinding.data.ResultCall
import io.github.yoobi.sampleviewbinding.databinding.MovieItemBinding

class HomeAdapter(private val listener: MovieOnClickListener)
    : ListAdapter<ResultCall, HomeAdapter.PostViewHolder>(PostDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        return PostViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        getItem(position).apply {
            holder.bind(this)
            holder.itemView.setOnClickListener { listener.onClick(show.id) }
        }
    }

    class PostViewHolder(private val binding: MovieItemBinding): RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(resultCall: ResultCall) {
            binding.itemMovieDescription.text = "${resultCall.show.title} - ${resultCall.show.status}"
            Glide.with(binding.itemMoviePoster)
                .setDefaultRequestOptions(RequestOptions().error(R.drawable.place_holder))
                .load(resultCall.show.image?.medium)
                .into(binding.itemMoviePoster)
        }

        companion object {
            fun from(parent: ViewGroup): PostViewHolder {
                val view = MovieItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return PostViewHolder(view)
            }
        }
    }

    companion object PostDiffCallback: DiffUtil.ItemCallback<ResultCall>() {
        override fun areItemsTheSame(oldItem: ResultCall, newItem: ResultCall): Boolean {
            return oldItem.show.id == newItem.show.id
        }

        override fun areContentsTheSame(oldItem: ResultCall, newItem: ResultCall): Boolean {
            return oldItem == newItem
        }
    }

    class MovieOnClickListener(val listener: (imdbId: String) -> Unit) {
        fun onClick(imdbId: String) = listener(imdbId)
    }
}