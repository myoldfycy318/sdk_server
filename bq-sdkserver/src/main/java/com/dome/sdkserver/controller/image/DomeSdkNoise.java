package com.dome.sdkserver.controller.image;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Random;

import com.google.code.kaptcha.NoiseProducer;
import com.google.code.kaptcha.util.Configurable;

public class DomeSdkNoise extends Configurable implements NoiseProducer {

	@Override
	public void makeNoise(BufferedImage image, float factorOne,
			float factorTwo, float factorThree, float factorFour) {
	        Graphics2D g2 = image.createGraphics();  
	        
	        int w = 125;
	        int h = 45;
	        //绘制干扰线  
	        Random random = new Random();  
	        g2.setColor(getRandColor());// 设置线条的颜色  
	        for (int i = 0; i < 5; i++) {  
	            int x = random.nextInt(w - 1);  
	            int y = random.nextInt(h - 1);  
	            int xl = random.nextInt(6) + 1;  
	            int yl = random.nextInt(12) + 1;  
	            g2.setColor(getRandColor());
	            g2.drawLine(x, y, x + xl + 40, y + yl + 20);
	            //g2.drawOval(x, y, random.nextInt(5), random.nextInt(5));
	        }
//	        float yawpRate = 0.05f;// 噪声率  
//	        int area = (int) (yawpRate * 100 * 160);  
//	        for (int i = 0; i < area; i++) {
//	        	int x = random.nextInt(w);
//	        	int y = random.nextInt(h);
//	        	int rgb = random.nextInt(255);
//	        	image.setRGB(x, y, rgb);  
//	        }  
//	        g2.setColor(getRandColor());  
	        
	}

	 private static Color getRandColor() {  
	       	Random random = new Random();
	        int r = random.nextInt(255);  
	        int g = random.nextInt(255);  
	        int b = random.nextInt(222);  
	        return new Color(r, g, b);  
	}  
}
