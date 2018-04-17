package com.dome.sdkserver.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import net.dongliu.apk.parser.ApkParser;
import net.dongliu.apk.parser.bean.ApkMeta;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.dome.sdkserver.bo.MerchantAppInfo;
import com.dome.sdkserver.bo.pkgmanage.Pkg;
import com.dome.sdkserver.constants.AppStatusEnum;
import com.dome.sdkserver.constants.AppTypeConstant;
import com.dome.sdkserver.metadata.dao.mapper.AppOperRecordMapper;
import com.dome.sdkserver.metadata.dao.mapper.MerchantAppMapper;
import com.dome.sdkserver.metadata.dao.mapper.PkgMapper;
import com.dome.sdkserver.metadata.entity.AppOperRecord;
import com.dome.sdkserver.service.PkgService;

import static com.dome.sdkserver.util.MyApkParser.getSdkVersion;;

@Service
public class PkgServiceImpl implements PkgService {
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Resource
	private PkgMapper pkgMapper;
	
	@Resource
	private MerchantAppMapper merchantAppMapper;
	
	@Autowired
	private AppOperRecordMapper appOperRecordMapper;
	@Override
	@Transactional
	public void upload(MerchantAppInfo app, Pkg p) {
		List<Pkg> pkgList = pkgMapper.queryHistory(p.getAppCode());
		// 变更包体时需要修改应用状态
		boolean changedPkg = false;
		if (!pkgList.isEmpty()) {
			changedPkg = true;
			
		}
		p.setUploadStatus(1);
		// 暂不提供加固
		p.setJiaguStatus(1);
		p.setJiaguTime(new Date());
		p.setJiaguFileSize(p.getFileSize());
		p.setJiaguPath(p.getUploadPath());
		String apkFile = p.getPhysicalFilePath();
		// 安卓安装包会解析
		if (StringUtils.isNotEmpty(p.getFileName()) && p.getFileName().toLowerCase().endsWith(".apk")) {
			p.setSdkVersion(getSdkVersion(new File(apkFile)));
			initPkgApkMeta(p, apkFile);
		}
		
		pkgMapper.upload(p);
		AppOperRecord record=new AppOperRecord();
		record.setAppId(app.getAppId());
		record.setStatus(app.getStatus());
		record.setOperUserId(app.getOperUserId());
		record.setOperUser(app.getOperUser());
		// 上传成功后开始包体加固
		//pkgMapper.jiagu(p);
		// 加固完成后再修改应用状态，且应用未游戏
		if (changedPkg) {
			int newStatus = -1;
			int status = app.getStatus();
			if (AppTypeConstant.APP_TYPE_GAME.equals(app.getAppType())){
				/**
				 * 修改应用状态
				 * 1、未上架      已接入
				 * 若应用已上架，修改为包体变更的测试中
				 */
				if (status == AppStatusEnum.shelf_finish.getStatus()) { // 已上架
					newStatus = AppStatusEnum.charge_changed.getStatus(); // 合并包体变更和计费点变更后的应用状态，都可以申请联调
				} else if (status != AppStatusEnum.access_finish.getStatus()){
					newStatus = AppStatusEnum.access_finish.getStatus();
				}
			} else { // 除了游戏，网站应用或移动应用状态由已上架修改为待上架
				if (status == AppStatusEnum.shelf_finish.getStatus()) {
					newStatus = AppStatusEnum.wait_shelf.getStatus();
				}
				
			}
			
			if (newStatus != -1) {
				merchantAppMapper.updateAppStatus(app.getAppId(), newStatus);
			}
			record.setOperDesc("变更包体");
		} else {
			record.setOperDesc("上传包体");
		}
		appOperRecordMapper.insert(record);
	}

	@Override
	public void jiagu(Pkg p) {
		// TODO Auto-generated method stub

	}

	@Override
	public Pkg query(String appCode) {
		List<Pkg> pkgList = pkgMapper.query(appCode);
		if (!CollectionUtils.isEmpty(pkgList)) {
			Pkg p = pkgList.get(0);
			//p.setId(0l);
			// 最新的上传包体记录中没有发现有加固，取上一次加固的数据
			if ((p.getJiaguPath() == null || "".equals(p.getJiaguPath())) && pkgList.size() >= 2) {
				Pkg p2 = pkgList.get(1);
				if (!StringUtils.isEmpty(p2.getJiaguPath()) && p2.getJiaguStatus() == 1) { // 加固成功，上一条上传包体记录中若仍没有发现有加固，就放弃继续查找
					p.setJiaguPath(p2.getJiaguPath());
					p.setJiaguTime(p2.getJiaguTime());
					p.setSdkVersion(p2.getSdkVersion());
					p.setId(p2.getId());
				}
			}
			return p;
		}
		return null;
	}

	@Override
	public List<Pkg> queryHistory(String appCode) {
		
		return null;
	}

	@Override
	public Pkg queryById(long id) {
		
		return pkgMapper.queryById(id);
	}

	@Override
	public Pkg queryNew(String appCode) {
		
		return pkgMapper.queryNew(appCode);
	}

//	/**
//	 * 获取冰穹sdk版本号
//	 * buildConfig文件格式
//	 *  <SdkConfig>
//     *    <ChannelId>CHA000002</ChannelId>
//	 *    <SdkVersion>10001</SdkVersion>
//     *  </SdkConfig>
//	 * @param filePath
//	 * @return
//	 */
//	private String getSdkVersion(String apkFile){
//		String sdkVersion = null;
//		MyApkParser parser = null;
//		try {
//			parser = new MyApkParser(apkFile);
//			String xml = parser.getSdkXml();
//			//log.debug("buildConfig.xml content: " + xml);
//			if (!StringUtils.isEmpty(xml)) {
//				Document doc = DocumentHelper.parseText(xml);
//				List nodeList = doc.selectNodes("SdkVersion");
//				if (!CollectionUtils.isEmpty(nodeList)) {
//					for (Object obj : nodeList) {
//						Element ele = (Element) obj;
//						sdkVersion = ele.getText();
//						log.info("sdkversion: {}", sdkVersion);
//					}
//				}
//				
//			}
//		} catch (Exception e) {
//			log.error("获取应用的sdkversion出错", e);
//		} finally {
//			IOUtils.closeQuietly(parser);
//		}
//		return sdkVersion;
//		
//	}
	
	private void initPkgApkMeta(Pkg pkg, String apkFile){
		ApkParser parser = null;
		try {
			parser = new ApkParser(apkFile);
			ApkMeta meta = parser.getApkMeta();
			log.info("应用{}的ApkMeta:{}", pkg.getAppCode(), meta);
			pkg.setPackageName(meta.getPackageName());
			// 软件版本号
			pkg.setVersion(meta.getVersionName());
		} catch (IOException e) {
			log.error("设置应用包体的包体名称和软件版本号信息出错", e);
		} finally {
			IOUtils.closeQuietly(parser);
		}
		
	}
}
