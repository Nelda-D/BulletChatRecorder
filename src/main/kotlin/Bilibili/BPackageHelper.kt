package Bilibili

class BPackageHelper {
    /*{
        "uid": "0 表示未登录，否则为用户ID",
        "roomid": "房间ID",
        "protover": "协议版本号",
        "platform": "平台",
        "clientver": "客户端版本号",
        "token": "接口返回的 token"
    }*/
    fun getAuthenticatePackage(id:Long, token:String){
        val uid = 0
        val roomId = id
        val protover = 2
        val platform = "web"
        val clientver = "1.6.3"
    }
}