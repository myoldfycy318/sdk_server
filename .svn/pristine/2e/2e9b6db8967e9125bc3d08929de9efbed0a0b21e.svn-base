package com.dome.sdkserver.constants;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;


/**
 * 应用状态
 * @author hexiaoyi
 */
public enum AppStatusEnum {
	/**
	 * 一期的应用状态
	 */
//	NOT_CHECKIN(1,"未接入"),
//	
//	WAIT_CHECKIN(2,"待接入"),
//	
//	TEST(3,"测试"),
//	
//	REJECT(4,"已驳回"),
//	
//	WAIT_PUBLISH(5,"待上线"),
//	
//	PUBLISH(6,"已上线"),
	/**
	 * 未接入，已接入，测试环境联调中，测试环境联调通过，线上环境联调中，线上环境联调通过，测试中，测试通过，待上架，已上架
	 * 接入驳回，测试环境联调不通过，线上环境联调不通过，测试不通过，上架驳回
	 * 
	 * 测试中状态可以申请联调，回到已接入状态，重新进行测试环境联调、线上环境联调
	 */
	unaccess(10, "未接入"), wait_access(11, "待接入"), access_finish(12, "已接入"),
	test_adjust(13, "测试环境联调中"), test_adjust_finish(14, "测试环境联调通过"),
	online_adjust(15, "线上环境联调中"), online_adjust_finish(16, "线上环境联调通过"),
	test(17, "测试中"),  test_finish(18, "测试通过"),
	wait_shelf(19, "待上架"), shelf_finish(1, "已上架"), shelf_off(0, "已下架"), hidden(2, "展示下线"),
	/**
	 * 驳回状态的下一个状态为   驳回状态-30
	 * 测试环境和线上环境联调不通过状态都变为已接入
	 * 没有上架驳回状态
	 */
	deny_access(41, "已驳回"), /** deny_test_adjust(43, "测试环境联调不通过"), deny_online_adjust(45, "线上环境联调不通过"), **/
	deny_test(47, "测试不通过"), /**  deny_shelf(49, "上架驳回"), **/
	
	charge_changed(50, "测试中"), pkg_changed(51, "测试中");
	
	
	private int status;
	
	private String msg;
	
	private AppStatusEnum(int status, String msg) {
        this.status = status;
        this.msg = msg;
    }
	
    public static final AppStatusEnum getFromKey(int status) {
        return appStatusMap.get(status);
    }

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	public static final boolean inStatus(int status, AppStatusEnum... appStatusEnums){
		if (appStatusEnums == null || appStatusEnums.length == 0) return false;
		for (AppStatusEnum appStatusEnum : appStatusEnums){
			if (status == appStatusEnum.getStatus()) {
				return true;
			}
		}
		return false;
	}
	
	public static final boolean notInStatus(int status, AppStatusEnum... appStatusEnums){
		if (appStatusEnums == null || appStatusEnums.length == 0) return true;
		for (AppStatusEnum appStatusEnum : appStatusEnums){
			if (status == appStatusEnum.getStatus()) {
				return false;
			}
		}
		return true;
	}
	
	private static Map<Integer, AppStatusEnum> appStatusMap=new HashMap<Integer, AppStatusEnum>();
	static{
		
		for (AppStatusEnum en: AppStatusEnum.values()){
			appStatusMap.put(en.status, en);
		}
	}
	
	public static String getStatusDesc(int status){
		AppStatusEnum en=appStatusMap.get(status);
		return en!=null ? en.getMsg() :StringUtils.EMPTY;
	}
}
