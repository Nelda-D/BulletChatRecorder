package Bilibili

import org.java_websocket.client.WebSocketClient
import org.java_websocket.drafts.Draft
import org.java_websocket.drafts.Draft_6455
import org.java_websocket.handshake.ServerHandshake
import java.lang.Exception
import java.net.URI

open class CommonWebSocketClient(
    val uri: URI,
    val protocolDraft: Draft = Draft_6455(),
    val httpHeaders: Map<String, String>? = null,
    val connectTimeOut: Int = 0
) : WebSocketClient(uri, protocolDraft, httpHeaders, connectTimeOut) {
    override fun onOpen(handshakedata: ServerHandshake?) {
        println("Bilibili.CommonWebSocketClient onOpen ${handshakedata?.httpStatusMessage}")
    }

    override fun onMessage(message: String?) {
        println("Bilibili.CommonWebSocketClient onMessage $message")
    }

    override fun onClose(code: Int, reason: String?, remote: Boolean) {
        println("Bilibili.CommonWebSocketClient onClose code=$code, reason=$reason, remote=$remote")
    }

    override fun onError(ex: Exception?) {
        println("Bilibili.CommonWebSocketClient onError ${ex?.message}")
    }
}