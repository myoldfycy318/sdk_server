package com.dome.sdkserver.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//import com.sun.image.codec.jpeg.ImageFormatException;
//import com.sun.image.codec.jpeg.JPEGImageDecoder;

public class ImageUtil {
	protected final static Logger log = LoggerFactory.getLogger(ImageUtil.class);
	
	public static boolean valImageSize(InputStream pic,int heigth, int width){
		boolean flag = false;
		try {
			BufferedImage bi = ImageIO.read(pic);
			if(heigth == bi.getHeight() && width == bi.getWidth()){
				flag = true;
			}
		} catch (IOException e) {
			log.error("图片上传大小异常,原因{}",e.getMessage());
		}
		return flag;
	}
	
	/**
	 * 默认java支持rgb模式，由扫描仪扫描，或者数码相机拍摄，并且处理时，图片的颜色空间（colorspace)为CMYK模式。 
	 * 导致了ImageIO.read()方法抛出异常的原因。 
	 * ImageIO.read报错：java.lang.IllegalArgumentException: Invalid ICC Profile Data
	 * : bad sequence number
	 * @param is
	 * @return
	 * @throws ImageFormatException
	 * @throws IOException
	 */
//	public static BufferedImage generateBufferedImage(InputStream is) throws ImageFormatException, IOException{
//		JPEGImageDecoder decoder = com.sun.image.codec.jpeg.JPEGCodec.createJPEGDecoder(is);
//		return decoder.decodeAsBufferedImage();
//	}
//
//	public static void main(String[] args) {
//		FileInputStream is = null;
//		try {
//			is=new FileInputStream("D:\\技术学习\\Java\\2.jpg");
////			is=new FileInputStream("D:\\qq_file\\274460611\\FileRecv\\仗剑天涯.jpg");
//			BufferedImage image = generateBufferedImage(is);
//			File outFile=new File("D:\\技术学习\\Java\\convert_pic.jpg");
//			ImageIO.write(image, "jpg", outFile);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}finally{
//			IOUtils.closeQuietly(is);
//		}
//	}
}
