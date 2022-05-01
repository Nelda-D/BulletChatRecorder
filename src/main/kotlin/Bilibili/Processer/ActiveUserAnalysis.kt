package Bilibili.Processer

import Bilibili.data.BilibiliChatPackage
import Bilibili.data.DanMuBMessage
import java.text.SimpleDateFormat
import java.util.*


class ActiveUserAnalysis : BaseBiliProcesser() {
    val allAuList = mutableListOf<ActiveUser>()
    val allAuIdList = mutableListOf<Long>()
    val auList = mutableListOf<ActiveUser>()
    val auIdList = mutableListOf<Long>()

    fun getRecentlyActiveUserCount(): Int {
        // test
//        println("获取最近活跃用户 开始前")
//        printAuList(allAuList)
        // test
//        for (au in auList) {
//            if (!au.isRecentlyActive()) {
//                auIdList.remove(au.uid)
//                auList.remove(au)
//            }
//        }
        for (i in auList.size - 1 downTo 0) {
            if (!auList[i].isRecentlyActive()) {
                auIdList.remove(auList[i].uid)
                auList.removeAt(i)
            }
        }
        // test
        println("去除不活跃用户后的最近活跃用户列表 ${allAuIdList.size} - ${allAuList.size}, ${auList.size} - ${auIdList.size}")
        printAuList(auList)
        // test
        return auList.size
    }

    override fun onNoticeEvent(chatPackage: BilibiliChatPackage) {
//        when (chatPackage.operation.name) {
//            CmdType.DANMU_MSG.name -> {
//
//            }
//        }
    }

    override fun onDanmuMsgEvent(msg: DanMuBMessage) {
        checkIdInList(allAuIdList, allAuList, msg.uid, msg.time, msg.uname)
        // test
        printAuList(auList)
        // test
        checkIdInList(auIdList, auList, msg.uid, msg.time, msg.uname)
    }

    fun checkIdInList(
        idList: MutableList<Long>, auList: MutableList<ActiveUser>, newUid: Long,
        newTime: Long, newName: String
    ) {
        if (idList.contains(newUid)) {
            for (au in auList) {
                if (au.uid == newUid) {
                    au.updateTime(newTime)
                    break
                }
            }
            println("checkIdInList 已经包含 $newUid")
        } else {
            idList.add(newUid)
            auList.add(ActiveUser(newUid, newTime, newName))
            println("checkIdInList 添加 $newUid")
        }
    }

    fun printAuList(auList: MutableList<ActiveUser>) {
        println("########################################################")
        for (au in auList) {
            au.print()
        }
        println("########################################################")
    }

    data class ActiveUser(val uid: Long, var time: Long, val name: String = "unknown") {
        var sendMsgCount = 1
        val RECENTLY_TIME = 1 * 60 * 1000 // For test
//        val RECENTLY_TIME = 10 * 60 * 1000 // ten minutes

        fun updateTime(newTime: Long) {
            println("updateTime $uid - $time -> $newTime , ${sendMsgCount + 1}")
            time = newTime
            sendMsgCount++
        }

        fun isRecentlyActive(): Boolean {
            return System.currentTimeMillis() - time * 1000 < RECENTLY_TIME
        }

        override fun equals(other: Any?): Boolean {
            if (other is ActiveUser) {
                return uid == other.uid
            }
            return super.equals(other)
        }

        companion object {
            val ft = SimpleDateFormat("yyyy-MM-dd hh-mm-ss")
        }

        fun print() {
            val timeString = ft.format(time * 1000)
            println("# $name - $uid - $timeString - $sendMsgCount")
        }
    }

}