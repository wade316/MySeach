package com.example.mysearch.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mysearch.API.Document
import com.example.mysearch.MainActivity.Util.getDateTimeFormat
import com.example.mysearch.databinding.ItemBinding

class HomeListAdapter :
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
        fun onClick(item:Document)
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
        val item = getItem(position)
        holder.itemView.setOnClickListener {
            //클릭했을때 아이콘이 있는지 없는지 체크하고 아이콘을 상태에 맞춰 띄운다
            holder.favoriteIcon.isVisible = !item.favorite //홀더에 아이콘이 visivle인지 확인하고 클릭시 boolean반대값을 적용
            item.favorite = !item.favorite //클릭시 boolean 값변경
            itemClick?.onClick(item)  //
        }
        holder.bind(item)  //RecyclerView에 리스트 띄우기
        //datetime Formet 바꾸기
        holder.time.text = getDateTimeFormat(
            item.datetime,
            "yyyy-MM-dd'T'HH:mm:ss.SSS+09:00",
            "yyyy-MM-dd HH:mm:ss")
    }


    //
    inner class ItemViewHolder(private val binding: ItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val imageview = binding.ivImage
        val favoriteIcon = binding.ivFavorite
        val title = binding.tvTitle
        val time = binding.tvTime
        fun bind(document: Document) {
//this는 Activity나 Fragment에서 사용하는데 Adapter에서는 이미지를 담는 변수만들어서 변수를 사용하는게 좋음 context는 무거워서 권장하지 않음
            Glide.with(imageview)
                .load(document.thumbnail_url)
                .into(imageview)
            title.text = document.display_sitename
            time.text = document.datetime
            //클릭했을때 아이콘이 없으면 생성
            favoriteIcon.isVisible = document.favorite //visivle상태 확인하고 꺼져있으면 false,켜져있으면 true
        }
    }

}