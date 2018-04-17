package com.dome.sdkserver.util;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.text.DecimalFormat;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;

/**
 * INFO: 有关文件操作的工具类
 * User: zhaokai@mail.qianwang365.com
 * Date: 2014/9/17
 * Time: 14:17
 * Version: 1.0
 * History: <p>如果有修改过程，请记录</P>
 */

public class FileUtils {

    /**
     * 默认的标准推荐使用的文件路径分隔符
     */
    public static final String DEFAULT_STANDARD_FILE_DELIMITER = "/";

    /**
     * 默认的不推荐使用的文件路径分隔符
     */
    public static final String DEFAULT_AGAINST_FILE_DELIMITER = "\\";

    /**
     * 默认的http协议地址头
     */
    public static final String DEFAULT_PREFIX_HTTP_PROTOCOL = "http://";

    /**
     * 获取文件格式
     *
     * @param imageFileName
     * @return
     */
    public static String getFileFormat(String fileName) {
        return fileName.substring(fileName.lastIndexOf('.') + 1).toLowerCase();
    }

    /**
     * 根据文件路径获取文件名称
     *
     * @param filePath
     * @return
     */
    public static String getFileName(String filePath) {
        filePath = formatFilePath(filePath);
        return filePath.substring(filePath.lastIndexOf(DEFAULT_STANDARD_FILE_DELIMITER) + 1);
    }

    /**
     * 获取文件大小
     *
     * @param fileFullPath
     * @return
     * @throws java.io.IOException
     */
    public static int getFileSize(String fileFullPath) throws IOException {
        int size = 0;
        fileFullPath = formatFilePath(fileFullPath);
        File file = new File(fileFullPath);
        if (file.exists() && !isDirectory(fileFullPath)) {
            FileInputStream fis = new FileInputStream(file);
            size = fis.available();
            if (fis != null) {
                fis.close();
            }
        }
        return size;
    }

    /**
     * 纠正不标准的文件路径分隔符
     * 如：\,\\,\\\,//,/// -> /
     *
     * @param path
     * @return
     */
    public static String formatFilePath(String path) {
        if (!StringUtils.isEmpty(path)) {
            boolean startWithHttpProtocol = path.toLowerCase().startsWith(DEFAULT_PREFIX_HTTP_PROTOCOL);
            if (startWithHttpProtocol) {
                path = path.substring(DEFAULT_PREFIX_HTTP_PROTOCOL.length());
            }
            // 将一个或多个“\”转化成“/”
            path = path.replaceAll("\\\\{1,}", "/");
            // 将多个“/”转化成一个“/”
            path = path.replaceAll("\\/{2,}", "/");
            if (startWithHttpProtocol) {
                path = DEFAULT_PREFIX_HTTP_PROTOCOL + path;
            }
        }
        return path;
    }

    /**
     * 在path前面加上/
     *
     * @param path
     * @return
     */
    public static String formatFileDyncPath(String path) {
        path = formatFilePath(path);
        if (StringUtils.startsWith(path, FileUtils.DEFAULT_STANDARD_FILE_DELIMITER)) {
            return path;
        }
        return FileUtils.DEFAULT_STANDARD_FILE_DELIMITER + path;
    }

    /**
     * 根据文件路径获取File实例
     *
     * @param filePath
     * @return
     * @throws Exception
     */
    public static File getFile(String filePath) {
        filePath = formatFilePath(filePath);
        return new File(filePath);
    }

    /**
     * 创建文件目录
     *
     * @param filePath
     * @return true-创建了新目录;false-没有创建新目录
     * @throws Exception
     */
    public static boolean mkDirIfNecessary(String filePath) throws Exception {
        filePath = formatFilePath(filePath);
        File dirFile = new File(getFileDir(filePath));
        if (!dirFile.exists()) {
            dirFile.mkdirs();
            return true;
        }
        return false;
    }

    /**
     * 根据文件路径判断该路径表示的是文件还是目录
     *
     * @param filePath
     * @return
     */
    public static boolean isDirectory(String filePath) {
        if (!StringUtils.isEmpty(filePath)) {
            int index1 = filePath.lastIndexOf('.');
            if (index1 == -1) {
                return true;
            } else {
                int index2 = filePath.lastIndexOf('/') == -1 ? filePath.lastIndexOf('\\') : filePath.lastIndexOf('/');
                if (index2 != -1) {
                    if (index1 > index2) {
                        return false;
                    } else {
                        return true;
                    }
                } else {
                    return false;
                }
            }
        }
        return false;
    }

    /**
     * 根据文件路径获取其目录
     *
     * @param filePath
     * @return
     */
    public static String getFileDir(String filePath) {
        if (!StringUtils.isEmpty(filePath)) {
            filePath = formatFilePath(filePath);
            if (isDirectory(filePath)) {
                return filePath;
            } else {
                return filePath.substring(0, filePath.lastIndexOf(DEFAULT_STANDARD_FILE_DELIMITER));
            }
        }
        System.out.println(filePath);
        return filePath;
    }

