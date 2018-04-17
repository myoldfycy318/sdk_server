package com.dome.sdkserver.constants.channel;

import java.util.HashMap;
import java.util.Map;

/**
 * 渠道包状态
 * @author lilongwei
 *
 */
public enum PkgStatusEnum {
	/**
	 * 渠道包游戏打包状态只有1,3,4,6
	 */
	首次申请打包(0), 已打包(1),未打包(2), 正在打包(3), 打包失败(4), 驳回(5), 包有更新(6);
	
	private int status;
	private PkgStatusEnum(int status){
		this.status=status;
	}
	
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	private static Map<Integer, String> pkgStatusMap=new HashMap<Integer, String>();
	static{
		
		for (PkgStatusEnum en: PkgStatusEnum.values()){
			pkgStatusMap.put(en.status, en.name());
		}
	}
	/**
	 * 是否可以申请打包
	 * @param code
	 * @return
	 */
	public static boolean canPkg(int code){
		
		return !(code==1 || code==3);
	}
	
	public static String getStatusDesc(int status){
		return pkgStatusMap.get(status);
	}
}
