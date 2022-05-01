package Bilibili.data

class RealRoomInfoBean(val code: Int/*, val realRoomId: Long, val uuid: Long*/) {
    val data: Data? = null

    class Data() {
        val room_info: Room_info? = null
        class Room_info(val uid: Long, val room_id: Long, val title: String)
    }
}