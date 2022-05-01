fun ByteArray.writeIntIn4Byte(value: Int, offset: Int) {
    this[offset] = (value ushr 24).toByte()
    this[offset + 1] = (value and 0xff0000 ushr 16).toByte()
    this[offset + 2] = (value and 0xff00 ushr 8).toByte()
    this[offset + 3] = (value and 0xff).toByte()
}

fun ByteArray.writeIntIn2Byte(value: Int, offset: Int) {
    val longValue: Long = value.toLong()
    this[offset] = (longValue and 0xff00 ushr 8).toByte()
    this[offset + 1] = (longValue and 0xff).toByte()
}

fun ByteArray.readInt(offset: Int, length: Int): Int {
    var result = 0
    for (i in 0 until length) {
        result += this[offset + i].toInt() and 0xff shl (8 * (length - 1 - i))
    }
    return result
}