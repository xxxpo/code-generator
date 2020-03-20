/**
 * Date:		 2018年3月3日下午6:28:25
 * Copyright (c) 2018, xxxlin.com All Rights Reserved.
 */

package com.xxxlin.core.utils;

import com.sun.image.codec.jpeg.ImageFormatException;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.color.ColorSpace;
import java.awt.geom.AffineTransform;
import java.awt.image.*;
import java.io.*;
import java.util.List;

/**
 * 图片处理工具类 功能：缩放图像、切割图像、图像类型转换、彩色转黑白、文字水印、图片水印等
 * <p>
 * Date: 2018年3月3日 下午6:28:25
 *
 * @author XiaoLin
 * @version 0.1
 */
public class ImageUtils {

    /**
     * 几种常见的图片格式
     */
    public static String IMAGE_TYPE_GIF = "gif";// 图形交换格式
    public static String IMAGE_TYPE_JPG = "jpg";// 联合照片专家组
    public static String IMAGE_TYPE_JPEG = "jpeg";// 联合照片专家组
    public static String IMAGE_TYPE_BMP = "bmp";// 英文Bitmap（位图）的简写，它是Windows操作系统中的标准图像文件格式
    public static String IMAGE_TYPE_PNG = "png";// 可移植网络图形
    public static String IMAGE_TYPE_PSD = "psd";// Photoshop的专用格式Photoshop

    /**
     * 读取byte数据, jpeg格式, (无失真)
     *
     * @param tag
     * @return
     * @author XiaoLin
     */
    private final static byte[] getByte(BufferedImage tag) throws IOException {
        ByteArrayOutputStream bous = new ByteArrayOutputStream();
        JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(bous);
        JPEGEncodeParam jParam = encoder.getDefaultJPEGEncodeParam(tag);
        jParam.setQuality(1f, false);
        encoder.encode(tag);
        return bous.toByteArray();
    }

