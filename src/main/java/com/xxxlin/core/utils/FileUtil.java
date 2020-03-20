/**
 * Date:		 2018年2月24日下午6:55:00
 * Copyright (c) 2018, xxxlin.com All Rights Reserved.
 *
 */

package com.xxxlin.core.utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.ByteBuffer;

/**
 * Date:     2018年2月24日 下午6:55:00
 * @author   XiaoLin
 * @version  0.1
 */
public class FileUtil {

	/**
	 * 读取文件内容
	 *
	 * @author XiaoLin
	 * @param file
	 * @return
	 */
	public static byte[] read(MultipartFile file){
		int size = (int) file.getSize();
		ByteBuffer byteBuffer = ByteBuffer.allocate(size);
		InputStream ins = null;
		byte[] data = null;
        try {
			ins = file.getInputStream();
			byte[] buffer = new byte[1024];
			int r;
			while((r=ins.read(buffer)) != -1){
				byteBuffer.put(buffer, 0, r);
			}
			data = byteBuffer.array();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(ins != null){
				try {
					ins.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
        return data;
	}

	public static byte[] read(InputStream ins){
		byte[] data = null;
		try {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			byte[] buffer = new byte[1024*128];
			int r;
			while((r=ins.read(buffer)) != -1){
				bos.write(buffer, 0, r);
			}
			data = bos.toByteArray();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(ins != null){
				try {
					ins.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return data;
	}

	public static boolean write(byte[] data, File file){
		boolean result = true;
		FileOutputStream fos = null;
        try {
			fos = new FileOutputStream(file);
			fos.write(data);
		} catch (IOException e) {
			e.printStackTrace();
			result = false;
		}finally{
			if(fos != null){
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

	public static String toFileSize(long size) {
		String sizeString = "";
		if (size < 1024) {
			sizeString = size + "B";
		} else if (size < 1048576) {
			sizeString = String.format("%.2fKB", size / 1024F);
		} else if (size < 1073741824) {
			sizeString = String.format("%.2fMB", size / 1048576F);
		} else {
			sizeString = String.format("%.2fGB", size / 1073741824F);
		}
		return sizeString;
	}


}
