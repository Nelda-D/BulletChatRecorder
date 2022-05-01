package Bilibili.data

import JsonFarmatter
import kotlinx.serialization.Serializable
import Bilibili.api.BilibiliApi
import Bilibili.api.Operation
import Bilibili.api.ProtocolVersion
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.SerialName
import kotlinx.serialization.encodeToString

@Serializable
data class AuthenticateBody(
    @SerialName("roomid")
    val roomId: Long,
    @SerialName("key")
    val token: String,
    val uid: Int = BilibiliApi.UID_DEFAULT,
    val protover: Int = /*BilibiliApi.PROTOVER_DEFAULT*/ 3,
    val platform: String = BilibiliApi.PLATFORM_DEFAULT,
//    val clientver: String = BilibiliApi.CLIENTVER_DEFAULT,
    val type: Int = 2
) {
    fun toPackageHexString(): ByteArray {
        val bodyString = JsonFarmatter.farmatter.encodeToString(this)
        val chatPackage = BilibiliChatPackage(bodyString.toByteArray(), ProtocolVersion.INT, Operation.HELLO)
        return chatPackage.toPackageHexString()

    }
}