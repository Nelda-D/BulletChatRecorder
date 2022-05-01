package Bilibili.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object BilibiliServiceCreate {
    val retrofit = Retrofit.Builder()
        .baseUrl(BilibiliApi.BASEURL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun <T> create(serverClass: Class<T>): T = retrofit.create(serverClass)
}