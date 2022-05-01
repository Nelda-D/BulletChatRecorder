package Bilibili.Processer

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class ActiveUserAnalysisTest {

    @Test
    fun onNoticeEvent() {
    }

    @Test
    fun onDanmuMsgEvent() {
    }

    @Test
    fun onActiveUserEqualsTest(){
        val user1 = ActiveUserAnalysis.ActiveUser(1, 2)
        val user2 = ActiveUserAnalysis.ActiveUser(1, 23)
        val user3 = ActiveUserAnalysis.ActiveUser(2, 23)
        val list = mutableListOf<ActiveUserAnalysis.ActiveUser>()
        list.add(user1)
        assertEquals(true, list.contains(user2))
        assertEquals(false, list.contains(user3))
    }
}