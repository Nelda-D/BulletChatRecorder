package Bilibili.util

import com.nixxcode.jvmbrotli.common.BrotliLoader
import com.nixxcode.jvmbrotli.dec.BrotliInputStream
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.util.*
import java.util.regex.Pattern
import java.util.zip.InflaterOutputStream

object BilibiliChatDataHelper {
    init {
        BrotliLoader.isBrotliAvailable();
    }

    fun ByteArray.brotli(): ByteArray {
        val inputStream = ByteArrayInputStream(this)
        val outputStream = ByteArrayOutputStream()

        val brotliInputStream = BrotliInputStream(inputStream)
        var read = brotliInputStream.read()
        while (read > -1) {
            outputStream.write(read)
            read = brotliInputStream.read()
        }
        return outputStream.toByteArray()
    }

    fun ByteArray.zlib(): ByteArray {
        val outputStream = ByteArrayOutputStream()
        val inflaterOutputStream = InflaterOutputStream(outputStream)
        inflaterOutputStream.write(this)
        inflaterOutputStream.close()
        return outputStream.toByteArray()
    }


    fun launchTimer(delay: Long, period: Long, process: () -> Unit): Timer {
        val timer = Timer()
        timer.schedule(object : TimerTask() {
            override fun run() {
                process()
            }

        }, delay, period)
        return timer
    }

}

val CmdKey = "cmd"
val ContextRegex = "(?<=],\").+?(?=\",\\[)"
val UidRegex = "(?<=\",\\[)\\d+"
//val UnameRegex = "(?<=\",\\[)\\d+,\".*?(?=\")"
val UnameRegex = "(?<=\",\\[)\\d+,\".*?(?=\")"
val ColorRegex = "(?<=\"color\\\\\":)\\d+(?=,)"

val KeyPattern = Pattern.compile("(?<=\"$CmdKey\":\").*?(?=\")")

fun String.getCmdString(): String? {
    var cmd = ""
    val match = KeyPattern.matcher(this)
    if (match.find()) {
        cmd = match.group()
    }
    return cmd
}

fun String.getStringValueByKeyInJsonString(key: String): String? {
    var cmd = ""
    val pattern = Pattern.compile("(?<=\"$key\":\").*?(?=\")")
    val match = pattern.matcher(this)
    if (match.find()) {
        cmd = match.group()
    }
    return cmd
}

fun String.getLongValueByKeyInJsonString(key: String): Long {
    var value = "1"
    val pattern = Pattern.compile("(?<=\"$key\":).*?(?=,)")
    val match = pattern.matcher(this)
    if (match.find()) {
        value = match.group()
    }
    return value.toLong()
}

fun String.getValueByRegexInJsonString(regex: String): String? {
    var cmd = ""
    val pattern = Pattern.compile(regex)
    val match = pattern.matcher(this)
    if (match.find()) {
        cmd = match.group()
    }
    return cmd
}


