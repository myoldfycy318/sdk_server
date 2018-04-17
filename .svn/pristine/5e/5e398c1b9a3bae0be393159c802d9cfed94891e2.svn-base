package com.dome.sdkserver.constants;

import java.util.HashMap;
import java.util.Map;

public enum AppOperEnum {
	
	/**
	 * 操作status为操作前的状态拼上操作后的状态
	 */
	apply_access("1011", "申请接入"), agree_access("1112", "应用审批通过"),
	apply_test_adjust("1213", "测试环境申请联调"), agree_test_adjust("1314", "测试环境联调通过"),
	apply_online_adjust("1415", "线上环境联调申请"), agree_online_adjust("1516", "线上环境联调通过"),
	apply_test("1617", "测试申请"), agree_test("1718", "测试通过"),
	apply_shelf("1819", "上架申请"), agree_shelf("191", "上架通过"),
	deny_access("1141", "接入驳回"), deny_test_adjust("1312", "测试环境联调不通过"),
	deny_online_adjust("1512", "线上环境联调不通过"), deny_test("1747", "测试不通过"),
	/** deny_shelf("1949", "上架驳回"), **/
	shelf_off("10", "下架"), hidden("12", "展示下线"), show("21", "展示上线"),
	/**
	 * 网站应用和移动应用接入后状态变为待上架
	 */
	webapp_apply_access("1119", "应用审批通过"), deny_apply_access("4111", "申请接入"), deny_aplly_test("4717", "测试申请"),
	shelf_on("01", "上架"),
	chargechange_apply_test_adjust("5013", "测试环境申请联调"), pkgchange_apply_test_adjust("5113", "测试环境申请联调"),
	chargechange_test_finish("5018", "测试通过"), pkgchange_test_finish("5118", "测试通过"),
	chargechange_test_deny("5047", "测试不通过"), pkgchange_test_deny("5147", "测试不通过"),;
	
	private String status;
	
	private String desc;
	
	private AppOperEnum(String status, String desc){
		this.status = status;
		this.desc = desc;
	}
	
    public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public static final AppOperEnum getFromKey(String status) {
        for (AppOperEnum e : AppOperEnum.values()) {
            if (e.getStatus().equals(status)) {
                return e;
            }
        }
        return null;
    }
	
	private static Map<String, String> operDescMap=new HashMap<String, String>();
	static{
		
		for (AppOperEnum en: AppOperEnum.values()){
			operDescMap.put(en.status, en.getDesc());
		}
	}
	
	public static String getStatusDesc(String status){
		return operDescMap.get(status);
	}
}