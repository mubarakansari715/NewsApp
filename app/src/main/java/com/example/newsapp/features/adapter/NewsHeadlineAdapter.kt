package com.example.newsapp.features.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newsapp.R
import com.example.newsapp.databinding.ItemHeadlineBinding
import com.example.newsapp.features.model.Article

class NewsHeadlineAdapter(

) : RecyclerView.Adapter<NewsHeadlineAdapter.NewsHeadlineViewHolder>() {

    private var list: List<Article> = listOf()

    fun setData(list: List<Article>) {
        this.list = list
        notifyDataSetChanged()
    }

    class NewsHeadlineViewHolder(private val binding: ItemHeadlineBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun setData(data: Article) {
            binding.apply {
                tvTitle.text = data.title
                Glide.with(binding.root.context)
                    .load(data.urlToImage)
                    .placeholder(R.drawable.ic_launcher_background)
                    .into(binding.shapeableImageView)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsHeadlineViewHolder {
        return NewsHeadlineViewHolder(
            ItemHeadlineBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: NewsHeadlineViewHolder, position: Int) {
        val data = list[position]
        holder.setData(data)
    }
}