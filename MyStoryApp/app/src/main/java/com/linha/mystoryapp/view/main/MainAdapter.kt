package com.linha.mystoryapp.view.main

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.linha.mystoryapp.data.api.response.ListStoryItem
import com.linha.mystoryapp.databinding.ItemRowStoryBinding
import com.linha.mystoryapp.view.detailStory.DetailStoryActivity

class MainAdapter :
    PagingDataAdapter<ListStoryItem, MainAdapter.ListViewHolder>(DIFF_CALLBACK) {

    class ListViewHolder(private val binding : ItemRowStoryBinding) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind (name: ListStoryItem) {
            binding.tvItemName.text = "${name.name}\n"
            binding.tvItemDesc.text = "${name.description}\n"
            Glide.with(binding.root.context)
                .load(name.photoUrl)
                .into(binding.imgItemPhoto)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemRowStoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val name = getItem(position)
        if (name != null) {
            holder.bind(name)
        }
        holder.itemView.setOnClickListener {
            val intentDetail = Intent(holder.itemView.context, DetailStoryActivity::class.java)
            intentDetail.putExtra(DetailStoryActivity.ID, name?.id )

            holder.itemView.context.startActivity(intentDetail)  }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ListStoryItem>() {
            override fun areItemsTheSame(oldItem: ListStoryItem, newItem: ListStoryItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: ListStoryItem,
                newItem: ListStoryItem
            ): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
}