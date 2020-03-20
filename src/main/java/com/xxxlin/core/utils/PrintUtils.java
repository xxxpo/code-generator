package com.xxxlin.core.utils;

/**
 * 无功能说明
 * Date:    2019年04月14日 01:17
 *
 * @author xiaolin
 * @version 0.1
 */
public class PrintUtils {

    private static final boolean isOutPrint = false;
    private static final boolean isErrPrint = false;

    public static void print(String str){
        if(isOutPrint){
            System.out.print(str);
        }
    }

    public static void println(){
        if(isOutPrint){
            System.out.println();
        }
    }

    public static void println(String str){
        if(isOutPrint){
            System.out.println(str);
        }
    }

    public static void errPrint(String str){
        if(isErrPrint){
            System.err.print(str);
        }
    }

    public static void errPrintln(String str){
        if(isErrPrint){
            System.err.println(str);
        }
    }

}
