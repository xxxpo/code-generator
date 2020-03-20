/**
 * Date:		 2018年5月1日下午4:32:05
 * Copyright (c) 2018, xxxlin.com All Rights Reserved.
 * 
 */

package com.xxxlin.core.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Date:     2018年5月1日 下午4:32:05
 * @author   XiaoLin
 * @version  0.1
 */
public class UUID19Util {
	
	final static char[] DIGITS = { '0', '1', '2', '3', '4', '5', '6', '7', '8',  
        '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l',  
        'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y',  
        'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L',  
        'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y',  
        'Z' };
	
	/**
     * 支持的最大进制数
     */
    public static final int MAX_RADIX = DIGITS.length;
  
    /**
     * 支持的最小进制数
     */
    public static final int MIN_RADIX = 2;

	final static Map<Character, Integer> digitMap = new HashMap<Character, Integer>();

	static {
		for (int i = 0; i < DIGITS.length; i++) {
			digitMap.put(DIGITS[i], (int) i);
		}
	}
	
	/** 
     * 将长整型数值转换为指定的进制数（最大支持62进制，字母数字已经用尽） 
     *
     * @param i
     * @param radix
     * @return
     */
    public static String toString(long i, int radix) {
        if (radix < MIN_RADIX || radix > MAX_RADIX)
            radix = 10;
        if (radix == 10)
            return Long.toString(i);
  
        final int size = 65;
        int charPos = 64;

        char[] buf = new char[size];
        boolean negative = (i < 0);

        if (!negative) {
            i = -i;
        }
  
        while (i <= -radix) {
            buf[charPos--] = DIGITS[(int) (-(i % radix))];
            i = i / radix;
        }
        buf[charPos] = DIGITS[(int) (-i)];
  
        if (negative) {
            buf[--charPos] = '-';
        }
        return new String(buf, charPos, (size - charPos));
    }
    
    private static String digits(long val, int digits) {
        long hi = 1L << (digits * 4);
        return toString(hi | (val & (hi - 1)), MAX_RADIX).substring(1);
    }

    /**
     * 以62进制（字母加数字）生成19位UUID，最短的UUID
     * 
     * @return 
     */
    public static String uuid() {
        UUID uuid = UUID.randomUUID();
        StringBuilder sb = new StringBuilder();
        sb.append(digits(uuid.getMostSignificantBits() >> 32, 8));
        sb.append(digits(uuid.getMostSignificantBits() >> 16, 4));
        sb.append(digits(uuid.getMostSignificantBits(), 4));
        sb.append(digits(uuid.getLeastSignificantBits() >> 48, 4));
        sb.append(digits(uuid.getLeastSignificantBits(), 12));
        return sb.toString();
    }

}
