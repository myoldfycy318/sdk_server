package com.dome.sdkserver.util;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

import org.apache.commons.io.IOUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public final class ZipUtils {
	
	private static Logger logger = LoggerFactory.getLogger(ZipUtils.class);
	
	public static void main(String[] args) {
//		File srcZipFile = new File("D:\\NewWorkSpace\\开发平台2\\TestQbaoSDK_signed.apk");
//		File destDir = new File("F:\\destDir");
		//unzip(srcZipFile, destDir, false);

//		zip(destDir, "F:" + File.separator + "dest.zip");
		String srcFilePath="F:\\360Downloads\\1470215126284.apk";
		String destFilePath="F:\\test\\1470215126284.apk";
		String relativeFilePath="assets/DomeSdk/buildConfig.xml";
		String replaceContent="CHA9999999";
		modifyZipFile(srcFilePath, destFilePath, relativeFilePath, "ChannelId", replaceContent);
	}

	private ZipUtils(){}
	
	public static void zip(File srcFile, String zipFilename){
		
		FileOutputStream fos = null;
		ZipOutputStream zos = null;
		try {
			fos = new FileOutputStream(new File(zipFilename));
			zos = new ZipOutputStream(fos);
			
			File[] files = null;
			if (srcFile.isDirectory()) {
				// 目录条目，需要在目录名后加一个"/"
				zos.putNextEntry(new ZipEntry(srcFile.getName() + "/"));
				//files = srcFile.listFiles(); // 不包含子目录下的文件
				files = FileUtils.listFiles(srcFile, true);
				
			} else {
				files = new File[]{srcFile};
			}
			File parentFile = srcFile.getParentFile();
			for (File file : files){
				// 获取相对路径
				
				String entryName = FileUtils.getRelativePath(file, parentFile)
						+ (file.isDirectory() ? "/" : "");
				ZipEntry entry = new ZipEntry(entryName);
				zos.putNextEntry(entry);
				if (file.isFile()) {
					
					FileUtils.writeStream(file, zos);
				} 
				
			}
			
			
		} catch (FileNotFoundException e) {
			logger.error("file not found", e);
		} catch (IOException e) {
			logger.error("zip file occur IOException", e);
		} finally {
			IOUtils.closeQuietly(zos);
		}
	}
	
	/**
	 * 解压缩文件
	 * @param srcZipFile
	 * @param destDir
	 * @param createParentDir 解压缩时是否创建父目录，父目录文件名为压缩文件去掉扩展名后的文件名
	 */
	public static void unzip(File srcZipFile, File destDir, boolean createParentDir){
		ZipFile zipFile = null;
		File newDestDir = null;
		if (createParentDir) {
			String zipFileName = srcZipFile.getName();
			final int index = zipFileName.lastIndexOf(".");
			String parentDirName = null;
			if (index >-1){
				parentDirName = zipFileName.substring(0, index);
			} else {
				parentDirName =zipFileName;
			}
			
			newDestDir = new File(destDir, parentDirName);
			if (!newDestDir.exists()) newDestDir.mkdirs();
		} else {
			newDestDir = destDir;
		}
		try {
			zipFile = new ZipFile(srcZipFile);
			ZipEntry entry = null;
			for (Enumeration<? extends ZipEntry> em = zipFile.entries(); em.hasMoreElements(); ){
				entry = em.nextElement();
				File f = new File(newDestDir, entry.getName());
				
				if (!entry.isDirectory()){
					File parent = f.getParentFile();
					if (!parent.exists()) parent.mkdirs();
					InputStream in = zipFile.getInputStream(entry);
					FileUtils.writeFile(in, f);
				} else {
					if (!f.exists()) f.mkdirs();
				}
			}
		} catch (ZipException e) {
			logger.error("unzip file occur ZipException", e);
		} catch (IOException e) {
			logger.error("unzip file occur IOException", e);
		}
	}
	
	public static class FileUtils {

		private static Logger logger = LoggerFactory.getLogger(FileUtils.class);
		
		/**
		 * 读取一个输入流，写入到指定的文件
		 * @param in
		 * @param destFile
		 */
		public static void writeFile(InputStream in, File destFile){
			FileOutputStream fos = null;
			
			try {
				fos = new FileOutputStream(destFile);
				int len = -1;
				byte[] b = new byte[4096];
				while ((len = in.read(b)) != -1) {
					fos.write(b, 0, len);
				}
				fos.flush();
			} catch (FileNotFoundException e) {
				logger.error("dest file not found", e);
			} catch (IOException e) {
				logger.error("write inputstream to file error", e);
			} finally {
				IOUtils.closeQuietly(fos);
				IOUtils.closeQuietly(in);
			}
			
			
		}
		
		/**
		 * 读取文件流，写入到指定的输出流
		 * 输出流没有关闭
		 * @param srcFile
		 * @param out
		 */
		public static void writeStream(File srcFile, OutputStream out){
			try {
				writeStream(new FileInputStream(srcFile), out);
			} catch (FileNotFoundException e) {
				logger.error("file not found, path:" + srcFile.getAbsolutePath(), e);
			}
				
		}
		
		public static void writeStream(InputStream is, OutputStream out){
			InputStream fis = is;
			try {
				byte[] b = new byte[8192];
				int len = -1;
				while ((len = fis.read(b)) != -1) {
					out.write(b, 0, len); // 若使用out.write(b)间接调用的是out.write(b, 0, b.length)会导致文件中多写了很多0
				}
				out.flush();
			} catch (IOException e) {
				logger.error("write file to outstream occur IOException", e);
			} finally {
				// out流没有关，因为压缩了一个文件夹，输出流后面还要用
				IOUtils.closeQuietly(fis);
			}
		}
		/**
		 * 获取文件相对路径
		 * file1相对于file2的相对路径
		 * 直接使用replaceAll("F:\\destDir\\assets", "F:\\")抛出异常Unexpected internal error F:\
		 * @param file1
		 * @param file2
		 * @return
		 */
		public static String getRelativePath(File file1, File file2){
			String path1 = file1.getPath();
			String path2 = file2.getPath();
			String os = System.getProperty("os.name");
			if (os != null && os.toLowerCase().contains("windows")){
				path1 = path1.replace('\\', '/');
				path2 = path2.replace('\\', '/');
			}
			return path1.replaceAll(path2, "");
		}
		
		/**
		 * 列举目录中的文件，子目录、子目录中的文件都包含在内
		 * @param directory
		 * @param containSubDir
		 * @return
		 */
		public static File[] listFiles(File directory, boolean containSubDir){
			if (containSubDir) {
				//Collection<File> files = org.apache.commons.io.FileUtils.listFiles(file, null, true);
				List<File> files = new LinkedList<File>();
				//files.add(directory);
				listFiles(files, directory, null);
				File[] fileArray = new File[files.size()];
				return files.toArray(fileArray);
			} else {
				return directory.listFiles();
			}
		}
		
		private static void listFiles(List<File> list, File directory, FileFilter filter){
			File[] files= directory.listFiles(filter);
			for (File file : files){
				if (file.isDirectory()) {
					listFiles(list, file, filter);
				}
				// 目录也包含在内
				list.add(file);
			}
		}
	}
	
	/**
	 * 修改压缩文件中的某个文件，将elementName标识的节点文本内容替换为replaceContent
	 * 
	 * @param zipFilePath
	 * @param destFilePath
	 * @param relativeFilePath 修改的文件在压缩文件中的相对路径
	 * @param elementName
	 * @param replaceContent
	 */
	public static void modifyZipFile(String srcFilePath, String destFilePath, String relativeFilePath, String elementName, String replaceContent){
		ZipFile zipFile = null;
		InputStream in = null;
		SAXReader reader = null;
		ZipOutputStream zos =null;
		try {
			zipFile =new ZipFile(srcFilePath);
			ZipEntry zipEntry = zipFile.getEntry(relativeFilePath); // "assets/DomeSdk/buildConfig.xml"
			in = zipFile.getInputStream(zipEntry);
			reader = new SAXReader();
			Document doc = reader.read(in);
			Element sdkVersionElement = doc.getRootElement().element(elementName); // "ChannelId"
			sdkVersionElement.setText(replaceContent);
			String xmlContent =doc.asXML();
			logger.info("after modify, xml content: {}", xmlContent);
			zos=new ZipOutputStream(new FileOutputStream(destFilePath));
			for (Enumeration<? extends ZipEntry> entrys =zipFile.entries();entrys.hasMoreElements();){
				ZipEntry entry = entrys.nextElement();
				// 直接使用zos.putNextEntry(entry)抛出java.util.zip.ZipException: invalid entry compressed size (expected 1832 but got 1862 bytes)
				zos.putNextEntry(new ZipEntry(entry.getName()));
				if (!entry.isDirectory()){
					String entryName = entry.getName();
					InputStream entryInputStream =null;
					if (relativeFilePath.equalsIgnoreCase(entryName)){
						entryInputStream= new ByteArrayInputStream(xmlContent.getBytes("utf-8"));
					} else {
						entryInputStream = zipFile.getInputStream(entry);
					}
					FileUtils.writeStream(entryInputStream, zos);
				}
			}
			zos.flush();
		} catch (Exception e) {
			logger.error("parser apk package sdk version error", e);
		} finally {
			if (reader != null){
				reader = null;
			}
			IOUtils.closeQuietly(in);
			if (zipFile != null){
				try {
					zipFile.close();
				} catch (IOException e) {
					logger.error("close zip file error", e);
				}
			}
			IOUtils.closeQuietly(zos);
			
		}
		
	}
}
