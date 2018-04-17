package com.dome.sdkserver.controller.image;

import java.awt.image.BufferedImage;  
import java.io.IOException;

import javax.imageio.ImageIO;  
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;  
import javax.servlet.http.HttpServletRequest;  
import javax.servlet.http.HttpServletResponse;  
import javax.servlet.http.HttpSession;  
  









import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;  
import org.springframework.stereotype.Controller;  
import org.springframework.web.bind.annotation.RequestMapping;  
import org.springframework.web.servlet.ModelAndView;  
  









import com.dome.sdkserver.bq.util.IPUtil;
import com.dome.sdkserver.service.BqSdkConstants;
import com.dome.sdkserver.util.RedisUtil;
import com.google.code.kaptcha.Constants;  
import com.google.code.kaptcha.NoiseProducer;
import com.google.code.kaptcha.Producer;  
import com.google.code.kaptcha.impl.DefaultNoise;
import com.google.code.kaptcha.servlet.KaptchaExtend;
  
/** 
 * 防止Captcha机器人登陆 
 * @author liuwang 
 * 
 */  
@Controller  
@RequestMapping("/kaptcha/")  
public class CaptchaController extends KaptchaExtend{  
    
    @Autowired  
    private Producer captchaProducer = null;

    @Autowired
	private RedisUtil redisUtil;
    
    @RequestMapping(value = "getKaptchaImage")
    public void getKaptchaImage(HttpServletRequest request, HttpServletResponse response) throws Exception {  
//        HttpSession session = request.getSession();  
//        String code = (String)session.getAttribute(Constants.KAPTCHA_SESSION_KEY);  
//        System.out.println("******************验证码是: " + code + "******************");  
        
        response.setDateHeader("Expires", 0);  
          
        // Set standard HTTP/1.1 no-cache headers.  
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");  
          
        // Set IE extended HTTP/1.1 no-cache headers (use addHeader).  
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");  
          
        // Set standard HTTP/1.0 no-cache header.  
        response.setHeader("Pragma", "no-cache");  
          
        // return a jpeg  
        response.setContentType("image/jpeg");  
          
        // create the text for the image  
        String capText = captchaProducer.createText();  
        
        System.out.println("capText = " + capText);
        // store the text in the session  
        //session.setAttribute(Constants.KAPTCHA_SESSION_KEY, capText);  
        
        String key = request.getParameter("key");
        if(StringUtils.isBlank(key)){
        	key = IPUtil.getIpAddr(request);
        }
        String redisKey = BqSdkConstants.domesdk_login_verifyImage+key+capText;
        redisUtil.set(redisKey, capText);
        redisUtil.expire(redisKey, 60);
        
        // create the image with the text  
        BufferedImage bi = captchaProducer.createImage(capText);
        ServletOutputStream out = response.getOutputStream();  
          
        // write the data out  
        ImageIO.write(bi, "jpg", out);  
        try {  
            out.flush();  
        } finally {  
            out.close();  
        }  
    }
    
    @RequestMapping(value = "verifyImage")
    public void verifyImage(HttpServletRequest request, HttpServletResponse response) throws Exception {  
    	String verifyCode = request.getParameter("verifyCode");
    	if(StringUtils.isBlank(verifyCode)){
    		return;
    	}
    	
    	String key = request.getParameter("key");
    	if(StringUtils.isBlank(key)){
    		key = IPUtil.getIpAddr(request);
    	}
    	String redisKey = "domesdk_login_verifyImage"+key+verifyCode;
    	String value = redisUtil.get(redisKey);
    	if(StringUtils.isBlank(value) || !verifyCode.endsWith(value)){
    		return;
    	}
    }
}  