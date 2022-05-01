import com.nixxcode.jvmbrotli.common.BrotliLoader
import com.nixxcode.jvmbrotli.dec.BrotliInputStream
import com.sun.xml.internal.ws.util.ByteArrayBuffer
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.math.BigInteger
import java.util.*
import kotlin.experimental.or

//import java.lang.String
val BilibiliHexString =
    "1bd61b002c078c6da4d60fc9d98ef3d1428d20ba0fb16666d5eda6aff1c07e76a6c0137c8fb280030d30a308926ef7ee076e030d50770772b948f9158a04a07f4a53e53aeff3ced994feba9814e5581da0eade2f2523487bc7c2b961b0b40e4b575fb39625e20e6ccaa5d86ad14f84394c5a68c0eeafb5d73f5171c42a4211a84846233251c0fbf6dd4e3600a000f82e4096486281d191fbca162c0ad589120514aeb2b62e5397c93236ad7a51c284053caab159d90cb0894feb635d54eb1a1874efefde78f73246c0fb18b01b43c33984aa32d5c8298816962a7d0895f40ec888419d4c8f07e75b310c391afddf3fd21eb1570931802681e073a6035840dc35027e2fa29ef3a86ab85619302cfefac1b33efd221a62f50cb8b28b7aed9fe088e0006774c59a716f1824faa3287148706f8ca8d82166a2bfe3edb168281ad18fb6b247fcc276ac8bc4e0a405707905098fa47a9603f6bbf449322730b1e00c96c2d765cd544c240c91a50e9acdbb5b72c6cfde18bfb1abfa56c58ce98f8780610db09cd51e562d41d679d86c27494f44f7dff41fff132b429e03eb2604551c5ce0963fcc03d220fe72be2c5f4087e79088c8d000136b9d83dbf1fadd809f9557e11029bca677459151de239b00b108f5a86c3c4ca1a8bf56873d196c7d3cd9883121308f41d67ae23703cefd560c6920780007b8d8411e8d01847ef0a06a802191f619bde3cea9e2351ca3f1fb1d2b1ff114b988499ea1ea10958e0a94398fc64319d289625711c0bb9ad357ff16ec509636028d80e0843de4828c330d990318b5680f2b2073d665eefd9ad56655b2b6b2521b9787c8cf957d531915d351be3d2d723dd17d92bbb4d6628bf8732a29f5d2e34d0dff29225e84eff252a099649a6d92a2f817281045ba06500d4c992f3df50f8c95f7189b92a4490c3ecb952784de6d06fd1b3a4299f383f9cd5d4d541135460dd141e26820ae26befd25531a141eaeeebb2fd5ef819ad539d783570632a5d3c9a23569d796c42e2381f47bb56444d83ec5a5f8dc56375234b2f792aa41a25505e280882092f043e019192539381e5c08335f49002a7a8353ba69c71dc6610f951386ff98e4eed96029564365999a0883064cddb243c35aa864e7300f039352c215bec76ffd6e28aa0d0fc787fe77d59cdfadbd11dfc5b7c87096c12337fbc6611fedbc862f41a1d44017959240f6f9dabaa8e426b6ad7a02e7a84aec84ce8879e5be89df13bb99cf5dfa7b16906450b490a512ea0dda4db12e38f568293c0f64fc41f8e1cf5b8d67eeb806f2158061764a6b191df468d121cfdd6c4d060bc5032f2d2942cc18d51c1d440960ecb35294a6a16ea62172fd6825a44f29f2ceb02c6ece1b64c864188dcf5271953f6d80da0efadee6928943f581d07169c89e5a310cdd93aa08fdccf0a9c3e5677ae7fa6726cf0eee8a91bb3bace5b1ca6bdbf25627fed4a0848574bbe8e373c320b5af6c1ba9527c75503c41252a9a60a74a989d48b83e5395624aab7c5c248af1a4b52d18f1330827dba97c1c642304cbb6b5501e7262e1744fc5ced92ab9624fb9f5d1536215f5cc2af7c25437f5690f7e61ed36fb6a803a6ece8769d72c142976db2ad795b29e2a9d53683cc12be1d0c92d911ae71193f150d683ddb35ffc7ffa00d0fe93306b94929cf279bced9e5f8a3be71c7427a06001534d0ac8c8a2ef32023ced7d1b84ed53ac172ad54178efb54401aa6545458e7f365a2ecbd89fb4cc56965abd7074f2410a2ed55c3a57db0fe71d9095b5c9cfca82100c44d5d03450973427156bf09a92d5e24c192ef1e1b5487ef950f5e7238a1c1f3aa02f4476e27776f51d4f3325bbf9d3ead787473966d66bc2d0961ae357da6b33edc2196ca939959b66ef9fcc246161aff43da34ed66b23e15799c363d25e83c7c3e18c99945f8779fd57feaa0e9b524fd42766f08802f0a77e704216b97c63b8567bf255471f8be07c8c807089c33d5e162ad1963e10bcc415d1671a7e103b9dc1bf78e6134e0d232866d824f0fbff9e0e19dc2ce162516a534ff0a081f8c8989b6086487ab2b3e7b01542829158bb69f8f590ab018b4721e070ae6b588497f72bdef6a8cb5c1958fd3e50fad00be9488a22ca14691061206931b801a2b0ba3a071e703e6845034743c8ee213479f315e31626f648998850c50759389df4b7bd96145c4a4564708453a816c5ca0a29a544f28eaaa370292c24ccd748e08b429aa50364fbadf6eabfb82392d5adb1163df514c883de917ef406a73f3b8a95c560b23d42ae563507bbdb6ff885c9ab4bc292f805"

