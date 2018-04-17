package com.dome.sdkserver.bo;

import com.dome.sdkserver.util.MathUtils;



/**
 * 
 * 创建人：<br>
 * 创建时间：2013-12-6 <br>
 * 功能描述： 文件上传进度<br>
 */
public class FileUpdProgress {

	/** 已读字节 **/
	private long bytesRead = 0L;
	/** 已读MB **/
	private String mbRead = "0";
	/** 总长度 **/
	private long contentLength = 0L;
	/****/
	private int items;
	/** 已读百分比 **/
	private String percent;
	/** 读取速度 **/
	private String speed;
	/** 开始读取的时间 **/
	private long startReatTime = System.currentTimeMillis();

	public long getBytesRead() {
		return bytesRead;
	}

	public void setBytesRead(long bytesRead) {
		this.bytesRead = bytesRead;
	}

	public long getContentLength() {
		return contentLength;
	}

	public void setContentLength(long contentLength) {
		this.contentLength = contentLength;
	}

	public int getItems() {
		return items;
	}

	public void setItems(int items) {
		this.items = items;
	}

	public String getPercent() {
		double d = MathUtils.divideExact(bytesRead, contentLength);
		return MathUtils.percentStr(d);
	}

	public void setPercent(String percent) {
		this.percent = percent;
	}

	public String getSpeed() {
		speed = MathUtils.divideExact(bytesRead, System.currentTimeMillis()- startReatTime) + "";
		return speed;
	}

	public void setSpeed(String speed) {
		this.speed = speed;
	}

	public long getStartReatTime() {
		return startReatTime;
	}

	public void setStartReatTime(long startReatTime) {
		this.startReatTime = startReatTime;
	}

	public String getMbRead() {
		mbRead = MathUtils.divideExact(bytesRead, 1000000) + "";
		return mbRead;
	}

	public void setMbRead(String mbRead) {
		this.mbRead = mbRead;
	}

	@Override
	public String toString() {
		return "FileUpdProgress [bytesRead=" + bytesRead + ", mbRead=" + mbRead
				+ ", contentLength=" + contentLength + ", items=" + items
				+ ", percent=" + percent + ", speed=" + speed
				+ ", startReatTime=" + startReatTime + "]";
	}


}