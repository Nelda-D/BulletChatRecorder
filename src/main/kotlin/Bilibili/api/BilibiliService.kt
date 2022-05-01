package Bilibili.api

import Bilibili.data.DanMuInfoBean
import Bilibili.data.RealRoomInfoBean
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface BilibiliService {
//    val BASEURL = "https://api.live.bilibili.com/xlive/web-room/v1/index/"

    // https://api.live.bilibili.com/xlive/web-room/v1/index/getInfoByRoom?room_id=732
    @GET("getInfoByRoom")
    fun getRealRoomId(@Query("room_id") roomId: Long): Call<RealRoomInfoBean>

    // getDanmuInfo?id=6154037&type=0
    @GET("getDanmuInfo")
    fun getToken(@Query("id") roomId: Long, @Query("type") type: Long = 0): Call<DanMuInfoBean>


}