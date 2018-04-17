package com.dome.sdkserver.bo;

import java.util.List;

/**
 * 用户中心显示对象
 * 
 * @author Frank.Zhou
 *
 */
public class UserCenter {
	
	private int count;
	
	private List<UserApp> list;

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public List<UserApp> getList() {
		return list;
	}

	public void setList(List<UserApp> list) {
		this.list = list;
	}

}
