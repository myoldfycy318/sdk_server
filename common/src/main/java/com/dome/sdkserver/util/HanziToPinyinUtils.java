package com.dome.sdkserver.util;

import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

/**
 * 汉字转换为中文
 * 
 * @author lilongwei
 *
 */
public class HanziToPinyinUtils {
	private static Logger log = LoggerFactory.getLogger(HanziToPinyinUtils.class);
	private static Pattern CHINESE_WORD_PATTERN = Pattern.compile("[\\u4E00-\\u9FA5]+");
    /**
     * 将汉字转换为拼音
     * 若参数中包含非中文字符，会原样保留
     * @param chineseText
     * @return
     */
    public static String getPingYin(String chineseText) {  
  
        char[] chArr  = chineseText.toCharArray();  
        HanyuPinyinOutputFormat pinyinOutputFormat = new HanyuPinyinOutputFormat();  
          
        pinyinOutputFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);  
        pinyinOutputFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);  
        pinyinOutputFormat.setVCharType(HanyuPinyinVCharType.WITH_V);  
        StringBuilder sb = new StringBuilder();  
        int len = chArr.length;  
        try {  
            for (int i = 0; i < len; i++) {  
                // 判断是否为汉字字符  
                if (CHINESE_WORD_PATTERN.matcher(Character.toString(chArr[i])).matches()) {  
                    String[] pinyin = PinyinHelper.toHanyuPinyinStringArray(chArr[i], pinyinOutputFormat);  
                    if (pinyin==null || pinyin.length==0 || "none0".equals(pinyin[0])) {
                    	log.info("中文转换为拼音，发现无法转换的字符，char=" + Character.toString(chArr[i]));
                    	continue;
                    }
                    sb.append(pinyin[0]);
                } else  
                    sb.append(chArr[i]);  
            } 
            return sb.toString();
        } catch (BadHanyuPinyinOutputFormatCombination e) {  
            log.error("中文转换为拼音出错，中文内容为：" + chineseText, e);  
        }  
        return "tanslate-error";  
    }
    
    public static void main(String[] args) {
		System.out.println(getPingYin("heLloWorld!中abcd国人efg"));
	}
}
