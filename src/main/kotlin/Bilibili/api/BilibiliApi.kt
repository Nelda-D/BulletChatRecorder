package Bilibili.api

class BilibiliApi {
    companion object {
        const val BASEURL = "https://api.live.bilibili.com/xlive/web-room/v1/index/"

        const val WEBSOCKET_URL = "wss://broadcastlv.chat.bilibili.com:443/sub"

        const val HEADERLENGTH = 16

        const val HEARTBEAT_TIMER: Long = 25000

        /****************** AuthenticateBody ******************/
        const val CLIENTVER_DEFAULT = "1.6.3"
        const val PLATFORM_DEFAULT = "web"

        // protover 为 1 时不会使用zlib压缩，为 2 时会发送带有zlib压缩的包，也就是数据包协议为 2 。
        const val PROTOVER_DEFAULT = 2

        // can be obtained from getUserInfo api
        const val UID_DEFAULT = 0

        // Unknown, non-required
        const val TYPE_DEFAULT = 2
    }
}