    /**
     * 剪切图像为正方形  (无失真缩放)
     *
     * @param data    图像二进制数据
     * @param maxSize 最大边长
     * @return
     * @author XiaoLin
     */
    public final static byte[] cutSquare(byte[] data, int maxSize) throws IOException {
        InputStream ins = new ByteArrayInputStream(data);
        BufferedImage img = null;
        try {
            img = ImageIO.read(ins);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        int bitWidth = img.getWidth();
        int bitHeight = img.getHeight();

        int figurerSize = bitHeight > bitWidth ? bitWidth : bitHeight;
        if (figurerSize > maxSize) {
            // 图像边长
            figurerSize = maxSize;
        }

        float scaleX = (float) figurerSize / bitWidth;
        float scaleY = (float) figurerSize / bitHeight;
        float scale = scaleX;
        if (scaleY > scaleX) {
            scale = scaleY;
        }
        int offsetX = (int) (figurerSize / 2f - bitWidth * scale / 2f);
        int offsetY = (int) (figurerSize / 2f - bitHeight * scale / 2f);

        BufferedImage tag = new BufferedImage(figurerSize, figurerSize, BufferedImage.TYPE_INT_RGB);
        Graphics g = tag.getGraphics();
        // 设置白色背景, 防止PNG出现黑色背景
        g.setColor(Color.white);
        // 填充颜色
        g.fillRect(0, 0, figurerSize, figurerSize);
        g.drawImage(img.getScaledInstance((int) (bitWidth * scale), (int) (bitHeight * scale), Image.SCALE_SMOOTH),
                offsetX, offsetY, null); // 绘制切割后的图
        g.dispose();
        return getByte(tag);
    }

    /**
     * 剪切图像为正方形
     *
     * @param data    图像二进制数据
     * @param maxSize 最大边长
     * @return
     * @author XiaoLin
     */
    public final static byte[] scale(byte[] data, int maxSize) {
        InputStream ins = new ByteArrayInputStream(data);
        BufferedImage img = null;
        try {
            img = ImageIO.read(ins);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        int bitWidth = img.getWidth();
        int bitHeight = img.getHeight();

        int figurerSize = bitHeight > bitWidth ? bitWidth : bitHeight;
        if (figurerSize > maxSize) {
            // 图像边长
            figurerSize = maxSize;
        }

        float scaleX = (float) figurerSize / bitWidth;
        float scaleY = (float) figurerSize / bitHeight;
        float scale = scaleX;
        if (scaleY > scaleX) {
            scale = scaleY;
        }
        int offsetX = (int) (figurerSize / 2f - bitWidth * scale / 2f);
        int offsetY = (int) (figurerSize / 2f - bitHeight * scale / 2f);

        BufferedImage tag = new BufferedImage(figurerSize, figurerSize, BufferedImage.TYPE_INT_RGB);
        Graphics g = tag.getGraphics();
        // 设置白色背景, 防止PNG出现黑色背景
        g.setColor(Color.white);
        // 填充颜色
        g.fillRect(0, 0, figurerSize, figurerSize);
        g.drawImage(img.getScaledInstance((int) (bitWidth * scale), (int) (bitHeight * scale), Image.SCALE_SMOOTH),
                offsetX, offsetY, null); // 绘制切割后的图
        g.dispose();

        ByteArrayOutputStream bous = new ByteArrayOutputStream();


        JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(bous);
        JPEGEncodeParam jParam = encoder.getDefaultJPEGEncodeParam(tag);
        jParam.setQuality(1f, false);
        try {
            encoder.encode(tag);
        } catch (ImageFormatException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return bous.toByteArray();
    }

    /**
     * 缩放图像（按比例缩放）
     *
     * @param srcImageFile 源图像文件地址
     * @param result       缩放后的图像地址
     * @param scale        缩放比例
     * @param flag         缩放选择:true 放大; false 缩小;
     */
    public final static void scale(String srcImageFile, String result, int scale, boolean flag) {
        try {
            BufferedImage src = ImageIO.read(new File(srcImageFile)); // 读入文件
            int width = src.getWidth(); // 得到源图宽

            int height = src.getHeight(); // 得到源图长

            if (flag) {// 放大
                width = width * scale;
                height = height * scale;
            } else {// 缩小
                width = width / scale;
                height = height / scale;
            }
            Image image = src.getScaledInstance(width, height, Image.SCALE_DEFAULT);
            BufferedImage tag = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics g = tag.getGraphics();
            g.drawImage(image, 0, 0, null); // 绘制缩小后的图

            g.dispose();
            ImageIO.write(tag, "JPEG", new File(result));// 输出到文件流
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 缩放图像（按高度和宽度缩放）
     *
     * @param srcImageFile 源图像文件地址
     * @param result       缩放后的图像地址
     * @param height       缩放后的高度
     * @param width        缩放后的宽度
     * @param bb           比例不对时是否需要补白：true为补白; false为不补白;
     */
    public final static void scale2(String srcImageFile, String result, int width, int height, boolean bb) {
        try {
            double ratio = 0.0; // 缩放比例
            File f = new File(srcImageFile);
            BufferedImage bi = ImageIO.read(f);
            Image itemp = bi.getScaledInstance(width, height, bi.SCALE_SMOOTH);
            // 计算比例
            if ((bi.getHeight() > height) || (bi.getWidth() > width)) {
                if (bi.getHeight() > bi.getWidth()) {
                    ratio = (new Integer(height)).doubleValue() / bi.getHeight();
                } else {
                    ratio = (new Integer(width)).doubleValue() / bi.getWidth();
                }
                AffineTransformOp op = new AffineTransformOp(AffineTransform.getScaleInstance(ratio, ratio), null);
                itemp = op.filter(bi, null);
            }
            if (bb) {// 补白
                BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
                Graphics2D g = image.createGraphics();
                g.setColor(Color.white);
                g.fillRect(0, 0, width, height);
                if (width == itemp.getWidth(null)) {
                    g.drawImage(itemp, 0, (height - itemp.getHeight(null)) / 2,
                            itemp.getWidth(null), itemp.getHeight(null),
                            Color.white, null);
                } else {
                    g.drawImage(itemp, (width - itemp.getWidth(null)) / 2, 0,
                            itemp.getWidth(null), itemp.getHeight(null),
                            Color.white, null);
                }
                g.dispose();
                itemp = image;
            }
            ImageIO.write((BufferedImage) itemp, "JPEG", new File(result));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 缩放图像（按高度和宽度缩放）
     *
     * @param srcImageFile 源图像文件地址
     * @param result       缩放后的图像地址
     * @param height       缩放后的高度
     * @param width        缩放后的宽度
     * @param bb           比例不对时是否需要补白：true为补白; false为不补白;
     */
    public final static void scale3(String srcImageFile, String result, int width, int height, boolean bb) {
        try {
            BufferedImage src = ImageIO.read(new File(srcImageFile)); // 读入文件
            // int _width = src.getWidth(); // 得到源图宽
            // int _height = src.getHeight(); // 得到源图长
            Image image = src.getScaledInstance(width, height,
                    Image.SCALE_DEFAULT);
            BufferedImage tag = new BufferedImage(width, height,
                    BufferedImage.TYPE_INT_RGB);
            Graphics g = tag.getGraphics();
            g.drawImage(image, 0, 0, null); // 绘制缩小后的图

            g.dispose();
            ImageIO.write(tag, "JPEG", new File(result));// 输出到文件流
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 图像切割(按指定起点坐标和宽高切割)
     *
     * @param srcImageFile 源图像地址
     * @param result       切片后的图像地址
     * @param x            目标切片起点坐标X
     * @param y            目标切片起点坐标Y
     * @param width        目标切片宽度
     * @param height       目标切片高度
     */
    public final static void cut(String srcImageFile, String result, int x, int y, int width, int height) {
        try {
            // 读取源图像
            BufferedImage bi = ImageIO.read(new File(srcImageFile));
            int srcWidth = bi.getHeight(); // 源图宽度
            int srcHeight = bi.getWidth(); // 源图高度
            if (srcWidth > 0 && srcHeight > 0) {
                Image image = bi.getScaledInstance(srcWidth, srcHeight, Image.SCALE_DEFAULT);
                // 四个参数分别为图像起点坐标和宽高
                // 即: CropImageFilter(int x,int y,int width,int height)
                ImageFilter cropFilter = new CropImageFilter(x, y, width, height);
                Image img = Toolkit.getDefaultToolkit()
                        .createImage(new FilteredImageSource(image.getSource(), cropFilter));
                BufferedImage tag = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
                Graphics g = tag.getGraphics();
                g.drawImage(img, 0, 0, width, height, null); // 绘制切割后的图
                g.dispose();
                // 输出为文件
                ImageIO.write(tag, "JPEG", new File(result));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 图像切割（指定切片的行数和列数）
     *
     * @param srcImageFile 源图像地址
     * @param descDir      切片目标文件夹
     * @param rows         目标切片行数。默认2，必须是范围 [1, 20] 之内
     * @param cols         目标切片列数。默认2，必须是范围 [1, 20] 之内
     */
    public final static void cut2(String srcImageFile, String descDir, int rows, int cols) {
        try {
            if (rows <= 0 || rows > 20)
                rows = 2; // 切片行数
            if (cols <= 0 || cols > 20)
                cols = 2; // 切片列数
            // 读取源图像
            BufferedImage bi = ImageIO.read(new File(srcImageFile));
            int srcWidth = bi.getHeight(); // 源图宽度
            int srcHeight = bi.getWidth(); // 源图高度
            if (srcWidth > 0 && srcHeight > 0) {
                Image img;
                ImageFilter cropFilter;
                Image image = bi.getScaledInstance(srcWidth, srcHeight,
                        Image.SCALE_DEFAULT);
                int destWidth = srcWidth; // 每张切片的宽度

                int destHeight = srcHeight; // 每张切片的高度

                // 计算切片的宽度和高度
                if (srcWidth % cols == 0) {
                    destWidth = srcWidth / cols;
                } else {
                    destWidth = (int) Math.floor(srcWidth / cols) + 1;
                }
                if (srcHeight % rows == 0) {
                    destHeight = srcHeight / rows;
                } else {
                    destHeight = (int) Math.floor(srcWidth / rows) + 1;
                }
                // 循环建立切片
                // 改进的想法:是否可用多线程加快切割速度
                for (int i = 0; i < rows; i++) {
                    for (int j = 0; j < cols; j++) {
                        // 四个参数分别为图像起点坐标和宽高
                        // 即: CropImageFilter(int x,int y,int width,int height)
                        cropFilter = new CropImageFilter(j * destWidth, i
                                * destHeight, destWidth, destHeight);
                        img = Toolkit.getDefaultToolkit()
                                .createImage(new FilteredImageSource(image.getSource(), cropFilter));
                        BufferedImage tag = new BufferedImage(destWidth, destHeight, BufferedImage.TYPE_INT_RGB);
                        Graphics g = tag.getGraphics();
                        g.drawImage(img, 0, 0, null); // 绘制缩小后的图
                        g.dispose();
                        // 输出为文件
                        ImageIO.write(tag, "JPEG", new File(descDir + "_r" + i + "_c" + j + ".jpg"));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 图像切割（指定切片的宽度和高度）
     *
     * @param srcImageFile 源图像地址
     * @param descDir      切片目标文件夹
     * @param destWidth    目标切片宽度。默认200
     * @param destHeight   目标切片高度。默认150
     */
    public final static void cut3(String srcImageFile, String descDir, int destWidth, int destHeight) {
        try {
            if (destWidth <= 0)
                destWidth = 200; // 切片宽度
            if (destHeight <= 0)
                destHeight = 150; // 切片高度
            // 读取源图像

            BufferedImage bi = ImageIO.read(new File(srcImageFile));
            int srcWidth = bi.getHeight(); // 源图宽度
            int srcHeight = bi.getWidth(); // 源图高度
            if (srcWidth > destWidth && srcHeight > destHeight) {
                Image img;
                ImageFilter cropFilter;
                Image image = bi.getScaledInstance(srcWidth, srcHeight,
                        Image.SCALE_DEFAULT);
                int cols = 0; // 切片横向数量
                int rows = 0; // 切片纵向数量
                // 计算切片的横向和纵向数量
                if (srcWidth % destWidth == 0) {
                    cols = srcWidth / destWidth;
                } else {
                    cols = (int) Math.floor(srcWidth / destWidth) + 1;
                }
                if (srcHeight % destHeight == 0) {
                    rows = srcHeight / destHeight;
                } else {
                    rows = (int) Math.floor(srcHeight / destHeight) + 1;
                }
                // 循环建立切片
                // 改进的想法:是否可用多线程加快切割速度
                for (int i = 0; i < rows; i++) {
                    for (int j = 0; j < cols; j++) {
                        // 四个参数分别为图像起点坐标和宽高
                        // 即: CropImageFilter(int x,int y,int width,int height)
                        cropFilter = new CropImageFilter(j * destWidth, i
                                * destHeight, destWidth, destHeight);
                        img = Toolkit.getDefaultToolkit().createImage(
                                new FilteredImageSource(image.getSource(),
                                        cropFilter));
                        BufferedImage tag = new BufferedImage(destWidth,
                                destHeight, BufferedImage.TYPE_INT_RGB);
                        Graphics g = tag.getGraphics();
                        g.drawImage(img, 0, 0, null); // 绘制缩小后的图

                        g.dispose();
                        // 输出为文件

                        ImageIO.write(tag, "JPEG", new File(descDir + "_r" + i
                                + "_c" + j + ".jpg"));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 图像类型转换：GIF->JPG、GIF->PNG、PNG->JPG、PNG->GIF(X)、BMP->PNG
     *
     * @param srcImageFile  源图像地址
     * @param formatName    包含格式非正式名称的 String：如JPG、JPEG、GIF等
     * @param destImageFile 目标图像地址
     */
    public final static void convert(String srcImageFile, String formatName, String destImageFile) {
        try {
            File f = new File(srcImageFile);
            f.canRead();
            f.canWrite();
            BufferedImage src = ImageIO.read(f);
            ImageIO.write(src, formatName, new File(destImageFile));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 彩色转为黑白
     *
     * @param srcImageFile  源图像地址
     * @param destImageFile 目标图像地址
     */
    public final static void gray(String srcImageFile, String destImageFile) {
        try {
            BufferedImage src = ImageIO.read(new File(srcImageFile));
            ColorSpace cs = ColorSpace.getInstance(ColorSpace.CS_GRAY);
            ColorConvertOp op = new ColorConvertOp(cs, null);
            src = op.filter(src, null);
            ImageIO.write(src, "JPEG", new File(destImageFile));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 给图片添加文字水印
     *
     * @param pressText     水印文字
     * @param srcImageFile  源图像地址
     * @param destImageFile 目标图像地址
     * @param fontName      水印的字体名称
     * @param fontStyle     水印的字体样式
     * @param color         水印的字体颜色
     * @param fontSize      水印的字体大小
     * @param x             修正值
     * @param y             修正值
     * @param alpha         透明度：alpha 必须是范围 [0.0, 1.0] 之内（包含边界值）的一个浮点数字
     */
    public final static void pressText(String pressText, String srcImageFile,
                                       String destImageFile, String fontName, int fontStyle, Color color,
                                       int fontSize, int x, int y, float alpha) {
        try {
            File img = new File(srcImageFile);
            Image src = ImageIO.read(img);
            int width = src.getWidth(null);
            int height = src.getHeight(null);
            BufferedImage image = new BufferedImage(width, height,
                    BufferedImage.TYPE_INT_RGB);
            Graphics2D g = image.createGraphics();
            g.drawImage(src, 0, 0, width, height, null);
            g.setColor(color);
            g.setFont(new Font(fontName, fontStyle, fontSize));
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,
                    alpha));
            // 在指定坐标绘制水印文字

            g.drawString(pressText, (width - (getLength(pressText) * fontSize))
                    / 2 + x, (height - fontSize) / 2 + y);
            g.dispose();
            ImageIO.write((BufferedImage) image, "JPEG",
                    new File(destImageFile));// 输出到文件流
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 给图片添加文字水印
     *
     * @param pressText     水印文字
     * @param srcImageFile  源图像地址
     * @param destImageFile 目标图像地址
     * @param fontName      字体名称
     * @param fontStyle     字体样式
     * @param color         字体颜色
     * @param fontSize      字体大小
     * @param x             修正值
     * @param y             修正值
     * @param alpha         透明度：alpha 必须是范围 [0.0, 1.0] 之内（包含边界值）的一个浮点数字
     */
    public final static void pressText2(String pressText, String srcImageFile,
                                        String destImageFile, String fontName, int fontStyle, Color color,
                                        int fontSize, int x, int y, float alpha) {
        try {
            File img = new File(srcImageFile);
            Image src = ImageIO.read(img);
            int width = src.getWidth(null);
            int height = src.getHeight(null);
            BufferedImage image = new BufferedImage(width, height,
                    BufferedImage.TYPE_INT_RGB);
            Graphics2D g = image.createGraphics();
            g.drawImage(src, 0, 0, width, height, null);
            g.setColor(color);
            g.setFont(new Font(fontName, fontStyle, fontSize));
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,
                    alpha));
            // 在指定坐标绘制水印文字

            g.drawString(pressText, (width - (getLength(pressText) * fontSize))
                    / 2 + x, (height - fontSize) / 2 + y);
            g.dispose();
            ImageIO.write((BufferedImage) image, "JPEG",
                    new File(destImageFile));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 给图片添加图片水印
     *
     * @param pressImg      水印图片
     * @param srcImageFile  源图像地址
     * @param destImageFile 目标图像地址
     * @param x             修正值。 默认在中间
     * @param y             修正值。 默认在中间
     * @param alpha         透明度：alpha 必须是范围 [0.0, 1.0] 之内（包含边界值）的一个浮点数字
     */
    public final static void pressImage(String pressImg, String srcImageFile,
                                        String destImageFile, int x, int y, float alpha) {
        try {
            File img = new File(srcImageFile);
            Image src = ImageIO.read(img);
            int wideth = src.getWidth(null);
            int height = src.getHeight(null);
            BufferedImage image = new BufferedImage(wideth, height, BufferedImage.TYPE_INT_RGB);
            Graphics2D g = image.createGraphics();
            g.drawImage(src, 0, 0, wideth, height, null);
            // 水印文件
            Image src_biao = ImageIO.read(new File(pressImg));
            int wideth_biao = src_biao.getWidth(null);
            int height_biao = src_biao.getHeight(null);
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha));
            g.drawImage(src_biao, (wideth - wideth_biao) / 2,
                    (height - height_biao) / 2, wideth_biao, height_biao, null);
            // 水印文件结束
            g.dispose();
            ImageIO.write((BufferedImage) image, "JPEG", new File(destImageFile));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 创建图片缩略图(等比缩放 无失真缩放)
     *
     * @param src    源图片文件完整路径
     * @param dist   目标图片文件完整路径
     * @param width  缩放的宽度
     * @param height 缩放的高度
     * @param flag   true 	按照实际长宽输出 如果 false 按照比例进行无失真压缩
     */
    public static boolean createThumbnail(String src, String dist, float width,
                                          float height, boolean flag) {
        boolean flag1 = false;
        try {
            File srcfile = new File(src);
            if (!srcfile.exists()) {
                System.out.println("文件不存在");
                return flag1;
            }
            BufferedImage image = ImageIO.read(srcfile);

            // 获得缩放的比例

            double ratio = 1.0;
            // 判断如果高、宽都不大于设定值，则不处理
            if (image.getHeight() > height || image.getWidth() > width) {
                if (image.getHeight() > image.getWidth()) {
                    ratio = height / image.getHeight();
                } else {
                    ratio = width / image.getWidth();
                }
            }
            int newWidth = flag ? (int) width : (int) (image.getWidth() * ratio);
            int newHeight = flag ? (int) height : (int) (image.getHeight() * ratio);
            BufferedImage bfImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);
            flag1 = bfImage
                    .getGraphics()
                    .drawImage(image.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH), 0, 0, null);

            FileOutputStream os = new FileOutputStream(dist);
            JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(os);
            JPEGEncodeParam jParam = encoder.getDefaultJPEGEncodeParam(bfImage);
            jParam.setQuality(1f, false);
            encoder.encode(bfImage);
            os.close();
            flag1 = true;
        } catch (Exception e) {
            flag1 = false;
        }
        return flag1;
    }

    /**
     * 获取图片宽度
     *
     * @param file 图片文件
     * @return 宽度
     */
    public static int getImgWidth(File file) {
        InputStream is = null;
        BufferedImage src = null;
        int ret = -1;
        try {
            is = new FileInputStream(file);
            src = ImageIO.read(is);
            ret = src.getWidth(null); // 得到源图宽
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }

    /**
     * 获取图片高度
     *
     * @param file 图片文件
     * @return 高度
     */
    public static int getImgHeight(File file) {
        InputStream is = null;
        BufferedImage src = null;
        int ret = -1;
        try {
            is = new FileInputStream(file);
            src = ImageIO.read(is);
            ret = src.getHeight(null); // 得到源图高
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }

    /**
     * 计算text的长度（一个中文算两个字符）
     *
     * @param text
     * @return
     */
    private final static int getLength(String text) {
        int length = 0;
        for (int i = 0; i < text.length(); i++) {
            if (new String(text.charAt(i) + "").getBytes().length > 1) {
                length += 2;
            } else {
                length += 1;
            }
        }
        return length / 2;
    }


    //================================

    /**
     * 横向拼接图片（两张）
     *
     * @param firstSrcImagePath  第一张图片的路径
     * @param secondSrcImagePath 第二张图片的路径
     * @param imageFormat        拼接生成图片的格式
     * @param toPath             拼接生成图片的路径
     */
    public void joinImagesHorizontal(String firstSrcImagePath, String secondSrcImagePath, String imageFormat, String toPath) {
        try {
            //读取第一张图片
            File fileOne = new File(firstSrcImagePath);
            BufferedImage imageOne = ImageIO.read(fileOne);
            int width = imageOne.getWidth();//图片宽度
            int height = imageOne.getHeight();//图片高度
            //从图片中读取RGB
            int[] imageArrayOne = new int[width * height];
            imageArrayOne = imageOne.getRGB(0, 0, width, height, imageArrayOne, 0, width);

            //对第二张图片做相同的处理
            File fileTwo = new File(secondSrcImagePath);
            BufferedImage imageTwo = ImageIO.read(fileTwo);
            int width2 = imageTwo.getWidth();
            int height2 = imageTwo.getHeight();
            int[] ImageArrayTwo = new int[width2 * height2];
            ImageArrayTwo = imageTwo.getRGB(0, 0, width, height, ImageArrayTwo, 0, width);
            //ImageArrayTwo  =  imageTwo.getRGB(0,0,width2,height2,ImageArrayTwo,0,width2);

            //生成新图片
            //int height3 = (height>height2 || height==height2)?height:height2;
            BufferedImage imageNew = new BufferedImage(width * 2, height, BufferedImage.TYPE_INT_RGB);
            //BufferedImage  imageNew  =  new  BufferedImage(width+width2,height3,BufferedImage.TYPE_INT_RGB);
            imageNew.setRGB(0, 0, width, height, imageArrayOne, 0, width);//设置左半部分的RGB
            imageNew.setRGB(width, 0, width, height, ImageArrayTwo, 0, width);//设置右半部分的RGB
            //imageNew.setRGB(width,0,width2,height2,ImageArrayTwo,0,width2);//设置右半部分的RGB

            File outFile = new File(toPath);
            ImageIO.write(imageNew, imageFormat, outFile);//写图片
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 横向拼接一组（多张）图像
     *
     * @param pics    将要拼接的图像
     * @param type    图像写入格式
     * @param dst_pic 图像写入路径
     * @return
     */
    public boolean joinImageListHorizontal(String[] pics, String type, String dst_pic) {
        try {
            int len = pics.length;
            if (len < 1) {
                System.out.println("pics len < 1");
                return false;
            }
            File[] src = new File[len];
            BufferedImage[] images = new BufferedImage[len];
            int[][] imageArrays = new int[len][];
            for (int i = 0; i < len; i++) {
                src[i] = new File(pics[i]);
                images[i] = ImageIO.read(src[i]);
                int width = images[i].getWidth();
                int height = images[i].getHeight();
                imageArrays[i] = new int[width * height];// 从图片中读取RGB
                imageArrays[i] = images[i].getRGB(0, 0, width, height, imageArrays[i], 0, width);
            }

            int dst_width = 0;
            int dst_height = images[0].getHeight();
            for (int i = 0; i < images.length; i++) {
                dst_height = dst_height > images[i].getHeight() ? dst_height : images[i].getHeight();
                dst_width += images[i].getWidth();
            }
            //System.out.println(dst_width);
            //System.out.println(dst_height);
            if (dst_height < 1) {
                System.out.println("dst_height < 1");
                return false;
            }
            /*
             * 生成新图片
             */
            BufferedImage ImageNew = new BufferedImage(dst_width, dst_height, BufferedImage.TYPE_INT_RGB);
            int width_i = 0;
            for (int i = 0; i < images.length; i++) {
                ImageNew.setRGB(width_i, 0, images[i].getWidth(), dst_height, imageArrays[i], 0, images[i].getWidth());
                width_i += images[i].getWidth();
            }
            File outFile = new File(dst_pic);
            ImageIO.write(ImageNew, type, outFile);// 写图片
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 纵向拼接图片（两张）
     *
     * @param firstSrcImagePath  读取的第一张图片
     * @param secondSrcImagePath 读取的第二张图片
     * @param imageFormat        图片写入格式
     * @param toPath             图片写入路径
     */
    public void joinImagesVertical(String firstSrcImagePath, String secondSrcImagePath, String imageFormat, String toPath) {
        try {
            //读取第一张图片
            File fileOne = new File(firstSrcImagePath);
            BufferedImage imageOne = ImageIO.read(fileOne);
            int width = imageOne.getWidth();//图片宽度
            int height = imageOne.getHeight();//图片高度
            //从图片中读取RGB
            int[] imageArrayOne = new int[width * height];
            imageArrayOne = imageOne.getRGB(0, 0, width, height, imageArrayOne, 0, width);

            //对第二张图片做相同的处理
            File fileTwo = new File(secondSrcImagePath);
            BufferedImage imageTwo = ImageIO.read(fileTwo);
            int width2 = imageTwo.getWidth();
            int height2 = imageTwo.getHeight();
            int[] ImageArrayTwo = new int[width2 * height2];
            ImageArrayTwo = imageTwo.getRGB(0, 0, width, height, ImageArrayTwo, 0, width);
            //ImageArrayTwo  =  imageTwo.getRGB(0,0,width2,height2,ImageArrayTwo,0,width2);

            //生成新图片
            //int width3 = (width>width2 || width==width2)?width:width2;
            BufferedImage imageNew = new BufferedImage(width, height * 2, BufferedImage.TYPE_INT_RGB);
            //BufferedImage  imageNew  =  new  BufferedImage(width3,height+height2,BufferedImage.TYPE_INT_RGB);
            imageNew.setRGB(0, 0, width, height, imageArrayOne, 0, width);//设置上半部分的RGB
            imageNew.setRGB(0, height, width, height, ImageArrayTwo, 0, width);//设置下半部分的RGB
            //imageNew.setRGB(0,height,width2,height2,ImageArrayTwo,0,width2);//设置下半部分的RGB

            File outFile = new File(toPath);
            ImageIO.write(imageNew, imageFormat, outFile);//写图片
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 纵向拼接一组（多张）图像
     *
     * @param pics    将要拼接的图像数组
     * @param type    写入图像类型
     * @param dst_pic 写入图像路径
     * @return
     */
    public boolean joinImageListVertical(String[] pics, String type, String dst_pic) {
        try {
            int len = pics.length;
            if (len < 1) {
                System.out.println("pics len < 1");
                return false;
            }
            File[] src = new File[len];
            BufferedImage[] images = new BufferedImage[len];
            int[][] imageArrays = new int[len][];
            for (int i = 0; i < len; i++) {
                //System.out.println(i);
                src[i] = new File(pics[i]);
                images[i] = ImageIO.read(src[i]);
                int width = images[i].getWidth();
                int height = images[i].getHeight();
                imageArrays[i] = new int[width * height];// 从图片中读取RGB
                imageArrays[i] = images[i].getRGB(0, 0, width, height, imageArrays[i], 0, width);
            }

            int dst_height = 0;
            int dst_width = images[0].getWidth();
            for (int i = 0; i < images.length; i++) {
                dst_width = dst_width > images[i].getWidth() ? dst_width : images[i].getWidth();
                dst_height += images[i].getHeight();
            }
            //System.out.println(dst_width);
            //System.out.println(dst_height);
            if (dst_height < 1) {
                System.out.println("dst_height < 1");
                return false;
            }
            /*
             * 生成新图片
             */
            BufferedImage ImageNew = new BufferedImage(dst_width, dst_height, BufferedImage.TYPE_INT_RGB);
            int height_i = 0;
            for (int i = 0; i < images.length; i++) {
                ImageNew.setRGB(0, height_i, dst_width, images[i].getHeight(), imageArrays[i], 0, dst_width);
                height_i += images[i].getHeight();
            }
            File outFile = new File(dst_pic);
            ImageIO.write(ImageNew, type, outFile);// 写图片
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 将一组图片一次性附加合并到底图上
     *
     * @param negativeImagePath 源图像（底图）路径
     * @param additionImageList 附加图像信息列表
     * @param imageFormat       图像写入格式
     * @param toPath            图像写入路径
     * @throws IOException
     */
    public void mergeImageList(String negativeImagePath,
                               List<String[]> additionImageList, String imageFormat, String toPath) throws IOException {
        InputStream is = null;
        InputStream is2 = null;
        OutputStream os = null;
        try {
            is = new FileInputStream(negativeImagePath);
            BufferedImage image = ImageIO.read(is);
            //Graphics g=image.getGraphics();
            Graphics2D g = image.createGraphics();
            ;
            BufferedImage image2 = null;
            if (additionImageList != null) {
                for (int i = 0; i < additionImageList.size(); i++) {
                    //解析附加图片信息：x坐标、 y坐标、 additionImagePath附加图片路径
                    //图片信息存储在一个数组中
                    String[] additionImageInfo = (String[]) additionImageList.get(i);
                    int x = Integer.parseInt(additionImageInfo[0]);
                    int y = Integer.parseInt(additionImageInfo[1]);
                    String additionImagePath = additionImageInfo[2];
                    //读取文件输入流，并合并图片
                    is2 = new FileInputStream(additionImagePath);
                    //System.out.println(x+"  :  "+y+"  :  "+additionImagePath);
                    image2 = ImageIO.read(is2);
                    g.drawImage(image2, x, y, null);
                }
            }
            os = new FileOutputStream(toPath);
            ImageIO.write(image, imageFormat, os);//写图片
            //JPEGImageEncoder enc=JPEGCodec.createJPEGEncoder(os);
            //enc.encode(image);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (os != null) {
                os.close();
            }
            if (is2 != null) {
                is2.close();
            }
            if (is != null) {
                is.close();
            }
        }
    }

    /**
     * 将附加图片合并到底图的左上角
     *
     * @param negativeImagePath 底图路径
     * @param additionImagePath 附加图片路径
     * @param toPath            合成图片写入路径
     * @throws IOException
     */
    public void mergeBothImageTopleftcorner(String negativeImagePath, String additionImagePath, String toPath) throws IOException {
        InputStream is = null;
        InputStream is2 = null;
        OutputStream os = null;
        try {
            is = new FileInputStream(negativeImagePath);
            is2 = new FileInputStream(additionImagePath);
            BufferedImage image = ImageIO.read(is);
            BufferedImage image2 = ImageIO.read(is2);
            Graphics g = image.getGraphics();
            g.drawImage(image2, 0, 0, null);
            os = new FileOutputStream(toPath);
            JPEGImageEncoder enc = JPEGCodec.createJPEGEncoder(os);
            enc.encode(image);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (os != null) {
                os.close();
            }
            if (is2 != null) {
                is2.close();
            }
            if (is != null) {
                is.close();
            }
        }
    }

    /**
     * 将附加图片合并到底图的右上角
     *
     * @param negativeImagePath 底图路径
     * @param additionImagePath 附加图片路径
     * @param toPath            合成图片写入路径
     * @throws IOException
     */
    public void mergeBothImageToprightcorner(String negativeImagePath,
                                             String additionImagePath, String toPath) throws IOException {
        InputStream is = null;
        InputStream is2 = null;
        OutputStream os = null;
        try {
            is = new FileInputStream(negativeImagePath);
            is2 = new FileInputStream(additionImagePath);
            BufferedImage image = ImageIO.read(is);
            BufferedImage image2 = ImageIO.read(is2);
            Graphics g = image.getGraphics();
            g.drawImage(image2, image.getWidth() - image2.getWidth(), 0, null);
            os = new FileOutputStream(toPath);
            JPEGImageEncoder enc = JPEGCodec.createJPEGEncoder(os);
            enc.encode(image);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (os != null) {
                os.close();
            }
            if (is2 != null) {
                is2.close();
            }
            if (is != null) {
                is.close();
            }
        }
    }

    /**
     * 将附加图片合并到底图的左下角
     *
     * @param negativeImagePath 底图路径
     * @param additionImagePath 附加图片路径
     * @param toPath            合成图片写入路径
     * @throws IOException
     */
    public void mergeBothImageLeftbottom(String negativeImagePath,
                                         String additionImagePath, String toPath) throws IOException {
        InputStream is = null;
        InputStream is2 = null;
        OutputStream os = null;
        try {
            is = new FileInputStream(negativeImagePath);
            is2 = new FileInputStream(additionImagePath);
            BufferedImage image = ImageIO.read(is);
            BufferedImage image2 = ImageIO.read(is2);
            Graphics g = image.getGraphics();
            g.drawImage(image2, 0, image.getHeight() - image2.getHeight(), null);
            os = new FileOutputStream(toPath);
            JPEGImageEncoder enc = JPEGCodec.createJPEGEncoder(os);
            enc.encode(image);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (os != null) {
                os.close();
            }
            if (is2 != null) {
                is2.close();
            }
            if (is != null) {
                is.close();
            }
        }
    }

    /**
     * 将附加图片合并到底图的左下角
     *
     * @param negativeImagePath 底图路径
     * @param additionImagePath 附加图片路径
     * @param toPath            合成图片写入路径
     * @throws IOException
     */
    public void mergeBothImageRightbottom(String negativeImagePath,
                                          String additionImagePath, String toPath) throws IOException {
        InputStream is = null;
        InputStream is2 = null;
        OutputStream os = null;
        try {
            is = new FileInputStream(negativeImagePath);
            is2 = new FileInputStream(additionImagePath);
            BufferedImage image = ImageIO.read(is);
            BufferedImage image2 = ImageIO.read(is2);
            Graphics g = image.getGraphics();
            g.drawImage(image2, image.getWidth() - image2.getWidth(), image.getHeight() - image2.getHeight(), null);
            os = new FileOutputStream(toPath);
            JPEGImageEncoder enc = JPEGCodec.createJPEGEncoder(os);
            enc.encode(image);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (os != null) {
                os.close();
            }
            if (is2 != null) {
                is2.close();
            }
            if (is != null) {
                is.close();
            }
        }
    }

    /**
     * 将附加图片合并到底图的正中央
     *
     * @param negativeImagePath 底图路径
     * @param additionImagePath 附加图片路径
     * @param toPath            合成图片写入路径
     * @throws IOException
     */
    public void mergeBothImageCenter(String negativeImagePath,
                                     String additionImagePath, String toPath) throws IOException {
        InputStream is = null;
        InputStream is2 = null;
        OutputStream os = null;
        try {
            is = new FileInputStream(negativeImagePath);
            is2 = new FileInputStream(additionImagePath);
            BufferedImage image = ImageIO.read(is);
            BufferedImage image2 = ImageIO.read(is2);
            Graphics g = image.getGraphics();
            g.drawImage(image2, image.getWidth() / 2 - image2.getWidth() / 2, image.getHeight() / 2 - image2.getHeight() / 2, null);
            os = new FileOutputStream(toPath);
            JPEGImageEncoder enc = JPEGCodec.createJPEGEncoder(os);
            enc.encode(image);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (os != null) {
                os.close();
            }
            if (is2 != null) {
                is2.close();
            }
            if (is != null) {
                is.close();
            }
        }
    }

    /**
     * 将附加图片合并到底图的上边中央
     *
     * @param negativeImagePath 底图路径
     * @param additionImagePath 附加图片路径
     * @param toPath            合成图片写入路径
     * @throws IOException
     */
    public void mergeBothImageTopcenter(String negativeImagePath,
                                        String additionImagePath, String toPath) throws IOException {
        InputStream is = null;
        InputStream is2 = null;
        OutputStream os = null;
        try {
            is = new FileInputStream(negativeImagePath);
            is2 = new FileInputStream(additionImagePath);
            BufferedImage image = ImageIO.read(is);
            BufferedImage image2 = ImageIO.read(is2);
            Graphics g = image.getGraphics();
            g.drawImage(image2, image.getWidth() / 2 - image2.getWidth() / 2, 0, null);
            os = new FileOutputStream(toPath);
            JPEGImageEncoder enc = JPEGCodec.createJPEGEncoder(os);
            enc.encode(image);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (os != null) {
                os.close();
            }
            if (is2 != null) {
                is2.close();
            }
            if (is != null) {
                is.close();
            }
        }
    }

    /**
     * 将附加图片合并到底图的下边中央
     *
     * @param negativeImagePath 底图路径
     * @param additionImagePath 附加图片路径
     * @param toPath            合成图片写入路径
     * @throws IOException
     */
    public void mergeBothImageBottomcenter(String negativeImagePath,
                                           String additionImagePath, String toPath) throws IOException {
        InputStream is = null;
        InputStream is2 = null;
        OutputStream os = null;
        try {
            is = new FileInputStream(negativeImagePath);
            is2 = new FileInputStream(additionImagePath);
            BufferedImage image = ImageIO.read(is);
            BufferedImage image2 = ImageIO.read(is2);
            Graphics g = image.getGraphics();
            g.drawImage(image2, image.getWidth() / 2 - image2.getWidth() / 2,
                    image.getHeight() - image2.getHeight(), null);
            os = new FileOutputStream(toPath);
            JPEGImageEncoder enc = JPEGCodec.createJPEGEncoder(os);
            enc.encode(image);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (os != null) {
                os.close();
            }
            if (is2 != null) {
                is2.close();
            }
            if (is != null) {
                is.close();
            }
        }
    }

    /**
     * 将附加图片合并到底图的左边中央
     *
     * @param negativeImagePath 底图路径
     * @param additionImagePath 附加图片路径
     * @param toPath            合成图片写入路径
     * @throws IOException
     */
    public void mergeBothImageLeftcenter(String negativeImagePath,
                                         String additionImagePath, String toPath) throws IOException {
        InputStream is = null;
        InputStream is2 = null;
        OutputStream os = null;
        try {
            is = new FileInputStream(negativeImagePath);
            is2 = new FileInputStream(additionImagePath);
            BufferedImage image = ImageIO.read(is);
            BufferedImage image2 = ImageIO.read(is2);
            Graphics g = image.getGraphics();
            g.drawImage(image2, 0, image.getHeight() / 2 - image2.getHeight()
                    / 2, null);
            os = new FileOutputStream(toPath);
            JPEGImageEncoder enc = JPEGCodec.createJPEGEncoder(os);
            enc.encode(image);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (os != null) {
                os.close();
            }
            if (is2 != null) {
                is2.close();
            }
            if (is != null) {
                is.close();
            }
        }
    }

    /**
     * 将附加图片合并到底图的右边中央
     *
     * @param negativeImagePath 底图路径
     * @param additionImagePath 附加图片路径
     * @param toPath            合成图片写入路径
     * @throws IOException
     */
    public void mergeBothImageRightcenter(String negativeImagePath,
                                          String additionImagePath, String toPath) throws IOException {
        InputStream is = null;
        InputStream is2 = null;
        OutputStream os = null;
        try {
            is = new FileInputStream(negativeImagePath);
            is2 = new FileInputStream(additionImagePath);
            BufferedImage image = ImageIO.read(is);
            BufferedImage image2 = ImageIO.read(is2);
            Graphics g = image.getGraphics();
            g.drawImage(image2, image.getWidth() - image2.getWidth(),
                    image.getHeight() / 2 - image2.getHeight() / 2, null);
            os = new FileOutputStream(toPath);
            JPEGImageEncoder enc = JPEGCodec.createJPEGEncoder(os);
            enc.encode(image);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (os != null) {
                os.close();
            }
            if (is2 != null) {
                is2.close();
            }
            if (is != null) {
                is.close();
            }
        }
    }

}
