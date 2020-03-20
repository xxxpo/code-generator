package com.xxxlin.core.utils;

/**
 * 无功能说明
 * Date:    2020年03月06日 8:04 下午
 *
 * @author xiaolin
 * @version 0.1
 */
public final class ANSIUtils {

    /**
     * 是否使用ANSI格式输
     */
    public static final boolean ANSI = true;

    public static final int WHITE = 30;             // 白色
    public static final int WHITE_BACKGROUND = 40;  // 白色背景
    public static final int RED = 31;               // 红色
    public static final int RED_BACKGROUND = 41;    // 红色背景
    public static final int GREEN = 32;             // 绿色
    public static final int GREEN_BACKGROUND = 42;  // 绿色背景
    public static final int YELLOW = 33;            // 黄色
    public static final int YELLOW_BACKGROUND = 43; // 黄色背景
    public static final int BLUE = 34;              // 蓝色
    public static final int BLUE_BACKGROUND = 44;   // 蓝色背景
    public static final int MAGENTA = 35;           // 品红（洋红）
    public static final int MAGENTA_BACKGROUND = 45;// 品红背景
    public static final int CYAN = 36;              // 蓝绿
    public static final int CYAN_BACKGROUND = 46;   // 蓝绿背景
    public static final int BLACK = 37;             // 黑色
    public static final int BLACK_BACKGROUND = 47;  // 黑色背景

    public static final int BOLD = 1;       // 粗体
    public static final int ITATIC = 3;     // 斜体
    public static final int UNDERLINE = 4;  // 下划线
    public static final int REVERSE = 7;    // 反转

    public static String FMT(String txt, int... codes) {
        if(!ANSI){
            return txt;
        }
        StringBuilder sb = new StringBuilder();
        for (int code : codes) {
            sb.append(code).append(";");
        }
        String _code = sb.toString();
        if (_code.endsWith(";")) {
            _code = _code.substring(0, _code.length() - 1);
        }
        return (char) 27 + "[" + _code + "m" + txt + (char) 27 + "[0m";
    }

    /**
     * 打印不换行
     */
    public static void print(String txt, int... codes) {
        System.out.print(FMT(txt, codes));
    }

    /**
     * 打印并换行
     */
    public static void println(String txt, int... codes) {
        System.out.println(FMT(txt, codes));
    }

    /**
     * 默认打印红色文字
     */
    public static void println(String txt) {
        System.out.println(txt);
    }

    public static void println() {
        System.out.println();
    }

    public static P create(){
        return new P();
    }

    public static class P{
        /**
         * 打印不换行
         */
        public P print(String txt, int... codes) {
            print(txt, codes);
            return this;
        }

        /**
         * 打印并换行
         */
        public P println(String txt, int... codes) {
            println(txt, codes);
            return this;
        }

        /**
         * 默认打印红色文字
         */
        public P println(String txt) {
            println(txt);
            return this;
        }

        public P println() {
            println();
            return this;
        }
    }

}
