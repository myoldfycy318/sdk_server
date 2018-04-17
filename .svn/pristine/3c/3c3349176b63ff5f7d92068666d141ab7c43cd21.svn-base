package com.dome.sdkserver.metadata.entity.channel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.dome.sdkserver.constants.channel.PromoteTypeStatusEnum;

public class TypeVo {

	private long typeId;
	private String typeName;
	
	private List<Map<String, Object>> gameIds;
	
	private int status;
	
	private String statusDesc;

	public TypeVo() {
	}

	public TypeVo(PromoteType pt) {
		this.typeId=pt.getTypeId();
		this.typeName=pt.getTypeName();
		this.status=pt.getStatus();
		this.gameIds=pt.getGameMapList();
		initStatusDesc();
	}

	private void initStatusDesc() {
		this.statusDesc=PromoteTypeStatusEnum.getStatusDesc(status);
		
	}

	public long getTypeId() {
		return typeId;
	}

	public void setTypeId(long typeId) {
		this.typeId = typeId;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}


	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getStatusDesc() {
		return statusDesc;
	}

	public void setStatusDesc(String statusDesc) {
		this.statusDesc = statusDesc;
	}

	public List<Map<String, Object>> getGameIds() {
		return gameIds;
	}

	public void setGameIds(List<Map<String, Object>> gameIds) {
		this.gameIds = gameIds;
	}
	
	
}
