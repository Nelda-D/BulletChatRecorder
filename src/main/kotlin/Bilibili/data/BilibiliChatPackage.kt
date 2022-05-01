package Bilibili.data

import Bilibili.api.BilibiliApi
import Bilibili.api.Operation
import Bilibili.api.ProtocolVersion
import Bilibili.toUint8ByteArray
import okio.ByteString
import readInt
import writeIntIn2Byte
import writeIntIn4Byte
import java.math.BigInteger

open class BilibiliChatPackage(
    val body: ByteArray,
    val packetLength: Int,
    val protocolVersion: ProtocolVersion,
    val operation: Operation,
    val headerLength: Int = 16,
    val bodyHeaderLength: Int = 1
) {
    constructor(
        body: ByteArray,
        protocolVersion: ProtocolVersion,
        operation: Operation
    ) : this(
        body,
        body.size + BilibiliApi.HEADERLENGTH,
        protocolVersion, operation
    )

    /**
    | offset | length | type | endian | name | explain |
    | --- | --- | --- | --- | --- | --- |
    | 0 | 4 | int | Big Endian | Packet Length | Packet Length |
    | 4 | 2 | int | Big Endian | Header Length | packet head Length (fixed for `16`)  |
    | 6 | 2 | int | Big Endian | Protocol Version | Protocol Version   |
    | 8 | 4 | int | Big Endian | Operation | Operation type |
    | 12 | 4 | int | Big Endian | Sequence Id |  Sequence Id packet head length (fixed `1`) |
    | 16 | \- | byte\[\] | \- | Body | Data content |
     */
    fun toPackageHexString(): ByteArray {
        val header = ByteArray(headerLength)
        header.apply {
            writeIntIn4Byte(packetLength, 0)
            writeIntIn2Byte(headerLength, 4)
            writeIntIn2Byte(protocolVersion.code, 6)
            writeIntIn4Byte(operation.code, 8)
            writeIntIn4Byte(bodyHeaderLength, 12)
        }
        return (header + body)
    }

    private fun byteToBinary(b: ByteArray, radix: Int): String {
        return BigInteger(1, b).toString(radix)
    }

    fun String.toUint8ByteArray(): ByteArray {
        check(length % 2 == 0) { "Must have an even length" }
        return chunked(2).map { it.toInt(16).toByte() }
            .toTypedArray().toByteArray()
    }

    companion object {
        fun ByteString.toChatPackage(): BilibiliChatPackage {
            if (this.size < 16) throw RuntimeException("Unknown package")
//            val headerByteArray = this.substring(0, BilibiliApi.HEADERLENGTH).hex().toUint8ByteArray()
            val headerByteArray = this.substring(0, BilibiliApi.HEADERLENGTH).toByteArray()
            val bodyByteArray = this.substring(BilibiliApi.HEADERLENGTH).toByteArray()
            return BilibiliChatPackage(
                bodyByteArray,
                headerByteArray.readInt(0, 4),
                ProtocolVersion.getByCode(headerByteArray.readInt(6, 2)),
                Operation.getByCode(headerByteArray.readInt(8, 4)),
                headerByteArray.readInt(4, 2),
                headerByteArray.readInt(12, 4)
            )
        }

        fun ByteString.toChatPackageList(): List<BilibiliChatPackage> {
            if(this.size < 16) throw RuntimeException("Unknown Packages")
            var offset = 0
            val subPkgList = mutableListOf<BilibiliChatPackage>()
            while (this.size > offset){
                val header = this.substring(offset, offset + BilibiliApi.HEADERLENGTH).toByteArray()
                val pkgLength = header.readInt(0, 4)
//                println("ND toChatPackageList size=$size, pkgLength=$pkgLength")
                val subPkg = this.substring(offset, offset + pkgLength).toChatPackage()
                subPkgList.add(subPkg)
                offset += pkgLength
            }
            return subPkgList
        }
    }


}
