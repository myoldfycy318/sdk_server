package com.dome.sdkserver.service.channel.impl;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.dome.sdkserver.autopack.AutoPackTask;
import com.dome.sdkserver.autopack.PackResult;
import com.dome.sdkserver.bo.Paginator;
import com.dome.sdkserver.constants.channel.PkgStatusEnum;
import com.dome.sdkserver.metadata.dao.mapper.ChannelPkgMapper;
import com.dome.sdkserver.metadata.entity.channel.AppPkg;
import com.dome.sdkserver.metadata.entity.channel.BqApp;
import com.dome.sdkserver.service.channel.ChannelPkgAuditService;

@Service
public class ChannelPkgAuditServiceImpl implements ChannelPkgAuditService{
	private Logger logger=LoggerFactory.getLogger("channelpkg");
	@Autowired
	private ChannelPkgMapper mapper;
	
	/**
	 * 线程池
	 * 渠道包APP后台打包审批，线程过多。多个线程同时上传文件会导致多FTP connection closed 421错误
	 */
	private ExecutorService pool = Executors.newFixedThreadPool(5);
	
	@Value("${channel.apppkg.ftp.workpath}")
	private String appPkgWorkPath;
	@Override
	public AppPkg selectAppPkg(long pid) {
		
		return mapper.selectAppPkgByPid(pid);
	}

	@Override
	public int updateAppPkg(AppPkg pkg) {
		
		logger.info("渠道包app申请被驳回，pid={}", pkg.getPid());
		return mapper.updateAppPkg(pkg);
	}

	@Override
	public int selectAppPkgCount(long channelId, String channelName) {
		
		return mapper.selectAppPkgCount(channelId, channelName);
	}

	@Override
	public List<AppPkg> selectAppPkgList(long channelId, String channelName,
			Paginator paginator) {
		
		return mapper.selectAppPkgList(channelId, channelName, paginator);
	}

	@Override
	public String pkg(final long pid) {
		// 先设置状态为正在打包
		final AppPkg ap = new AppPkg();
		ap.setPid(pid);
		ap.setStatus(PkgStatusEnum.正在打包.getStatus());
		mapper.updateAppPkg(ap);
		logger.info("渠道包app打包开始，pid={}", pid);
		Runnable pkgTask=new Runnable() {
			public void run() {
				// APP渠道包信息
				AppPkg appPkg = mapper.selectAppPkgByPid(pid);
				long appId = appPkg.getAppId();
				//冰趣官方上传APP的信息
				BqApp bqApp =mapper.selectBqApp(appId);
				String appCode=bqApp.getAppCode();
				String filePath=appPkgWorkPath;
				String channelCode=appPkg.getChannelCode();
				String downloadUrl=bqApp.getDownloadUrl();
				AutoPackTask packTask = new AutoPackTask(appCode, filePath,
						channelCode, downloadUrl, 1);
				FutureTask<PackResult> task = new FutureTask<>(packTask);
				new Thread(task).start();
				try {
					PackResult result=task.get(30, TimeUnit.MINUTES);
					// 打包结束，更新为已打包状态
					if (result.isSuccess()){
						ap.setStatus(PkgStatusEnum.已打包.getStatus());
						ap.setDownloadUrl(result.getDownloadUrl());
					} else {
						ap.setStatus(PkgStatusEnum.打包失败.getStatus());
					}
					
				} catch (Exception e) {
					logger.error("APP渠道打包出错，pid=" +pid +", appId=" +appId, e);
					// 超时后设置为打包失败
					ap.setStatus(PkgStatusEnum.打包失败.getStatus());
				}finally{
					mapper.updateAppPkg(ap);
				}
				
			}
		};
		pool.execute(pkgTask);
		return null;
	}

	@Override
	public String batchPkg(List<Long> pidList) {
		logger.info("开始批量APP渠道包打包，pidList:" +pidList);
		for (long pid : pidList){
			this.pkg(pid);
		}
		return null;
	}

	@Override
	public String receBqApp(BqApp app) {
		BqApp bqApp = new BqApp();
		long appId = app.getAppId();
		BqApp pastApp = mapper.selectBqApp(appId);
		if (pastApp!=null){
			// 前后的两次版本一致且下载url一致，不需要做处理
			if (pastApp.getVersion().equals(app.getVersion())
					&& pastApp.getDownloadUrl().equals(app.getDownloadUrl())) return "版本和下载地址没有变更，不需要处理";
			mapper.deleteBqApp(pastApp.getId()); // 根据主键id删除
		}
		bqApp.setAppId(appId);
		bqApp.setAppCode(String.format("A%07d", app.getAppId()));
		bqApp.setAppName(app.getAppName());
		bqApp.setVersion(app.getVersion());
		bqApp.setDownloadUrl(app.getDownloadUrl());
		mapper.addBqApp(bqApp);
		return null;
	}

}
