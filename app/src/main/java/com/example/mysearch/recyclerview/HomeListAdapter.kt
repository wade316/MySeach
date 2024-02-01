package com.example.mysearch.recyclerview

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mysearch.API.Document
import com.example.mysearch.databinding.ItemBinding

class HomeListAdapter() :
//class Adapter(val mitems: List<Document>) : RecyclerView.Adapter<Adapter.ItemViewHolder>() {

    ListAdapter<Document, HomeListAdapter.ItemViewHolder>(object :
        DiffUtil.ItemCallback<Document>() {
        //        추가부분
        override fun areItemsTheSame(oldItem: Document, newItem: Document): Boolean {
            // 비디오 id가 같은지 확인
            return (oldItem.thumbnail_url == newItem.thumbnail_url) && (oldItem.datetime == newItem.datetime)
        }

        override fun areContentsTheSame(oldItem: Document, newItem: Document): Boolean {
            // 모든 필드가 같은지 확인 (data class의 equals 사용)
            return oldItem == newItem
        }
    }) {

    interface ItemClick {
        fun onClick(view: View, position: Int)
    }

    var itemClick: ItemClick? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HomeListAdapter.ItemViewHolder {
        val binding = ItemBinding.inflate(LayoutInflater.from(parent.context), parent, false) //
        return ItemViewHolder(binding) //
    }

    override fun onBindViewHolder(holder: HomeListAdapter.ItemViewHolder, position: Int) {
        holder.itemView.setOnClickListener {  //
            itemClick?.onClick(it, position)  //
        }
        holder.bind(getItem(position))  //RecyclerView에 리스트 띄우기
    }


    //
    inner class ItemViewHolder(private val binding: ItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val imageview = binding.ivImage

        val title = binding.tvTitle
        val time = binding.tvTime
        fun bind(document: Document) {
//this는 Activity나 Fragment에서 사용하는데 Adapter에서는 이미지를 담는 변수만들어서 변수를 사용하는게 좋음 context는 무거워서 권장하지 않음
            Glide.with(imageview)
                .load(document.thumbnail_url)
                .into(imageview)
            title.text = document.display_sitename
            time.text = document.datetime
        }
    }

}