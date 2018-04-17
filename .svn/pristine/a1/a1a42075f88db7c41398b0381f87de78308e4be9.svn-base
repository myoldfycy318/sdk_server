package com.dome.sdkserver.interceptors;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.dome.sdkserver.bq.constants.ParamBaseValResEnum;
import com.dome.sdkserver.bq.constants.TransTypeEnum;
import com.dome.sdkserver.web.tools.ReadTransValTool;
import com.dome.sdkserver.web.tools.ValidateParamsTool;


/**
 * 
 * 描述：
 * 
 * @author hexiaoyi
 */
public class TransParamValInterceptor implements HandlerInterceptor  {
	
	@Resource
	private ReadTransValTool readTransValTool;
	
	@Resource
	private ValidateParamsTool validateParamsTool;
	
	@Override
	public void afterCompletion(HttpServletRequest arg0,
			HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1,
			Object arg2, ModelAndView arg3) throws Exception {
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
			Object handler) throws Exception {
//		return true;
		String transType = request.getParameter("transType");
		Map<String, Object> resultMap = new HashMap<String, Object>();
		response.setContentType("text/html;charset=utf-8");
		if(TransTypeEnum.getFromKey(transType) == null){
			PrintWriter out = response.getWriter();
			resultMap.put("responseCode", 1005);
			resultMap.put("errorCode", ParamBaseValResEnum.TRANS_TYPE_ILLEGAL.getResponeCode());
			String errorMsg = ParamBaseValResEnum.TRANS_TYPE_ILLEGAL.getResponeMsg();
			resultMap.put("errorMsg", errorMsg);
			out.write(JSON.toJSON(resultMap).toString());  
			out.flush();  
			out.close(); 
			return false;
		}
		//参数校验
		String xml = readTransValTool.getContentMap().get("trans"+transType);
		resultMap = validateParamsTool.valParam(request, xml);
		int responseCode = (Integer)resultMap.get("responseCode");
		if(1000 == responseCode){
			return true;
		}else{
			//适配处理返回值
			this.adapterReturnMsg(transType, resultMap, request);
			PrintWriter out = response.getWriter();
			out.write(JSON.toJSON(resultMap).toString());  
			out.flush();  
			out.close(); 
		}
		return false;
	}
	
	/**
	 * 适配处理返回值
	 * @param transType
	 * @param resultMap
	 */
	private void adapterReturnMsg(String transType,Map<String, Object> map,HttpServletRequest request){
//		//授权
//		if(transType.equals(TransTypeEnum.TRANS_TYPE_GRANT.getTransCode())){
//			map.put("tokenCode", "");
//			map.put("merchantTransCode", request.getParameter("merchantTransCode"));
//			map.put("transTime", request.getParameter("transTime"));
//		}else if(transType.equals(TransTypeEnum.TRANS_TYPE_PAY.getTransCode())){//支付
//			map.put("checkOutUrl", "");
//			map.put("qbTransCode", "");
//			map.put("merchantTransCode", request.getParameter("merchantTransCode"));
//			map.put("transTime", request.getParameter("transTime"));
//		}else if(transType.equals(TransTypeEnum.TRANS_TYPE_REFUND.getTransCode())){//退款
//			map.put("refundTransNum", request.getParameter("refundTransNum"));
//			map.put("qbRefundNum", "");
//			map.put("refundDateTime", request.getParameter("refundDateTime"));
//		}
		Map<String, Object> resultMap = new HashMap<String, Object>();
		if(transType.equals(TransTypeEnum.TRANS_TYPE_SIGNLE_QUERY.getTransCode())){//3010,单笔查询
			resultMap.put("orderNo", request.getParameter("orderNo"));
			resultMap.put("appCode",request.getParameter("appCode"));
			resultMap.put("queryType",request.getParameter("queryType"));
			resultMap.put("transType",request.getParameter("transType"));
			map.put("data", JSON.toJSON(resultMap));
		}else if(transType.equals(TransTypeEnum.TRANS_TYPE_SDK_SERVER.getTransCode())){//2012,SDK支付
			resultMap.put("transType",request.getParameter("transType"));
			resultMap.put("appCode", request.getParameter("appCode"));
			resultMap.put("orderNo", request.getParameter("orderNo"));
			resultMap.put("userId", request.getParameter("userId"));
			resultMap.put("payCode", request.getParameter("payCode"));
			resultMap.put("payNotifyUrl", request.getParameter("payNotifyUrl"));
			map.put("data", JSON.toJSON(resultMap));
		}
	}

}
