package com.example.mysearch

import com.example.mysearch.API.KakaoModel
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

private val REST_API_KEY = "1110dcb7cd03156bef73c1b2cbd9a3b1"
private val authorization = "KakaoAK $REST_API_KEY"

interface KakaoAPI {
    @GET("v2/search/image")
    suspend fun getSearchImage(
        @Header("Authorization") apiKey: String = authorization,
        @Query("query") query: String = "" ): KakaoModel
}
