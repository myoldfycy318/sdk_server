package com.dome.sdkserver.biz.impl.pay;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dome.sdkserver.metadata.entity.bq.pay.OrderEntity;
import com.dome.sdkserver.service.BqSdkConstants;
import com.dome.sdkserver.util.ApiConnector;
import com.dome.sdkserver.util.RSACoder;

public class NotifyGame {
	
	protected static final Logger log = LoggerFactory.getLogger(NotifyGame.class);
	
	public static boolean sendMessage(OrderEntity order){
		String response = null;
		try {
			List<NameValuePair> pairs = new ArrayList<NameValuePair>();
			pairs.add(new BasicNameValuePair("responseCode", order.getGameOrderNo()));
			pairs.add(new BasicNameValuePair("signCode", "K8gp8iOB7LEGEwjH2ChDNfz45rFWLCGtzU83i0qlUMqQUVB2ggSZQA7XhNvXIvAZYevcRQO9oT4Tv8pfAcG5RpYJpQ/mRyoWYj6C4cNfOgifkj9QrnKsS54XlMZJh+WyNxJ6fU20Ndylf0SgmmkWMwKO1oExMiabjfLBlcrvlQE="));
			response = ApiConnector.post(order.getPayNotifyUrl(), pairs);
			System.out.println(response);
			return true;
		} catch (Exception e) {
			log.error("send message to game server faild",e);
		}
		
		return false;
	}

	/**
	 * 异步通知
	 * @param responseentity
	 */
	private void asyncNotice(OrderEntity order) {
		List<NameValuePair> pairs = new ArrayList<NameValuePair>();
		pairs.add(new BasicNameValuePair("responseCode", ""));
		pairs.add(new BasicNameValuePair("errorCode", ""));
		pairs.add(new BasicNameValuePair("errorMsg",""));
		pairs.add(new BasicNameValuePair("data", ""));
		
		
		StringBuffer sb = new StringBuffer();
		sb.append("responseCode=").append("1000").append(",")
		.append("errorCode=").append("").append(",")
		.append("sdkflowId=").append(order.getOrderNo()).append(",")
		.append("orderNo=").append("");
		
		/**加签**/
		String privateKey = "";
		String signCode = "";
		try {
			signCode = RSACoder.sign(sb.toString().getBytes(), privateKey);
		} catch (Exception e) {
		}
		
		pairs.add(new BasicNameValuePair("signCode", signCode));
		
		String res = ApiConnector.post("", pairs);
		String status;
		Boolean isSuccess = null;
		if(!StringUtils.isBlank(res)){
			JSONObject json = JSONObject.parseObject(res);
			isSuccess = json.getBoolean("isSuccess");
		}
		
	}
	
	public static void main(String[] args) {
		Boolean isSuccess = null;
		boolean sucess = check();
		System.out.println(sucess);
	}
	
	public static boolean check(){
		Boolean isSuccess = null;
		return isSuccess;
	}
}
