import com.sun.xml.internal.ws.util.ByteArrayBuffer;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;

public class Test {

    public String strToHexString(String inputString) {
        String s = "Bilibili.test";
        s.charAt(1);
        byte[] bytes = new byte[0];
        bytes = inputString.getBytes();
        StringBuilder sb = new StringBuilder(bytes.length * 2);
        // 将字节数组中每个字节拆解成2位16进制整数
        for (int i = 0; i < bytes.length; i++) {
            sb.append(inputString.charAt((bytes[i] & 0xf0) >> 4));
            sb.append(inputString.charAt((bytes[i] & 0x0f)));
        }
        return sb.toString();
    }

    private String convertStringToHex(String str) {
        StringBuilder stringBuilder = new StringBuilder();

        char[] charArray = str.toCharArray();

        for (char c : charArray) {
            String charToHex = Integer.toHexString(c);
            stringBuilder.append(charToHex);
        }

        System.out.println("Converted Hex from String: " + stringBuilder.toString());
        return stringBuilder.toString();
    }

    private String hexStringToString(String hexString) {
        StringBuilder stringBuilder = new StringBuilder();
//        byte[] bytes = Hex.decodeHex(hexString.toCharArray());
//        System.out.println(new String(bytes, "UTF-8"));

//        byte b = Byte.parseByte(hexString, 16);
//        new String(byte[] bytes)
        return "null";
    }

    private byte[] getBytes(String string) throws UnsupportedEncodingException {
        byte[] b = string.getBytes("UTF-8");
        return b;
    }

    private String bytesToString(byte[] bytes) throws UnsupportedEncodingException {
        String s = new String(bytes, "UTF-8");
        return s;
    }

    public byte[] hexStringToBytes(String hexString) {
        if (hexString == null || hexString.equals("")) {
            return null;
        }
        hexString = hexString.toLowerCase();
        int length = hexString.length() / 2;
        char[] hexChars = hexString.toCharArray();
        byte[] d = new byte[length];
        for (int i = 0; i < length; i++) {
            int pos = i * 2;
            d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
        }
        return d;
    }

    private byte charToByte(char c) {
        return (byte) "0123456789abcdef".indexOf(c);
    }

    // byte 转成 二进制
    private String byteToBinary(byte b) {
        byte[] newBytes = new byte[b];
        return new BigInteger(1, newBytes).toString(2);
    }
}
