package com.dome.sdkserver.web.tools;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import com.dome.sdkserver.util.DomUtil;
import com.dome.sdkserver.bq.constants.ParamBaseValResEnum;



/**
 * 
 * 描述：
 * 参数校验
 * @author hexiaoyi
 */
public class ValidateParamsTool {
	
	protected final Logger log = LoggerFactory.getLogger(this.getClass());
	
	private static final String FIELD_PATH =  "fields/field";
	
	private static final String FIELD_CHECK_NULL =  "field/check-null";

	private static final String FIELD_CHECK_LENGTH =  "field/check-length";

	private static final String FIELD_CHECK_EXP =  "field/check-exp";
	
	private static final String FIELD_ATTR =  "attr";

	private static final String ALLOW_NULL =  "/allow-null";

	private static final String MAX_LENGTH =  "/max-length";

	private static final String EXP =  "/exp";
	
	private static final String CODE =  "/code";
	
	public Map<String, Object> valParam(HttpServletRequest request,String xml){
		Map<String ,Object> resultMap = new HashMap<String ,Object>();
		try {
			List<String> attrs = DomUtil.dealToItemAttr(xml, FIELD_PATH, FIELD_ATTR);
			List<String> fieldXmls = DomUtil.dealToItem(xml, FIELD_PATH);
			for(int i = 0 ;i < fieldXmls.size() ; i++){
				String attrName = attrs.get(i);
				String fieldXml = fieldXmls.get(i);
				String requestValue = request.getParameter(attrName);
				resultMap = this.valRule(requestValue, fieldXml);
				int responseCode = (Integer)resultMap.get("responseCode");
				if(1005 == responseCode){
					return resultMap;
				}
			}
		} catch (Exception e) {
			log.error("校验异常 {}" + e.getMessage());
		}
		return resultMap;
	}
	
	/**
	 * 验证规则
	 * @param requestValue
	 * @param fieldXml
	 * @return
	 */
	private Map<String ,Object> valRule(String requestValue,String fieldXml){
		//非空校验
		Map<String ,Object> resultMap = this.valAllowNull(requestValue, fieldXml);
		int responseCode = (Integer) resultMap.get("responseCode");
		if(1005 == responseCode){
			return resultMap;
		}
		//字段长度校验
		resultMap = this.valMaxLength(requestValue, fieldXml);
		responseCode = (Integer) resultMap.get("responseCode");
		if(1005 == responseCode){
			return resultMap;
		}
		//正则校验
		resultMap = this.valExp(requestValue, fieldXml);
		responseCode = (Integer) resultMap.get("responseCode");
		if(1005 == responseCode){
			return resultMap;
		}
		return resultMap;
	}
	
	private Map<String, Object> valAllowNull(String requestValue,String fieldXml){
		//非空校验
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			List<String> allowNulls = DomUtil.dealToItemText(fieldXml, FIELD_CHECK_NULL + ALLOW_NULL);
			if(!CollectionUtils.isEmpty(allowNulls)){
				String allowNull = allowNulls.get(0);
				//不可为空
				if(allowNull.equals("1") && StringUtils.isBlank(requestValue)){
					resultMap.put("responseCode", 1005);
					List<String> codes = DomUtil.dealToItemText(fieldXml, FIELD_CHECK_NULL + CODE);
					if(!CollectionUtils.isEmpty(codes)){
						String code = codes.get(0);
						resultMap.put("errorCode", code);
						resultMap.put("errorMsg", ParamBaseValResEnum.getFromKey(code).getResponeMsg());
					}
				}else{
					resultMap.put("responseCode", 1000);
				}
			}else{
				resultMap.put("responseCode", 1000);
			}
		} catch (Exception e) {
			resultMap.put("responseCode", 1005);
			log.error("校验为空异常：{}",e.getMessage());
		}
		return resultMap;
	}
	
	private Map<String, Object> valMaxLength(String requestValue,String fieldXml){
		//字段长度校验
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			List<String> maxLengths = DomUtil.dealToItemText(fieldXml, FIELD_CHECK_LENGTH + MAX_LENGTH);
			if(!CollectionUtils.isEmpty(maxLengths)){
				String maxLength = maxLengths.get(0);
				//参数超长
				if(requestValue != null && requestValue.length() > Integer.valueOf(maxLength)){
					resultMap.put("responseCode", 1005);
					List<String> codes = DomUtil.dealToItemText(fieldXml, FIELD_CHECK_LENGTH + CODE);
					if(!CollectionUtils.isEmpty(codes)){
						String code = codes.get(0);
						resultMap.put("errorCode", code);
						resultMap.put("errorMsg", ParamBaseValResEnum.getFromKey(code).getResponeMsg());
					}
				}else{
					resultMap.put("responseCode", 1000);
				}
			}else{
				resultMap.put("responseCode", 1000);
			}
		} catch (Exception e) {
			resultMap.put("responseCode", 1005);
			log.error("校验最大长度异常：{}",e.getMessage());
		}
		return resultMap;
	}
	
	private Map<String, Object> valExp(String requestValue,String fieldXml){
		//正则校验
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			List<String> exps = DomUtil.dealToItemText(fieldXml, FIELD_CHECK_EXP + EXP);
			if(!CollectionUtils.isEmpty(exps)){
				String exp = exps.get(0);
				//参数超长
				if(requestValue != null && !requestValue.matches(exp)){
					resultMap.put("responseCode", 1005);
					List<String> codes = DomUtil.dealToItemText(fieldXml, FIELD_CHECK_EXP + CODE);
					if(!CollectionUtils.isEmpty(codes)){
						String code = codes.get(0);
						resultMap.put("errorCode", code);
						resultMap.put("errorMsg", ParamBaseValResEnum.getFromKey(code).getResponeMsg());
					}
				}else{
					resultMap.put("responseCode", 1000);
				}
			}else{
				resultMap.put("responseCode", 1000);
			}
		} catch (Exception e) {
			resultMap.put("responseCode", 1005);
			log.error("校验正则异常：{}",e.getMessage());
		}
		return resultMap;
	}
}
