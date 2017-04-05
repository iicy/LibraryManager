package com.ljy.librarymanager.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by jiayu on 2017/4/3.
 */

public class Encryption {

    //md5

    /**
     * 将16进制范围的字母或数字的字符转换成对应的整数， 0－9 a－f｜A－F则转换成10－15
     *
     * @param ch
     * @return
     */
    private static char char2Int(char ch) {
        if (ch >= '0' && ch <= '9')
            return (char) (ch - '0');
        if (ch >= 'a' && ch <= 'f')
            return (char) (ch - 'a' + 10);
        if (ch >= 'A' && ch <= 'F')
            return (char) (ch - 'A' + 10);
        return ' ';
    }

    /**
     * 将两个字符转换成一个字节表示
     *
     * @param str
     * @return
     */
    private static byte str2Bin(char[] str) {
        byte chn;
        char[] tempWord = new char[2];

        tempWord[0] = char2Int(str[0]); // make the B to 11 -- 00001011
        tempWord[1] = char2Int(str[1]); // make the 0 to 0 -- 00000000
//		System.out.println("tempWord[0]=" + tempWord[0] + " tempWord[1]=" + tempWord[1]);

        chn = (byte) ((tempWord[0] << 4) | tempWord[1]); // to change the BO to
        // 10110000

        return chn;
    }

    /**
     * 将32长度的字符数组压缩生成标准的16位字节数组的MD5
     *
     * @param md5chs32len 32长度的MD5字符串的字符数组
     * @return
     */
    public static byte[] compress(char[] md5chs32len) {
        char[] tem = new char[2];
        byte[] sDst = new byte[md5chs32len.length / 2];
        int j = 0;
        for (int i = 0; i + 1 < md5chs32len.length; i += 2) {
            tem[0] = md5chs32len[i];
            tem[1] = md5chs32len[i + 1];
            sDst[j++] = (byte) (str2Bin(tem));
//			System.out.println("tem[0]=" + (int)tem[0] + " tem[1]=" +(int)tem[1] + " sDst["+ (j-1) + "]=" + sDst[j-1]);
        }
        return sDst;
    }

