package com.dome.sdkserver.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.apache.commons.io.IOUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import net.dongliu.apk.parser.ApkParser;
import net.dongliu.apk.parser.bean.ApkMeta;
import net.dongliu.apk.parser.exception.ParserException;
import net.dongliu.apk.parser.parser.ApkMetaTranslator;
import net.dongliu.apk.parser.parser.CompositeXmlStreamer;
import net.dongliu.apk.parser.parser.XmlStreamer;
import net.dongliu.apk.parser.parser.XmlTranslator;

/**
 * 解析apk包，获得冰穹sdk版本号
 * @author li
 *
 */
public class MyApkParser extends ApkParser{

	private static Logger log = LoggerFactory.getLogger(MyApkParser.class);
	
	private static String sdkXmlPath = "assets/DomeSdk/buildConfig.xml";
	
	private String sdkXml;
	
	public MyApkParser(File apkFile) throws IOException {
		super(apkFile);
	}

	public MyApkParser(String filePath) throws IOException {
		super(filePath);
	}
	
	public String getSdkXml() throws IOException {
		if (sdkXml == null)
			parseSdkXml();
		return sdkXml;
	}

	private void parseSdkXml() throws IOException {
		XmlTranslator xmlTranslator = new XmlTranslator();
		ApkMetaTranslator translator = new ApkMetaTranslator();
		XmlStreamer xmlStreamer = new CompositeXmlStreamer(new XmlStreamer[] {
				xmlTranslator, translator });
		transBinaryXml(sdkXmlPath, xmlStreamer);
		sdkXml = xmlTranslator.getXml();
		if (sdkXml == null) {
			throw new ParserException("sdk xml not exists");
		}
	}

	private void transBinaryXml(String path, XmlStreamer xmlStreamer)
			throws IOException {
		// 反射技术调用父类的私有方法

		try {
			Method method = ApkParser.class.getDeclaredMethod("transBinaryXml",
					String.class, XmlStreamer.class);
			method.setAccessible(true);
			method.invoke(this, path, xmlStreamer);
		} catch (Exception e) {
			log.error("reflect invoke private method error", e);
		}

	}
	
	/**
	 * 解决问题安卓安装包中assets文件夹中文件没有经过加密，使用dongliu工具解析会报net.dongliu.apk.parser.exception.ParserException: Unexpected chunk type:16188
	 * 因此使用解压缩，dom4j解析xml
	 * <SdkConfig>
	 *	    <ChannelId>CHA000002</ChannelId>
	 *		<SdkVersion>10001</SdkVersion>
	 * </SdkConfig>
	 * @param apkFile
	 * @return
	 */
	public static final String getSdkVersion(File apkFile){
		ZipFile zipFile = null;
		InputStream in = null;
		SAXReader reader = null;
		try {
			zipFile = new ZipFile(apkFile);
				ZipEntry entry = zipFile.getEntry(sdkXmlPath);
				in = zipFile.getInputStream(entry);
				reader = new SAXReader();
				Document doc = reader.read(in);
				Element sdkVersionElement = doc.getRootElement().element("SdkVersion");
				return sdkVersionElement.getText();
		} catch (Exception e) {
			log.error("parser apk package sdk version error", e);
		} finally {
			if (reader != null){
				reader = null;
			}
			IOUtils.closeQuietly(in);
			if (zipFile != null){
				try {
					zipFile.close();
				} catch (IOException e) {
					log.error("close zip file error", e);
				}
			}
			
		}
		
		return null;
	}
	public static void main(String[] args) {
		File apkFile = new File("D:\\NewWorkSpace\\开发平台2\\TestQbaoSDK_signed.apk");
		try {
			// 要求jdk 1.8
			MyApkParser parser = new MyApkParser(apkFile);
			String xml = parser.getSdkXml();
			System.out.println("xml: " + xml);
			ApkMeta meta = parser.getApkMeta();
			System.out.println("meta: " + meta);
			parser.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("sdk version:" + getSdkVersion(apkFile));
	}

}
