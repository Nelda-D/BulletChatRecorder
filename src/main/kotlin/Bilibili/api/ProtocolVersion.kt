package Bilibili.api

enum class ProtocolVersion(val code: Int) {
    /*
值  Body格式            说明
0   JSON               JSON纯文本
1   Int 32 Big Endian  Body 内容为房间人气值
2   Buffer             压缩过的 Buffer，Body 内容需要用zlib.inflate解压出一个新的数据包，然后从数据包格式那一步重新操作一遍
3   Buffer             压缩信息,需要brotli解压,然后从数据包格式 那一步重新操作一遍
    */
    JSON(0),
    INT(1),
    ZLIB_INFLATE(2),
    BROTLI(3),
    UNKNOWN(-1),
    ;

    companion object {
        fun getByCode(code: Int): ProtocolVersion {
            return values().associateBy { it.code }[code] ?: UNKNOWN
        }
    }

}