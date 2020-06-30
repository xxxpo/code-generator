/**
 * Date:		 2018年2月24日下午6:55:00
 * Copyright (c) 2018, xxxlin.com All Rights Reserved.
 */

package com.xxxlin.main.util;

import java.io.*;
import java.nio.ByteBuffer;
import java.util.Properties;

/**
 * Date:     2018年2月24日 下午6:55:00
 * @author XiaoLin
 * @version 0.1
 */
public class FileUtil {

    /**
     * 读取文件内容
     *
     * @author XiaoLin
     * @param file
     * @return
     */
    public static byte[] read(File file) {
        int size = (int) file.length();
        ByteBuffer byteBuffer = ByteBuffer.allocate(size);
        InputStream ins = null;
        byte[] data = null;
        try {
            ins = new FileInputStream(file);
            byte[] buffer = new byte[1];
            int r;
            while ((r = ins.read(buffer)) != -1) {
                byteBuffer.put(buffer, 0, r);
            }
            data = byteBuffer.array();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (ins != null) {
                try {
                    ins.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return data;
    }

    /**
     * 加载配置
     * @param resourcesFilePath
     * @return
     * @throws Exception
     */
    public static Properties loadPropertiesFile(String resourcesFilePath) throws Exception {
        System.out.println(resourcesFilePath);
        Properties properties = new Properties();
        InputStream in = null;
        try {
            in = FileUtil.class.getClassLoader().getResourceAsStream(resourcesFilePath);
            BufferedReader bf = new BufferedReader(new InputStreamReader(in, "UTF-8"));
            properties.load(bf);
            in.close();
            return properties;
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }
    }


}
