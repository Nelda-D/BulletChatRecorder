package Bilibili.Processer

import Bilibili.BiliMsgEventListener
import Bilibili.data.BilibiliChatPackage
import Bilibili.data.DanMuBMessage

open class BaseBiliProcesser : BiliMsgEventListener {
    override fun init(register: () -> Unit) {
        register()
    }

    override fun close(unregister: () -> Unit) {
        unregister()
    }

    override fun onNoticeEvent(chatPackage: BilibiliChatPackage) {
    }

    override fun onDanmuMsgEvent(msg: DanMuBMessage) {
    }

    override fun onSuperChatEvent(msg: String) {
    }
}