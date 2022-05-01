package Bilibili

import Bilibili.api.BilibiliApi
import okio.ByteString.Companion.toByteString
import java.math.BigInteger

fun main() {

    var console = CommonWebSocketConsole()
//    console.test(510)
//    console.test(5520)
//    console.test(843615) // 汉堡王
//    console.test(4795936) // 乌龟酱
//    console.test(1375798) // 压刹channel // 测试用户弹幕活跃数据、
//    console.test(52030) // 火星报 // 测试用户弹幕活跃数据
    console.test(5169315)
}

fun main1() {
//    val str = "[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0]"
    val header = ByteArray(4)
    val testInt = 2147483645
    header[3] = (testInt and 0xff).toByte()
//    val intByteArray = testInt.toByte()
    header[2] = (testInt and 0xff00).toByte()
    for (byte in header) {
        val binary = Integer.toBinaryString((byte.toInt() and 0xFF) + 0x100).substring(1)
        println("$byte -> $binary")
    }
    println(BigInteger(1, header).toString(2))
    println("80 -> ${Integer.toBinaryString(80)}")
    println("2147483645 -> ${Integer.toBinaryString(2147483645)}")
    println(Integer.toBinaryString((2147483645 and 0xFF) + 0x100).substring(1))
//    header.writeInt32BE()
}

fun main2() {
    test()
}

fun test() {
    val testInt = 2147483645
    println("$testInt -> ${Integer.toBinaryString(testInt)}")
    println("$testInt and 0xff -> ${Integer.toBinaryString(testInt and 0xff)}")
    println("$testInt and 0xff00 -> ${Integer.toBinaryString(testInt and 0xff00)}")
    println("$testInt and 0xff000000 -> ${Integer.toBinaryString((testInt.toLong() and 0xff000000).toInt())}")
}

fun main3() {
    val testString = "000000c70010000100000008000000017b22636f6465223a307d"
    val list: List<Byte> = testString.chunked(2).map { it.toInt(16).toByte() }
    val testByteArray = list.toTypedArray().toByteArray()
    val byteString = testByteArray.toByteString()
    val headerByteArray = byteString.substring(0, BilibiliApi.HEADERLENGTH).hex().toUint8Array()
    val bodyByteArray = byteString.substring(BilibiliApi.HEADERLENGTH).toByteArray()
    val packageLength = readInt(headerByteArray, 0, 4)
    println("packageLength=$packageLength")
    val headerLength = readInt(headerByteArray, 4, 2)
    println("headerLength=$headerLength")
    val protocolVersion = readInt(headerByteArray, 6, 2)
    println("protocolVersion=$protocolVersion")
    val operation = readInt(headerByteArray, 8, 4)
    println("operation=$operation")
    val bodyHeaderLength = readInt(headerByteArray, 12, 4)
    println("bodyHeaderLength=$bodyHeaderLength")

    println("---------------------------------")
    val headerByteArray2 = byteString.substring(0, BilibiliApi.HEADERLENGTH).hex().toUint8ByteArray()
    val packageLength2 = readInt(headerByteArray2, 0, 4)
    println("packageLength2=$packageLength2")

//    val valueInt = 199
//    val valueByte = valueInt.toByte()
//    println("$valueInt.toByte() = ${valueByte}")
}

fun readInt(rawMessage: Array<Int>, offset: Int, length: Int): Int {
    var result = 0
    for (i in 0 until length) {
        result += rawMessage[offset + i] shl (8 * (length - 1 - i))
    }
    return result
}

fun String.toUint8Array(): Array<Int> {
    check(length % 2 == 0) { "Must have an even length" }
    return chunked(2).map { it.toInt(16) }
        .toTypedArray()
}

fun readInt(rawMessage: ByteArray, offset: Int, length: Int): Int {
    var result = 0
    for (i in 0 until length) {
        result += rawMessage[offset + i].toInt() and 0xff shl (8 * (length - 1 - i))
    }
    return result
}

fun String.toUint8ByteArray(): ByteArray {
    check(length % 2 == 0) { "Must have an even length" }
    return chunked(2).map { it.toInt(16).toByte() }
        .toTypedArray().toByteArray()
}