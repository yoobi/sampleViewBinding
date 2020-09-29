package io.github.yoobi.sampleviewbinding.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import io.github.yoobi.sampleviewbinding.databinding.ItemSearchBinding

class SearchAdapter(var listener: SearchOnClickListener)
    : ListAdapter<String, SearchAdapter.SearchViewHolder>(SearchDiffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        return SearchViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        getItem(position).apply {
            holder.bind(this)
            holder.itemView.setOnClickListener { listener.clickListener(this) }
        }
    }

    companion object SearchDiffUtil : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }
    }

    class SearchViewHolder(private val binding: ItemSearchBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(text: String) {
            binding.itemLabel.text = text
        }

        companion object {
            fun from(parent: ViewGroup): SearchViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                return SearchViewHolder(ItemSearchBinding.inflate(inflater, parent, false))
            }
        }
    }

    class SearchOnClickListener(val clickListener: (text: String) -> Unit) {
        fun onClick(text: String) = clickListener(text)
    }
}
