package Bilibili.data

import Bilibili.api.CmdType
import com.google.gson.Gson

abstract class BilibiliMessage(val cmdType: String, val time: Long, val uname: String, val uid: Long) {

//    abstract fun create(bodyString: String): BilibiliMessage

    fun toJsonString(): String {
        return Gson().toJson(this).toString()
    }
}
