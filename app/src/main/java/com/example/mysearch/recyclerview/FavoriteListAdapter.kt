package com.example.mysearch.recyclerview

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mysearch.API.Document
import com.example.mysearch.MainActivity
import com.example.mysearch.databinding.ItemBinding

class FavoriteListAdapter : ListAdapter<Document, FavoriteListAdapter.ItemViewHolder>(object :
    DiffUtil.ItemCallback<Document>() {
    override fun areItemsTheSame(oldItem: Document, newItem: Document): Boolean {
        // 비디오 id가 같은지 확인
        return (oldItem.thumbnail_url == newItem.thumbnail_url) && (oldItem.datetime == newItem.datetime)
    }

    override fun areContentsTheSame(oldItem: Document, newItem: Document): Boolean {
        // 모든 필드가 같은지 확인 (data class의 equals 사용)
        return oldItem == newItem
    }
}) {
    private val list: MainActivity by lazy {
        MainActivity()
    }

    interface ItemClick {
        fun onClick(item:Document)
    }

    var itemClick: ItemClick? = null



    inner class ItemViewHolder(private val binding: ItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        val image = binding.ivImage
        val time = binding.tvTime
        val title = binding.tvTitle
        fun bind(favorit: Document) {
            time.text = favorit.datetime
            title.text = favorit.display_sitename
            Glide.with(image)
                .load(favorit.thumbnail_url)
                .into(image)
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FavoriteListAdapter.ItemViewHolder {
        val binding = ItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoriteListAdapter.ItemViewHolder, position: Int) {
        holder.bind(getItem(position))
        val item = getItem(position)
        holder.itemView.setOnClickListener {
            itemClick?.onClick(item)  //
            Log.d("favoritadapter", "bindviewholder item =  $item")
        }
        holder.time.text = MainActivity.Util.getDateTimeFormat(
            item.datetime,
            "yyyy-MM-dd'T'HH:mm:ss.SSS+09:00",
            "yyyy-MM-dd HH:mm:ss"
        )
    }
}