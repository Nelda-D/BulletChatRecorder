package Bilibili

import Bilibili.data.BilibiliChatPackage
import Bilibili.data.DanMuBMessage

interface BiliMsgEventListener {
    fun init(register: () -> Unit)
    fun close(unregister: () -> Unit)
    fun onNoticeEvent(chatPackage: BilibiliChatPackage)
    fun onDanmuMsgEvent(msg: DanMuBMessage)
    fun onSuperChatEvent(msg: String)
}