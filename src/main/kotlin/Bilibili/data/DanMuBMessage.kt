package Bilibili.data

import Bilibili.util.*

class DanMuBMessage(
    cmdType: String, time: Long,
    uname: String, uid: Long,
    val message: String, val color: String
) : BilibiliMessage(cmdType, time, uname, uid) {

    //    val danmuString = """{"cmd":"DANMU_MSG","info":[[0,1,25,16777215,1650549151667,1531851253,0,"4a612ee6",0,0,0,"",0,"{}","{}",{"mode":0,"show_player_type":0,"extra":"{\"send_from_me\":false,\"mode\":0,\"color\":16777215,\"dm_type\":0,\"font_size\":25,\"player_mode\":1,\"show_player_type\":0,\"content\":\"动态？\",\"user_hash\":\"1247882982\",\"emoticon_unique\":\"\",\"bulge_display\":0,\"recommend_score\":0,\"direction\":0,\"pk_direction\":0,\"quartet_direction\":0,\"yeah_space_type\":\"\",\"yeah_space_url\":\"\",\"jump_to_url\":\"\",\"space_type\":\"\",\"space_url\":\"\"}"}],"动态？",[178959956,"可爱飞飞酱",0,0,0,10000,1,""],[4,"王菠萝","菠萝赛东°",37527,6067854,"",0,6067854,6067854,6067854,0,1,197098],[1,0,9868950,"\u003e50000",0],["",""],0,0,null,{"ts":1650549151,"ct":"3F70DD5D"},0,0,null,null,0,28]}"""
    companion object {
        fun create(bodyString: String): DanMuBMessage {
            return DanMuBMessage(
                bodyString.getCmdString() ?: "null",
                bodyString.getLongValueByKeyInJsonString("ts"),
                bodyString.getValueByRegexInJsonString(UnameRegex)?.replace(Regex("\\d+,\""), "") ?: "unknown",
                bodyString.getValueByRegexInJsonString(UidRegex)?.toLong() ?: -1,
                bodyString.getValueByRegexInJsonString(ContextRegex) ?: "null",
                bodyString.getValueByRegexInJsonString(ColorRegex) ?: "16777215"
            )
        }
    }


}
