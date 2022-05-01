package Bilibili

import Bilibili.Processer.BaseBiliProcesser
import Bilibili.data.DanMuBMessage
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*

class DanmuBiliMsgRecorder(val roomId: Long) : BaseBiliProcesser() {

    var writeString = ""
    var fileOutputStream: FileOutputStream? = null
    var msgFile: File? = null

    override fun onDanmuMsgEvent(msg: DanMuBMessage) {
        super.onDanmuMsgEvent(msg)
        writeString = msg.toJsonString()
//        println("# " + writeString)
        writeMsgInFile(writeString.toByteArray())
    }

    override fun onSuperChatEvent(msg: String) {
        super.onSuperChatEvent(msg)
//        println("# " + msg)
        writeMsgInFile(msg.toByteArray())
    }

    override fun init(register: () -> Unit) {
        super.init(register)
    }

    override fun close(unregister: () -> Unit) {
        super.close(unregister)
        fileOutputStream?.close()
    }

    private fun writeMsgInFile(msg: ByteArray) {
        if (fileOutputStream == null) {
            val ft = SimpleDateFormat("yyyy-MM-dd hh-mm-ss")
            val time = ft.format(Date())
            val curDir = System.getProperty("user.dir")
            val filePath = "$curDir\\LiveData\\"
            val fileName = "$roomId-${time}.txt"
            println("Write file: $filePath$fileName")
//            //true表示在文件末尾追加
            msgFile = File(filePath + fileName)
            if (msgFile?.exists() == false) msgFile?.createNewFile()
            fileOutputStream = msgFile?.outputStream()
        }
        fileOutputStream?.write(msg)
        fileOutputStream?.write("\r\n".toByteArray())
    }
}