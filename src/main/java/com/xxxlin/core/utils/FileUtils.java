package com.xxxlin.core.utils;

import java.io.*;
import java.nio.channels.FileChannel;
import java.util.Locale;

public class FileUtils {

    public static byte[] readFileToByte(File file) {
        FileInputStream ins = null;
        ByteArrayOutputStream bos = null;
        byte[] ret = null;
        try {
            ins = new FileInputStream(file);
            bos = new ByteArrayOutputStream();

            byte[] buffer = new byte[1024 * 1024];
            int r;
            while ((r = ins.read(buffer)) != -1) {
                bos.write(buffer, 0, r);
            }
            ret = bos.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (ins != null) {
                try {
                    ins.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return ret;
    }

    public static String readToString(File file) {
        StringBuffer sb = new StringBuffer();
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return sb.toString();
    }

    public static boolean write(byte[] data, File file) {
        boolean result = true;
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
            fos.write(data);
        } catch (IOException e) {
            e.printStackTrace();
            result = false;
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                    result = false;
                }
            }
        }
        return result;
    }

    /**
     * 文件大小转为常用单位
     *
     * @param size
     * @return
     */
    public static String toFileSize(long size) {
        String sizeString = "";
        if (size < 1024) {
            sizeString = size + "B";
        } else if (size < 1048576) {
            sizeString = String.format(Locale.getDefault(), "%.2fKB", size / 1024F);
        } else if (size < 1073741824) {
            sizeString = String.format(Locale.getDefault(), "%.2fMB", size / 1048576F);
        } else {
            sizeString = String.format(Locale.getDefault(), "%.2fGB", size / 1073741824F);
        }
        return sizeString;
    }






    /**
     * shell 字符串转义
     * shell转义符有三种。 单引号、双引号、反斜杠。
     *
     * @param str
     * @return
     */
    public static String shellEncode0(String str) {
        return str == null ? null : str.replace("\\", "\\\\")
                .replace("'", "\\'")
                .replace("\"", "\\\"");
    }

    /**
     * shell 字符串转义
     * shell转义符有三种。 单引号、双引号、反斜杠。
     *
     * @param str
     * @return
     */
    public static String shellEncode(String str) {
        return str == null ? null : str.replace("\\", "\\\\")
                .replace(" ", "\\ ")
                .replace("'", "\\'")
                .replace("\"", "\\\"");
    }

    /**
     * shell 字符串转义
     * shell转义符有三种。 单引号、双引号、反斜杠。
     *
     * @param str
     * @return
     */
    public static String shellEncode2(String str) {
        return str == null ? null : str.replace("\\", "\\\\")
                .replace(" ", "\\ ")
                .replace("'", "\\'")
                .replace("\"", "\\\"")
                .replace("(", "\\(")
                .replace(")", "\\)")
                .replace("[", "\\[")
                .replace("]", "\\]")
                .replace("{", "\\{")
                .replace("}", "\\}");
    }

    /**
     * shell 字符串转义
     * shell转义符有三种。 单引号、双引号、反斜杠。
     *
     * @param str
     * @return
     */
    public static String shellEncode3(String str) {
        return str == null ? null : str.replace("\\", "\\\\")
                .replace(" ", "\\ ")
                .replace("'", "\\'")
                .replace("\"", "\\\"")
                .replace("(", "\\(")
                .replace(")", "\\)")
                .replace("[", "\\[")
                .replace("]", "\\]")
                .replace("{", "\\{")
                .replace("}", "\\}")
                .replace(":", "\\:")
                .replace("?", "\\?")
                .replace("<", "\\<")
                .replace(">", "\\>")
                .replace("=", "\\=")
                .replace("&", "\\&")
                .replace("*", "\\*");
    }

    /**
     * shell 字符串反转义
     *
     * @param str
     * @return
     */
    public static String shellDecode(String str) {
        return str == null ? null : str.replace("\\\\", "\\")
                .replace("\\ ", " ")
                .replace("\\'", "'")
                .replace("\\\"", "\"");
    }


    /**
     * 快速复制文件
     *
     * @param source
     * @param dest
     * @throws IOException
     * @throws NullPointerException
     */
    public static void copyFileUsingFileChannels(File source, File dest) throws IOException, NullPointerException {
        FileChannel inputChannel = null;
        FileChannel outputChannel = null;
        try {
            inputChannel = new FileInputStream(source).getChannel();
            outputChannel = new FileOutputStream(dest).getChannel();
            outputChannel.transferFrom(inputChannel, 0, inputChannel.size());
        } finally {
            inputChannel.close();
            outputChannel.close();
        }
    }

    public static void copyFileByChannel(File source, File dest) throws
            IOException {
        try (FileChannel sourceChannel = new FileInputStream(source).getChannel();
             FileChannel targetChannel = new FileOutputStream(dest).getChannel()) {
            for (long count = sourceChannel.size(); count > 0; ) {
                long transferred = sourceChannel.transferTo(sourceChannel.position(), count, targetChannel);
                sourceChannel.position(sourceChannel.position() + transferred);
                count -= transferred;
            }
        }
    }

}
