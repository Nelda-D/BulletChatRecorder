import Bilibili.data.DanMuInfoBean
import Bilibili.data.RealRoomInfoBean

fun RealRoomInfoBean.print(){
    this.let {
        println("RealRoomInfoBean.Bilibili.util.print code=${it.code}")
        println("RealRoomInfoBean.Bilibili.util.print uid=${it.data?.room_info?.uid}")
        println("RealRoomInfoBean.Bilibili.util.print realroom_id=${it.data?.room_info?.room_id}")
        println("RealRoomInfoBean.Bilibili.util.print title=${it.data?.room_info?.title}")
    }
}

fun DanMuInfoBean.print(){
    this.let {
        println("getToken onResponse code=${it.code}")
        println("getToken onResponse code=${it.data?.token}")
        if (it.data?.hostList?.isNotEmpty() == true) {
            println("getToken onResponse wssUrl=${it.data.hostList[0].toWssUrl()}")
        }
    }
}