package com.example.mysearch.recyclerview


data class FavoriteItemList(
val image: String,
val title: String,
val time: String,
val favorites: Boolean = false
)
