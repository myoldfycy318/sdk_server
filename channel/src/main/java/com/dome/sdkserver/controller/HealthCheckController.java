package com.dome.sdkserver.controller;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;

@Controller
@RequestMapping("/health")
public class HealthCheckController {

	private final Logger log = LoggerFactory.getLogger(this.getClass());
	@RequestMapping("/check")
	@ResponseBody
	public void check(HttpServletRequest request, HttpServletResponse response){
		Map<String, String> data = new HashMap<String, String>();
		data.put("result", "ok");
		
		try {
			StreamUtils.copy(JSONObject.toJSONString(data), Charset.forName("utf-8"), response.getOutputStream());
		} catch (IOException e) {
			log.error("write health check message error", e);
		}
		
	}
}