    /**
     * 将16字节的MD5数组转换成32长度的字符串
     *
     * @param md5b16
     * @return
     */
    public static String unCompress(byte[] md5b16) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < md5b16.length; i++) {
            byte b = md5b16[i];
            sb.append(Integer.toHexString((b >> 4) & 0x0F));
            sb.append(Integer.toHexString(b & 0x0F));
        }
        return sb.toString().toUpperCase();
    }

    /**
     * 将明文用MD5算法加密后并压缩成16字节数组
     *
     * @param text 明文
     * @return 16字节数组
     * @author sunyiping 2011-10-13 下午12:50:51
     */
    public static byte[] encrypt2MD5toByte16(String text) {
        String md5 = getMD5Str32(text);
//		System.out.println("md5=" + md5);
        if (md5 != null) {
            return compress(md5.toCharArray());
        }
        return null;
    }

    /**
     * 获取MD5加密后的32位字符串
     *
     * @param str 明文
     * @return 返回MD5加密后的32位串
     * @author sunyiping 2011-10-13 下午12:12:45
     */
    public static String getMD5Str32(String str) {
        if (str == null || "".equals(str)) {
            return null;
        }
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(str.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            return null;
        } catch (UnsupportedEncodingException e) {
            return null;
        }

        return byteToHexString(messageDigest.digest());
    }

    static char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8',
            '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    /**
     * 把byte[]数组转换成十六进制字符串表示形式
     *
     * @param tmp 要转换的byte[]
     * @return 十六进制字符串表示形式
     */
    private static String byteToHexString(byte[] tmp) {
        String s;
        // 用字节表示就是 16 个字节
        char str[] = new char[16 * 2]; // 每个字节用 16 进制表示的话，使用两个字符，
        // 所以表示成 16 进制需要 32 个字符
        int k = 0; // 表示转换结果中对应的字符位置
        for (int i = 0; i < 16; i++) { // 从第一个字节开始，对 MD5 的每一个字节
            // 转换成 16 进制字符的转换
            byte byte0 = tmp[i]; // 取第 i 个字节
            str[k++] = hexDigits[byte0 >>> 4 & 0xf]; // 取字节中高 4 位的数字转换,
            // >>> 为逻辑右移，将符号位一起右移
            str[k++] = hexDigits[byte0 & 0xf]; // 取字节中低 4 位的数字转换
        }
        s = new String(str); // 换后的结果转换为字符串
        return s;
    }


    /////////////////////////////////////////////
    //base64

    private static final byte[] encodingTable = {(byte) 'A', (byte) 'B',
            (byte) 'C', (byte) 'D', (byte) 'E', (byte) 'F', (byte) 'G',
            (byte) 'H', (byte) 'I', (byte) 'J', (byte) 'K', (byte) 'L',
            (byte) 'M', (byte) 'N', (byte) 'O', (byte) 'P', (byte) 'Q',
            (byte) 'R', (byte) 'S', (byte) 'T', (byte) 'U', (byte) 'V',
            (byte) 'W', (byte) 'X', (byte) 'Y', (byte) 'Z', (byte) 'a',
            (byte) 'b', (byte) 'c', (byte) 'd', (byte) 'e', (byte) 'f',
            (byte) 'g', (byte) 'h', (byte) 'i', (byte) 'j', (byte) 'k',
            (byte) 'l', (byte) 'm', (byte) 'n', (byte) 'o', (byte) 'p',
            (byte) 'q', (byte) 'r', (byte) 's', (byte) 't', (byte) 'u',
            (byte) 'v', (byte) 'w', (byte) 'x', (byte) 'y', (byte) 'z',
            (byte) '0', (byte) '1', (byte) '2', (byte) '3', (byte) '4',
            (byte) '5', (byte) '6', (byte) '7', (byte) '8', (byte) '9',
            (byte) '+', (byte) '/'};

    /**
     * encode the input data producong a base 64 encoded byte array.
     *
     * @return a byte array containing the base 64 encoded data.
     */
    public static byte[] encode(byte[] data) {
        byte[] bytes;

        int modulus = data.length % 3;
        if (modulus == 0) {
            bytes = new byte[4 * data.length / 3];
        } else {
            bytes = new byte[4 * ((data.length / 3) + 1)];
        }

        int dataLength = (data.length - modulus);
        int a1, a2, a3;
        for (int i = 0, j = 0; i < dataLength; i += 3, j += 4) {
            a1 = data[i] & 0xff;
            a2 = data[i + 1] & 0xff;
            a3 = data[i + 2] & 0xff;

            bytes[j] = encodingTable[(a1 >>> 2) & 0x3f];
            bytes[j + 1] = encodingTable[((a1 << 4) | (a2 >>> 4)) & 0x3f];
            bytes[j + 2] = encodingTable[((a2 << 2) | (a3 >>> 6)) & 0x3f];
            bytes[j + 3] = encodingTable[a3 & 0x3f];
        }

		/*
         * process the tail end.
		 */
        int b1, b2, b3;
        int d1, d2;

        switch (modulus) {
            case 0: /* nothing left to do */
                break;
            case 1:
                d1 = data[data.length - 1] & 0xff;
                b1 = (d1 >>> 2) & 0x3f;
                b2 = (d1 << 4) & 0x3f;

                bytes[bytes.length - 4] = encodingTable[b1];
                bytes[bytes.length - 3] = encodingTable[b2];
                bytes[bytes.length - 2] = (byte) '=';
                bytes[bytes.length - 1] = (byte) '=';
                break;
            case 2:
                d1 = data[data.length - 2] & 0xff;
                d2 = data[data.length - 1] & 0xff;

                b1 = (d1 >>> 2) & 0x3f;
                b2 = ((d1 << 4) | (d2 >>> 4)) & 0x3f;
                b3 = (d2 << 2) & 0x3f;

                bytes[bytes.length - 4] = encodingTable[b1];
                bytes[bytes.length - 3] = encodingTable[b2];
                bytes[bytes.length - 2] = encodingTable[b3];
                bytes[bytes.length - 1] = (byte) '=';
                break;
        }

        return bytes;
    }

    /*
     * set up the decoding table.
     */
    private static final byte[] decodingTable;

    static {
        decodingTable = new byte[128];

        for (int i = 0; i < 128; i++) {
            decodingTable[i] = (byte) -1;
        }

        for (int i = 'A'; i <= 'Z'; i++) {
            decodingTable[i] = (byte) (i - 'A');
        }

        for (int i = 'a'; i <= 'z'; i++) {
            decodingTable[i] = (byte) (i - 'a' + 26);
        }

        for (int i = '0'; i <= '9'; i++) {
            decodingTable[i] = (byte) (i - '0' + 52);
        }

        decodingTable['+'] = 62;
        decodingTable['/'] = 63;
    }

    /**
     * decode the base 64 encoded input data.
     *
     * @return a byte array representing the decoded data.
     */
    public static byte[] decode(byte[] data) {

        byte[] bytes;
        byte b1, b2, b3, b4;

        data = discardNonBase64Bytes(data);

        if (data[data.length - 2] == '=') {
            bytes = new byte[(((data.length / 4) - 1) * 3) + 1];
        } else if (data[data.length - 1] == '=') {
            bytes = new byte[(((data.length / 4) - 1) * 3) + 2];
        } else {
            bytes = new byte[((data.length / 4) * 3)];
        }

        for (int i = 0, j = 0; i < data.length - 4; i += 4, j += 3) {
            b1 = decodingTable[data[i]];
            b2 = decodingTable[data[i + 1]];
            b3 = decodingTable[data[i + 2]];
            b4 = decodingTable[data[i + 3]];

            bytes[j] = (byte) ((b1 << 2) | (b2 >> 4));
            bytes[j + 1] = (byte) ((b2 << 4) | (b3 >> 2));
            bytes[j + 2] = (byte) ((b3 << 6) | b4);
        }

        if (data[data.length - 2] == '=') {
            b1 = decodingTable[data[data.length - 4]];
            b2 = decodingTable[data[data.length - 3]];

            bytes[bytes.length - 1] = (byte) ((b1 << 2) | (b2 >> 4));
        } else if (data[data.length - 1] == '=') {
            b1 = decodingTable[data[data.length - 4]];
            b2 = decodingTable[data[data.length - 3]];
            b3 = decodingTable[data[data.length - 2]];

            bytes[bytes.length - 2] = (byte) ((b1 << 2) | (b2 >> 4));
            bytes[bytes.length - 1] = (byte) ((b2 << 4) | (b3 >> 2));
        } else {
            b1 = decodingTable[data[data.length - 4]];
            b2 = decodingTable[data[data.length - 3]];
            b3 = decodingTable[data[data.length - 2]];
            b4 = decodingTable[data[data.length - 1]];

            bytes[bytes.length - 3] = (byte) ((b1 << 2) | (b2 >> 4));
            bytes[bytes.length - 2] = (byte) ((b2 << 4) | (b3 >> 2));
            bytes[bytes.length - 1] = (byte) ((b3 << 6) | b4);
        }

        return bytes;
    }

    /**
     * decode the base 64 encoded String data.
     *
     * @return a byte array representing the decoded data.
     */
    public static byte[] decode(String data) {
        byte[] bytes;
        byte b1, b2, b3, b4;

        data = discardNonBase64Chars(data);

        if (data.charAt(data.length() - 2) == '=') {
            bytes = new byte[(((data.length() / 4) - 1) * 3) + 1];
        } else if (data.charAt(data.length() - 1) == '=') {
            bytes = new byte[(((data.length() / 4) - 1) * 3) + 2];
        } else {
            bytes = new byte[((data.length() / 4) * 3)];
        }

        for (int i = 0, j = 0; i < data.length() - 4; i += 4, j += 3) {
            b1 = decodingTable[data.charAt(i)];
            b2 = decodingTable[data.charAt(i + 1)];
            b3 = decodingTable[data.charAt(i + 2)];
            b4 = decodingTable[data.charAt(i + 3)];

            bytes[j] = (byte) ((b1 << 2) | (b2 >> 4));
            bytes[j + 1] = (byte) ((b2 << 4) | (b3 >> 2));
            bytes[j + 2] = (byte) ((b3 << 6) | b4);
        }

        if (data.charAt(data.length() - 2) == '=') {
            b1 = decodingTable[data.charAt(data.length() - 4)];
            b2 = decodingTable[data.charAt(data.length() - 3)];

            bytes[bytes.length - 1] = (byte) ((b1 << 2) | (b2 >> 4));
        } else if (data.charAt(data.length() - 1) == '=') {
            b1 = decodingTable[data.charAt(data.length() - 4)];
            b2 = decodingTable[data.charAt(data.length() - 3)];
            b3 = decodingTable[data.charAt(data.length() - 2)];

            bytes[bytes.length - 2] = (byte) ((b1 << 2) | (b2 >> 4));
            bytes[bytes.length - 1] = (byte) ((b2 << 4) | (b3 >> 2));
        } else {
            b1 = decodingTable[data.charAt(data.length() - 4)];
            b2 = decodingTable[data.charAt(data.length() - 3)];
            b3 = decodingTable[data.charAt(data.length() - 2)];
            b4 = decodingTable[data.charAt(data.length() - 1)];

            bytes[bytes.length - 3] = (byte) ((b1 << 2) | (b2 >> 4));
            bytes[bytes.length - 2] = (byte) ((b2 << 4) | (b3 >> 2));
            bytes[bytes.length - 1] = (byte) ((b3 << 6) | b4);
        }

        return bytes;
    }

    // --------------------------------------------------------- Private Methods

    /**
     * Discards any characters outside of the base64 alphabet (see page 25 of
     * RFC 2045) "Any characters outside of the base64 alphabet are to be
     * ignored in base64 encoded data."
     *
     * @param data the base64 encoded data
     * @return the data, less non-base64 characters.
     */
    private static byte[] discardNonBase64Bytes(byte[] data) {
        byte temp[] = new byte[data.length];
        int bytesCopied = 0;

        for (int i = 0; i < data.length; i++) {
            if (isValidBase64Byte(data[i])) {
                temp[bytesCopied++] = data[i];
            }
        }

        byte newData[] = new byte[bytesCopied];

        System.arraycopy(temp, 0, newData, 0, bytesCopied);

        return newData;
    }

    /**
     * Discards any characters outside of the base64 alphabet (see page 25 of
     * RFC 2045) "Any characters outside of the base64 alphabet are to be
     * ignored in base64 encoded data."
     *
     * @param data the base64 encoded data
     * @return the data, less non-base64 characters.
     */
    private static String discardNonBase64Chars(String data) {

        StringBuffer sb = new StringBuffer();

        int length = data.length();

        for (int i = 0; i < length; i++) {
            if (isValidBase64Byte((byte) (data.charAt(i)))) {
                sb.append(data.charAt(i));
            }
        }

        return sb.toString();
    }

    /**
     * Checks is the given byte is in base64 alphabet
     *
     * @param b the byte to check
     * @return boolean true if the byte is in base64 alphabet
     */
    private static boolean isValidBase64Byte(byte b) {
        if (b == '=') {
            return true;
        } else if (b < 0 || b >= 128) {
            return false;
        } else if (decodingTable[b] == -1) {
            return false;
        }
        return true;
    }

    ////////////////////////////////////////////
    //AES
    private static byte[] Encrypt(byte[] text, byte[] key) throws Exception {
        SecretKeySpec aesKey = new SecretKeySpec(key, "AES");
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, aesKey);
        return cipher.doFinal(text);
    }

    private static byte[] Decrypt(byte[] text, byte[] key) throws Exception {
        SecretKeySpec aesKey = new SecretKeySpec(key, "AES");

        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, aesKey);
        return cipher.doFinal(text);
    }

    public static String EncodeAES(String password, String key) throws Exception {
        byte[] keybBytes = encrypt2MD5toByte16(key);
        byte[] passwdBytes = password.getBytes();
        byte[] aesBytyes = Encrypt(passwdBytes, keybBytes);
        return new String(encode(aesBytyes));
    }

    public static String DeCodeAES(String password, String key) throws Exception {
        byte[] keybBytes = encrypt2MD5toByte16(key);
        byte[] debase64Bytes = decode(password);
        return new String(Decrypt(debase64Bytes, keybBytes));
    }
}
