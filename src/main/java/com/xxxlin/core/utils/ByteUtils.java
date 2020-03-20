package com.xxxlin.core.utils;

import java.nio.charset.Charset;
import java.util.Arrays;

public class ByteUtils {

    /*
     * 使用慢比较比较两个byte数组(每次调用函数使用的时间相同)
     */
    public static boolean equals(byte[] a, byte[] b) {
        int diff = a.length ^ b.length;
        for (int i = 0; i < a.length && i < b.length; i++) {
            diff |= a[i] ^ b[i];
        }
        return diff == 0;
    }

    public static byte[] longToBytes(long values) {
        byte[] buffer = new byte[8];
        for (int i = 0; i < 8; i++) {
            int offset = 64 - (i + 1) * 8;
            buffer[i] = (byte) ((values >> offset) & 0xff);
        }
        return buffer;
    }

    public static long bytesToLong(byte[] buffer) {
        long values = 0;
        for (int i = 0; i < 8; i++) {
            values <<= 8;
            values |= (buffer[i] & 0xff);
        }
        return values;
    }

    public static byte[] getBytes(short data) {
        byte[] bytes = new byte[2];
        bytes[0] = (byte) (data & 0xff);
        bytes[1] = (byte) ((data & 0xff00) >> 8);
        return bytes;
    }

    public static byte[] getBytes(char data) {
        byte[] bytes = new byte[2];
        bytes[0] = (byte) (data);
        bytes[1] = (byte) (data >> 8);
        return bytes;
    }

    public static byte[] getBytes(int data) {
        byte[] bytes = new byte[4];
        bytes[0] = (byte) (data & 0xff);
        bytes[1] = (byte) ((data & 0xff00) >> 8);
        bytes[2] = (byte) ((data & 0xff0000) >> 16);
        bytes[3] = (byte) ((data & 0xff000000) >> 24);
        return bytes;
    }

    public static byte[] getBytes(long data) {
        byte[] bytes = new byte[8];
        bytes[0] = (byte) (data & 0xff);
        bytes[1] = (byte) ((data >> 8) & 0xff);
        bytes[2] = (byte) ((data >> 16) & 0xff);
        bytes[3] = (byte) ((data >> 24) & 0xff);
        bytes[4] = (byte) ((data >> 32) & 0xff);
        bytes[5] = (byte) ((data >> 40) & 0xff);
        bytes[6] = (byte) ((data >> 48) & 0xff);
        bytes[7] = (byte) ((data >> 56) & 0xff);
        return bytes;

    }

    public static byte[] getBytes(float data) {
        int intBits = Float.floatToIntBits(data);
        return getBytes(intBits);
    }

    public static byte[] getBytes(double data) {
        long intBits = Double.doubleToLongBits(data);
        return getBytes(intBits);
    }

    public static byte[] getBytes(String data, String charsetName) {
        Charset charset = Charset.forName(charsetName);
        return data.getBytes(charset);
    }

    public static byte[] getBytes(String data) {
        return getBytes(data, "utf-8");
    }

    public static short getShort(byte[] bytes) {
        return (short) ((0xff & bytes[0]) | (0xff00 & (bytes[1] << 8)));
    }

    public static short getShort(byte[] bytes, int off) {
        return (short) ((0xff & bytes[off]) | (0xff00 & (bytes[off + 1] << 8)));
    }

    public static char getChar(byte[] bytes) {
        return (char) ((0xff & bytes[0]) | (0xff00 & (bytes[1] << 8)));
    }

    public static char getChar(byte[] bytes, int offset) {
        return (char) ((0xff & bytes[offset]) | (0xff00 & (bytes[offset + 1] << 8)));
    }

    public static int getInt(byte[] bytes) {
        return (0xff & bytes[0]) | (0xff00 & (bytes[1] << 8)) | (0xff0000 & (bytes[2] << 16))
                | (0xff000000 & (bytes[3] << 24));
    }

    public static int getInt(byte[] bytes, int i) {
        return (0xff & bytes[i]) | (0xff00 & (bytes[i + 1] << 8)) | (0xff0000 & (bytes[i + 2] << 16))
                | (0xff000000 & (bytes[i + 3] << 24));
    }

    public static long getLong(byte[] bytes) {
        return (0xffL & (long) bytes[0])
                | (0xff00L & ((long) bytes[1] << 8))
                | (0xff0000L & ((long) bytes[2] << 16))
                | (0xff000000L & ((long) bytes[3] << 24))
                | (0xff00000000L & ((long) bytes[4] << 32))
                | (0xff0000000000L & ((long) bytes[5] << 40))
                | (0xff000000000000L & ((long) bytes[6] << 48))
                | (0xff00000000000000L & ((long) bytes[7] << 56));
    }

    public static float getFloat(byte[] bytes) {
        return Float.intBitsToFloat(getInt(bytes));
    }

    public static double getDouble(byte[] bytes) {
        long l = getLong(bytes);
        return Double.longBitsToDouble(l);
    }

    public static String getString(byte[] bytes, String charsetName) {
        return new String(bytes, Charset.forName(charsetName));
    }

    public static String getString(byte[] bytes) {
        return getString(bytes, "utf-8");
    }

    /**
     * 按位反转
     *
     * @param y
     * @return
     */
    public static int reverseBits(int y) {
        int INT_SIZE = Integer.SIZE;
        int j, low, high, A, B;
        for (int i = 0; i < INT_SIZE / 2; i++) {
            j = INT_SIZE - 1 - i;

            low = (y >> i) & 1;
            high = (y >> j) & 1;

            A = 1 << i;
            B = 1 << j;

            if ((high ^ low) == 1) {
                y = y ^ (A | B);
            }
        }
        return y;
    }

    public static byte[] copy(byte[] buffer, int offset, int len) {
        return Arrays.copyOfRange(buffer, offset, offset + len);
    }

    public static boolean eq(byte[] src, int srcOffset, byte[] dsc, int dscOffset, int len) {
        for (int i = 0; i < len; i++) {
            if (src[srcOffset + i] != dsc[dscOffset + i]) {
                return false;
            }
        }
        return true;
    }

}
