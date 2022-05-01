package Bilibili

import Bilibili.api.BilibiliService
import Bilibili.api.BilibiliServiceCreate
import Bilibili.api.GetDanMuInfoException
import Bilibili.api.GetRoomIdException
import Bilibili.data.DanMuInfoBean
import Bilibili.data.RealRoomInfoBean
import kotlinx.coroutines.*
import okhttp3.internal.wait
import print
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.*


class CommonWebSocketConsole() {

//    fun getRealRoomId(roomId: Long) {
//
//        bilibiliService.getRealRoomId(roomId).enqueue(object : Callback<RealRoomInfoBean> {
//            override fun onResponse(call: Call<RealRoomInfoBean>, response: Response<RealRoomInfoBean>) {
//                val room = response.body()
//                val realRoomId = room?.data?.room_info?.room_id ?: -1
//                room?.let {
//                    println("getRealRoomId onResponse code=${room.code}")
//                    println("getRealRoomId onResponse uid=${room.data?.room_info?.uid}")
//                    println("getRealRoomId onResponse realroom_id=${room.data?.room_info?.room_id}")
//                    println("getRealRoomId onResponse title=${room.data?.room_info?.title}")
//                    getToken(bilibiliService, realRoomId)
//                }
//            }
//
//            override fun onFailure(call: Call<RealRoomInfoBean>, t: Throwable) {
//                println("getRealRoomId onFailure ${t.message}")
//            }
//
//        })
//    }

    private suspend fun getRealRoomId(service: BilibiliService, roomId: Long) = suspendCoroutine<Long> {
        service.getRealRoomId(roomId).enqueue(object : Callback<RealRoomInfoBean> {
            override fun onResponse(call: Call<RealRoomInfoBean>, response: Response<RealRoomInfoBean>) {
                val room = response.body()
                val realRoomId = room?.data?.room_info?.room_id ?: -1
                room?.print()
                if (realRoomId != -1L && realRoomId != 0L) {
                    it.resume(realRoomId)
                } else {
                    it.resumeWithException(GetRoomIdException())
                }
            }

            override fun onFailure(call: Call<RealRoomInfoBean>, t: Throwable) {
                println("getRealRoomId onFailure ${t.message}")
                it.resumeWithException(GetRoomIdException(t))
            }
        })
    }


//    fun getToken(service: BilibiliService, realRoomId: Long) {
//        service.getToken(realRoomId).enqueue(object : Callback<DanMuInfoBean> {
//            override fun onResponse(call: Call<DanMuInfoBean>, response: Response<DanMuInfoBean>) {
//                val info = response.body()
//                info?.let {
//                    println("getToken onResponse code=${info.code}")
//                    println("getToken onResponse code=${info.data?.token}")
//                    if (info.data?.token == null) {
//                        onFailure(call, Throwable("Token is null"))
//                        return
//                    }
//                    if (info.data.hostList == null || info.data.hostList?.size <= 0) {
//                        onFailure(call, Throwable("host is null"))
//                        return
//                    }
//                    println("getToken onResponse wssUrl=${info.data.hostList.get(0).toWssUrl()}")
//                    startConnect(realRoomId, info.data?.token, info.data.hostList.get(0).toWssUrl())
//
//                }
//            }
//
//            override fun onFailure(call: Call<DanMuInfoBean>, t: Throwable) {
//                println("getToken onFailure ${t.message}")
//            }
//
//        })
//    }

    private suspend fun getToken(service: BilibiliService, realRoomId: Long) =
        suspendCoroutine<DanMuInfoBean.DanMuInfoResult> { cont ->
            service.getToken(realRoomId).enqueue(object : Callback<DanMuInfoBean> {
                override fun onResponse(call: Call<DanMuInfoBean>, response: Response<DanMuInfoBean>) {
                    val info = response.body()

                    val token = info?.data?.token ?: ""
                    if (token.isEmpty()) {
                        cont.resumeWithException(Throwable("GetDanmuInfo token is empty"))
                    }
                    if (info?.data?.hostList == null || info.data.hostList.isEmpty()) {
                        cont.resumeWithException(Throwable("GetDanmuInfo host is null"))
                        return
                    }
                    val data = DanMuInfoBean.DanMuInfoResult(token, info.data.hostList[0].toWssUrl())
                    cont.resume(data)
                }

                override fun onFailure(call: Call<DanMuInfoBean>, t: Throwable) {
//                println("getToken onFailure ${t.message}")
                    cont.resumeWithException(t)
                }

            })
        }


    fun startConnect(roomId: Long, token: String, wssUrl: String) {
        val client = BilibiliWebSocketClient(roomId, token, wssUrl)
        client.startConnect()
    }

    var mRealRoomId: Long? = null
    var mDanMuInfoResult: DanMuInfoBean.DanMuInfoResult? = null
    var mDeferred: Deferred<DanMuInfoBean.DanMuInfoResult>? = null

    fun test(roomId: Long) {
        val bilibiliService = BilibiliServiceCreate.create(BilibiliService::class.java)
        runBlocking(BilibiliErrorHandler) {
//        GlobalScope.launch(BilibiliErrorHandler) {
            println("get real room id start")

            val realRoomId = withContext(Dispatchers.IO) {
                getRealRoomId(bilibiliService, roomId)
            }
            mRealRoomId = realRoomId
            println("get real room id $realRoomId")
//            val danMuInfoResult = withContext(Dispatchers.IO) {
//                getToken(bilibiliService, realRoomId)
//            }
            mDeferred = async {
                getToken(bilibiliService, realRoomId)
            }
            mDanMuInfoResult = mDeferred?.await()
//            mDanMuInfoResult = danMuInfoResult
            println("get token ${mDanMuInfoResult?.token}")
        }

        mRealRoomId?.let {
            mDanMuInfoResult?.let { it1 -> startConnect(it, it1.token, it1.wssUrl) }
        }
        println("start connect")
    }

    val BilibiliErrorHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        when (throwable) {
            is GetRoomIdException -> println("BilibiliErrorHandler 1 " + throwable.message)
            is GetDanMuInfoException -> println("BilibiliErrorHandler 2 " + throwable.message)
            else -> println("BilibiliErrorHandler else " + throwable.message)
        }
    }
}

