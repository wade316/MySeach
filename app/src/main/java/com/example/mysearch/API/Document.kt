package com.example.mysearch.API

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.mysearch.MainActivity
import com.example.mysearch.recyclerview.HomeListAdapter

//검색 결과 리스트
data class Document(
    val collection: String,
    val datetime: String,
    val display_sitename: String,
    val doc_url: String,
    val height: Int,
    val image_url: String,
    val thumbnail_url: String,
    val width: Int,
    val bookmark: Boolean = false
)