fun main(args: Array<String>) {
    val result = BrotliLoader.isBrotliAvailable();
    println("BrotliLoader $result")
    val testString = "你好World"
    val s: String
    val testHexString = convertStringToHex(testString)
    println("$testString.convertStringToHex = $testHexString")

    println("BilibiliHexString.hexStringToBytes = " + hexStringToBytes(BilibiliHexString)?.let { bytesToString(it) })
}

private fun convertStringToHex(str: String): String? {
//    val stringBuilder = StringBuilder()
//    val charArray = str.toCharArray()
//    for (c in charArray) {
//        val charToHex = Integer.toHexString(c.code)
//        stringBuilder.append(charToHex)
//        println("$c code is ${c.code}, to hex is $charToHex")
//    }

    var byteAr = str.toByteArray(charset("UTF-8"))

//    for (i in byteAr.indices) {
//        println(
//            "urf-8 bytes byteAr[$i] : " + byteAr[i] + " - " + byteToBinary(byteAr[i], 2) + " - " + byteToBinary(byteAr[i], 16)
//        )
//    }

//    println("Converted Hex from String: ${BigInteger(1, byteAr)}")
//    println("Converted Hex from String: ${BigInteger(1, byteAr).toString(16)}")
//    return stringBuilder.toString()
    return byteToBinary( byteAr, 16)
}

//@Throws(UnsupportedEncodingException::class)
private fun getBytes(string: String): ByteArray? {
    return string.toByteArray(charset("UTF-8"))
}

//@Throws(UnsupportedEncodingException::class)
private fun bytesToString(bytes: ByteArray): String? {
    val byteBrotliDecode = brotilDecode(bytes)
    return String(byteBrotliDecode, charset("UTF-8"))
}


fun hexStringToBytes(hexString: String?): ByteArray? {
    var hexString = hexString
    if (hexString == null || hexString == "") {
        return null
    }
    hexString = hexString.lowercase(Locale.getDefault())
    val length = hexString.length / 2
    val hexChars = hexString.toCharArray()
    val d = ByteArray(length)
    for (i in 0 until length) {
        val pos = i * 2
        val charInt = charToByte(hexChars[pos]).toInt() shl 4
        d[i] = charInt.toByte() or charToByte(hexChars[pos + 1])
//        println("hexStringToBytes d[$i] = ${d[i]} - ${byteToBinary(d[i], 2)} - ${byteToBinary(d[i], 16)}")
    }
    return d
}

private fun charToByte(c: Char): Byte {
    return "0123456789abcdef".indexOf(c).toByte()
}

// byte 转成 二进制
private fun byteToBinary(b: Byte, radix: Int): String? {
    val newBytes = byteArrayOf(b)
    return byteToBinary(newBytes, radix)
}

private fun byteToBinary(b: ByteArray, radix: Int): String? {
    return BigInteger(1, b).toString(radix)
}

fun brotilDecode(byteArray: ByteArray): ByteArray{
    val inputStream = ByteArrayInputStream(byteArray)
    val outputStream = ByteArrayOutputStream()

    val brotliInputStream = BrotliInputStream(inputStream)
    var read = brotliInputStream.read()
    while (read > -1) {
        outputStream.write(read)
        read = brotliInputStream.read()
    }
    return outputStream.toByteArray()
}