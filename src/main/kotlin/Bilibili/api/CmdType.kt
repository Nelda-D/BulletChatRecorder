package Bilibili.api

enum class CmdType(type:String) {
    DANMU_MSG("DANMU_MSG"),
    SEND_GIFT("SEND_GIFT"),
    INTERACT_WORD("INTERACT_WORD"),
    SUPER_CHAT_MESSAGE("SUPER_CHAT_MESSAGE"),

    ;

//    companion object {
//        fun getByCode(type: String): CmdType {
//            return CmdType.values().associateBy { it.type }[] ?: Operation.UNKNOWN
//        }
//    }
}