    /**
     * 重命名文件名
     *
     * @param originalName - 原文件名
     * @param renameAll    - true-舍弃原文件名完全做随机重新命名;false-在原文件名后面做随机重命名
     * @param appendStr    - 加在文件名后的追加后缀,e.g. ${originalName}_${appendStr}.jpg
     * @return
     * @throws Exception
     */
    public static String renameFile(String originalName, boolean renameAll, String appendStr) {
        String suffix = originalName.substring(originalName.lastIndexOf('.') + 1);
        String fileName = originalName.substring(0, originalName.lastIndexOf('.'));
        String randomName = UUID.randomUUID().toString().replace("-", "");
        if (!StringUtils.isEmpty(appendStr)) {
            return String.format("%s_%s.%s", renameAll ? randomName : fileName + "_" + randomName.substring(0, 8), appendStr, suffix);
        } else {
            return String.format("%s.%s", renameAll ? randomName : fileName + "_" + randomName.substring(0, 8), suffix);
        }
    }

    /**
     * 文件复制
     *
     * @param srcFullFileName  - 源文件名
     * @param destFullFileName - 目标文件名
     * @throws Exception
     */
    public static void copyFile(String srcFullFileName, String destFullFileName) throws Exception {

        //获取源文件和目标文件的输入输出字节流
        FileInputStream fis = null;
        FileOutputStream fos = null;
        //获取输入输出通道
        FileChannel fci = null;
        FileChannel fco = null;
        try {
            mkDirIfNecessary(destFullFileName);
            fis = new FileInputStream(srcFullFileName);
            fos = new FileOutputStream(destFullFileName);
            fci = fis.getChannel();
            fco = fos.getChannel();
            //创建字节缓冲区
            ByteBuffer bbuffer = ByteBuffer.allocate(1024);
            while (true) {
                //clear缓冲区以接受新的数据
                bbuffer.clear();
                //从输入通道中将数据读出来,读到流末尾返回-1
                int r = fci.read(bbuffer);
                if (r == -1) {
                    break;
                }
                //反转此缓冲区,让缓冲区可以将新读入的数据写入输出通道
                bbuffer.flip();
                //从输出通道中将数据写入缓冲区
                fco.write(bbuffer);
            }
        } finally {
            if (fci != null) {
                fci.close();
            }
            if (fis != null) {
                fis.close();
            }
            if (fco != null) {
                fco.close();
            }
            if (fos != null) {
                fos.close();
            }
        }
    }

    /**
     * 文件复制
     *
     * @param srcFile  - 源文件
     * @param destFile - 目标文件
     * @throws Exception
     */
    public static void copyFile(File srcFile, File destFile) throws Exception {

        //获取源文件和目标文件的输入输出字节流
        FileInputStream fis = null;
        FileOutputStream fos = null;
        //获取输入输出通道
        FileChannel fci = null;
        FileChannel fco = null;
        try {
            fis = new FileInputStream(srcFile);
            fos = new FileOutputStream(destFile);
            fci = fis.getChannel();
            fco = fos.getChannel();
            //创建字节缓冲区
            ByteBuffer bbuffer = ByteBuffer.allocate(1024);
            while (true) {
                //clear缓冲区以接受新的数据
                bbuffer.clear();
                //从输入通道中将数据读出来,读到流末尾返回-1
                int r = fci.read(bbuffer);
                if (r == -1) {
                    break;
                }
                //反转此缓冲区,让缓冲区可以将新读入的数据写入输出通道
                bbuffer.flip();
                //从输出通道中将数据写入缓冲区
                fco.write(bbuffer);
            }
        } finally {
            if (fci != null) {
                fci.close();
            }
            if (fis != null) {
                fis.close();
            }
            if (fco != null) {
                fco.close();
            }
            if (fos != null) {
                fos.close();
            }
        }
    }

    /**
     * 获取文件大小的简单形式
     *
     * @param fileSize - 单位byte
     * @return
     */
    public static String resolveFileSizeDisplayValue(long fileSize) {
        DecimalFormat df = new DecimalFormat("#");
        if (fileSize < 0) {
            return "不限";
        } else if (fileSize < 1024) {
            return fileSize + "Byte";
        } else if (fileSize < (1024 * 1024)) {
            return df.format(fileSize / (1024 * 1.0)) + "KB";
        } else if (fileSize < (1024 * 1024 * 1024)) {
            return df.format(fileSize / (1024 * 1024 * 1.0)) + "MB";
        } else {
            return df.format(fileSize / (1024 * 1024 * 1024 * 1.0)) + "GB";
        }
    }

    /**
     * 删除文件
     *
     * @param fullPath
     * @return
     */
    public static boolean deleteFile(String fullPath) {
        File file = getFile(fullPath);
        if (file.exists()) {
            return file.delete();
        }
        return false;
    }

    /**
     * 尽最大努力删除文件,删除失败不抛出异常
     *
     * @param fullPath
     * @return
     */
    public static void deleteFileQuietly(String fullPath) {
        try {
            File file = getFile(fullPath);
            if (file.exists()) {
                file.delete();
            }
        } catch (Exception e) {
        }
    }


    public static void main(String[] args) throws Exception {
        System.out.println(resolveFileSizeDisplayValue((1024 * 1024) / 2));
    }
}
