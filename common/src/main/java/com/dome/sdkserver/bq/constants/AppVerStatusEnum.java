package com.dome.sdkserver.bq.constants;

/**
 * 应用的每个版本可以设置一个状态
 * @author lilongwei
 *
 */
public enum AppVerStatusEnum {

	status_notconfig(0, "未配置"), status_online(10, "上线"), status_audit(20, "审核"), status_test(30, "测试");
	
	public int status = 0;
	
	public String desc;
	
	private AppVerStatusEnum(int status, String desc){
		this.status = status;
		this.desc = desc;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	
    public static final AppVerStatusEnum getFromKey(int status) {
        for (AppVerStatusEnum e : AppVerStatusEnum.values()) {
            if (e.status == status) {
                return e;
            }
        }
        return null;
    }

}
