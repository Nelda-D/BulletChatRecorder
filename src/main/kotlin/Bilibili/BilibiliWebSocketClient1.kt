package Bilibili

import Bilibili.data.AuthenticateBody
import org.java_websocket.handshake.ServerHandshake
import java.lang.Exception
import java.net.URI

class BilibiliWebSocketClient1(
    val realRoomId: Long, val token: String, val url: String
) :
    CommonWebSocketClient(URI(url)) {
    override fun onOpen(handshakedata: ServerHandshake?) {
        super.onOpen(handshakedata)
        send(AuthenticateBody(realRoomId, token).toPackageHexString())
    }

    override fun onMessage(message: String?) {
        super.onMessage(message)
    }

    override fun onClose(code: Int, reason: String?, remote: Boolean) {
        super.onClose(code, reason, remote)
    }

    override fun onError(ex: Exception?) {
        super.onError(ex)
    }
}