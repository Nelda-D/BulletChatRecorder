package Bilibili

import Bilibili.Processer.ActiveUserAnalysis
import Bilibili.api.BilibiliApi
import Bilibili.api.CmdType
import Bilibili.api.Operation
import Bilibili.api.ProtocolVersion
import Bilibili.data.AuthenticateBody
import Bilibili.data.BilibiliChatPackage
import Bilibili.data.BilibiliChatPackage.Companion.toChatPackage
import Bilibili.data.BilibiliChatPackage.Companion.toChatPackageList
import Bilibili.data.DanMuBMessage
import Bilibili.data.HeartbeatBPackage.Companion.HeartBeatBPackageByteString
import Bilibili.util.BilibiliChatDataHelper.brotli
import Bilibili.util.BilibiliChatDataHelper.launchTimer
import Bilibili.util.BilibiliChatDataHelper.zlib
import Bilibili.util.getCmdString
import okhttp3.*
import okio.ByteString
import okio.ByteString.Companion.toByteString
import java.util.*


class BilibiliWebSocketClient(
    val realRoomId: Long, val token: String,
    val url: String
) : WebSocketListener() {
    val okHttpClient: OkHttpClient = OkHttpClient.Builder().build()
    val wssRequest = Request.Builder().get().url(url).build()
    var websocket: WebSocket? = null
    var heartBeatTimer: Timer? = null
    var subPkgBodyString = ""

    val danmuRecorder = DanmuBiliMsgRecorder(realRoomId)
    private val activeUserAnalysis = ActiveUserAnalysis()

    fun startConnect() {
//        registerMsgEventListener(this)
        danmuRecorder.init { registerMsgEventListener(danmuRecorder) }
        activeUserAnalysis.init { registerMsgEventListener(activeUserAnalysis) }
        websocket = okHttpClient.newWebSocket(wssRequest, this)
    }

    fun stopConnect(){
        websocket?.cancel()
        danmuRecorder.close { unregisterMsgEventListener(danmuRecorder) }
        activeUserAnalysis.close { unregisterMsgEventListener(activeUserAnalysis) }
    }

    fun sendMessage(text: String) {
        websocket?.send(text)
        println("BilibiliWebSocketClient sendMessage ${text}")
    }

    private fun sendAuthenticateMessage() {
        val frame = AuthenticateBody(realRoomId, token).toPackageHexString().toByteString()
        println("BilibiliWebSocketClient sendMessage ${frame}")
        websocket?.send(frame)
    }

    private fun startHeartbeat() {
        heartBeatTimer?.cancel()
        heartBeatTimer = launchTimer(BilibiliApi.HEARTBEAT_TIMER, BilibiliApi.HEARTBEAT_TIMER) {
            websocket?.send(HeartBeatBPackageByteString)
        }
    }

    private fun onHelloAck() {
        println("BilibiliWebSocketClient onHelloAck ")
        startHeartbeat()
    }

    private fun onHeartbeatAck() {
        println("BilibiliWebSocketClient onHeartbeatAck ")
        println("最近活跃用户数： ${activeUserAnalysis.getRecentlyActiveUserCount()}")
    }

    fun onNotice(chatPackage: BilibiliChatPackage) {
//        println("BilibiliWebSocketClient onNotice ")
//        println("##################################################################")
        notifyOnNoticeEvent(chatPackage)

        println("# start ${subPkgBodyString.getCmdString()}, protocol=${chatPackage.protocolVersion}, operation=${chatPackage.operation}")
        subPkgBodyString = String(chatPackage.body)
        when (subPkgBodyString.getCmdString()) {
            CmdType.DANMU_MSG.name -> {
                val danmuMessage = DanMuBMessage.create(subPkgBodyString)
//                writeString = danmuMessage.toJsonString()
//                println("# " + writeString)
//                writeMsgInFile(writeString.toByteArray())
                notifyDanmuMsgEvent(danmuMessage)
            }
            CmdType.SUPER_CHAT_MESSAGE.name -> {
//                println("# " + subPkgBodyString)
//                writeMsgInFile(subPkgBodyString.toByteArray())
                notifySuperChatEvent(subPkgBodyString)
            }

        }
//        println("##################################################################")
    }

    private fun onDisconnect() {

//        unregisterMsgEventListener(this)
    }






    override fun onOpen(webSocket: WebSocket, response: Response) {
        super.onOpen(webSocket, response)
        println("BilibiliWebSocketClient onOpen ${response.message}")

        sendAuthenticateMessage()
    }

    override fun onMessage(webSocket: WebSocket, text: String) {
        super.onMessage(webSocket, text)
        println("BilibiliWebSocketClient onMessage ${text}")
    }

    override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
        super.onMessage(webSocket, bytes)
//        println("BilibiliWebSocketClient onMessage")
        val pkg = bytes.toChatPackage()
        val subPkgList = mutableListOf<BilibiliChatPackage>()
        println("== start protocol=${pkg.protocolVersion}, operation=${pkg.operation}, size=${pkg.packetLength}")
        when (pkg.protocolVersion) {
            ProtocolVersion.BROTLI -> {
                val pkgList = pkg.body.brotli().toByteString().toChatPackageList()
                subPkgList.addAll(pkgList)
            }
            ProtocolVersion.ZLIB_INFLATE -> {
                val pkgList = pkg.body.zlib().toByteString().toChatPackageList()
                subPkgList.addAll(pkgList)
            }
            ProtocolVersion.INT, ProtocolVersion.JSON -> {
                subPkgList.add(pkg)
            }
            else -> {
                println("BilibiliWebSocketClient onMessage unknown message, $bytes")
            }
        }
        subPkgList.onEach { pkg ->
            when (pkg.operation) {
                Operation.HELLO_ACK -> onHelloAck()
                Operation.HEARTBEAT_ACK -> onHeartbeatAck()
                Operation.NOTICE -> {
                    onNotice(pkg)
                }
            }
        }
    }

    override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
        super.onClosing(webSocket, code, reason)
        println("BilibiliWebSocketClient onClosing code=${code}, reason=$reason")
    }

    override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
        super.onClosed(webSocket, code, reason)
        println("BilibiliWebSocketClient onClosed code=${code}, reason=$reason")
        onDisconnect()
    }

    override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
        super.onFailure(webSocket, t, response)
        println("BilibiliWebSocketClient onFailure ${t.message}, ${response?.message}")
        t.printStackTrace()
        onDisconnect()
    }

    val eventList = mutableListOf<BiliMsgEventListener>()
    private fun notifyOnNoticeEvent(chatPackage: BilibiliChatPackage) {
        for (listener in eventList) {
            listener.onNoticeEvent(chatPackage)
        }
    }

    private fun notifyDanmuMsgEvent(msg: DanMuBMessage) {
        for (listener in eventList) {
            listener.onDanmuMsgEvent(msg)
        }
    }

    private fun notifySuperChatEvent(msg: String) {
        for (listener in eventList) {
            listener.onSuperChatEvent(msg)
        }
    }

    fun registerMsgEventListener(listener: BiliMsgEventListener) {
        if (!eventList.contains(listener)) {
            eventList.add(listener)
        }
    }

    fun unregisterMsgEventListener(listener: BiliMsgEventListener) {
        if (eventList.contains(listener)) {
            eventList.remove(listener)
        }
    }
}