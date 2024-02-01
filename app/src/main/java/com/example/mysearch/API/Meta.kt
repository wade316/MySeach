package com.example.mysearch.API
//검색 메타 데이터
data class Meta(
    val is_end: Boolean,
    val pageable_count: Int,
    val total_count: Int
)