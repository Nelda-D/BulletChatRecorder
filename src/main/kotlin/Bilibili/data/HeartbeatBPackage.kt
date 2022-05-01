package Bilibili.data

import Bilibili.api.Operation
import Bilibili.api.ProtocolVersion
import okio.ByteString.Companion.toByteString

class HeartbeatBPackage() : BilibiliChatPackage(
    "Heartbeat".toByteArray(),
    ProtocolVersion.INT,
    Operation.HEARTBEAT
) {
    companion object {
        val HeartBeatBPackageByteString = HeartbeatBPackage().toPackageHexString().toByteString()
    }
}