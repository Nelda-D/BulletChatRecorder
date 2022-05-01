package Bilibili.data

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

class DanMuInfoBean() {
    val code: Long = -1
    val data: Data? = null

    @Serializable
    class Data() {
        val token: String? = null

        @SerializedName("host_list")
        val hostList: List<Host> = emptyList()

        @Serializable
        data class Host(
            @SerialName("host") val host: String,
            val port: Int, val wss_port: Int, val ws_port: Int
        ) {
            fun toWssUrl(): String {
                return "wss://$host/sub"
            }
        }
    }

    data class DanMuInfoResult(val token: String, val wssUrl: String)

}