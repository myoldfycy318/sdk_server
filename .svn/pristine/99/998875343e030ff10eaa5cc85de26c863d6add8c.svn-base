package com.dome.sdkserver.service.channel.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
import com.dome.sdkserver.autopack.FtpConfig;
import com.dome.sdkserver.autopack.PackResult;
import com.dome.sdkserver.bo.MerchantAppInfo;
import com.dome.sdkserver.bo.Paginator;
import com.dome.sdkserver.bo.pkgmanage.Pkg;
import com.dome.sdkserver.constants.channel.PkgStatusEnum;
import com.dome.sdkserver.metadata.dao.mapper.ChannelPkgMapper;
import com.dome.sdkserver.metadata.dao.mapper.channel.SecondChannelMapper;
import com.dome.sdkserver.metadata.entity.channel.AppPkg;
import com.dome.sdkserver.metadata.entity.channel.BqApp;
import com.dome.sdkserver.metadata.entity.channel.FirstChannel;
import com.dome.sdkserver.metadata.entity.channel.Game;
import com.dome.sdkserver.service.channel.ChannelPkgService;

import static com.dome.sdkserver.service.channel.ChannelService.CHANNEL_CODE_PREFIX;
/**
 * 业务分析：
 * 1、游戏（应用）有过上架记录，且状态不是下架。
 * 2、比较apk版本号和是否有新上传记录
 * 3、应用为上架状态时，才允许申请打包
 * 4、包是否有更新
 * （1）应用为上架状态，update_time大于打包时间
 * （2）包体上传时间大于打包时间
 * @author lilongwei
 *
 */
@Service
public class ChannelPkgServiceImpl implements ChannelPkgService {
	private static Logger logger = LoggerFactory.getLogger("channelpkg");
	@Autowired
	private ChannelPkgMapper mapper;
	
	/**
	 * 渠道包app的appid，目前为冰趣
	 */
	@Value("${channel.apppkg.appid}")
	private long appId;
	
	@Value("${channel.apppkg.appName}")
	private String appName;
	/**
	 * 线程池
	 */
	private ExecutorService pool = Executors.newFixedThreadPool(10);

	@Override
	public int selectGameCount(String gameName, long channelId) {
		
		return mapper.selectGameCount(gameName, channelId);
	}
	
	@Override
	public List<Game> selectGameList(String gameName, long channelId, Paginator paginator) {
		List<Game> list =mapper.selectGameList(gameName, channelId, paginator);
		for (Game g: list){
			if (g.getStatus()==0){ // || StringUtils.isBlank(g.getDownloadUrl())
				g.setStatus(PkgStatusEnum.未打包.getStatus());
			}
			// 已打包情况，查看是否有包更新
			if (g.getStatus()==PkgStatusEnum.已打包.getStatus()){
				long appId = g.getGameId();
				// 应用为上架时，才需要判断包是否有更新
				MerchantAppInfo app=mapper.selectApp(appId);
				if (app!=null){
					Date shelfTime = app.getUpdateTime();
					long pkgTime = g.getUpdateTime().getTime();
					// 应用上架的时间晚于打包时间  和包上传的时间大于打包时间
					// 暂时没有通过包体版本号比较，因为合作方上传的包体版本号存在数据不准确的问题
					if (shelfTime.getTime()>pkgTime){
						Pkg pkg = mapper.selectPkg(app.getAppCode());
						if (pkg.getJiaguTime().getTime()>pkgTime){
							g.setStatus(PkgStatusEnum.包有更新.getStatus());
							// 更新渠道包下载表状态，下次查询节省时间
							mapper.updateGamePkgStatus(channelId, appId, PkgStatusEnum.包有更新.getStatus());
						}
					}
				}
			}
		}
		return list;
	}

	@Override
	public int selectDlGameCount(String gameName, long channelId) {
		
		return mapper.selectDlGameCount(gameName, channelId);
	}
	
	@Override
	public List<Game> selectDlGameList(String gameName, long channelId, Paginator paginator) {
		
		return mapper.selectDlGameList(gameName, channelId, paginator);
	}

	@Override
	public String addAppPkg(AppPkg pkg) {
		// 没有申请过打包情况下可以打包
		long appId=this.appId;
		BqApp bqApp = mapper.selectBqApp(appId);
		if (bqApp==null){
			return "APP包体不存在，还没有开放申请打包";
		}
		AppPkg p = mapper.selectAppPkg(pkg.getChannelId(), appId);
		if (p != null){
			int status = p.getStatus();
			if (status==PkgStatusEnum.未打包.getStatus() ||
					status==PkgStatusEnum.正在打包.getStatus()){
				return "已申请过打包，不允许重复申请。";
			}
			if (status==PkgStatusEnum.已打包.getStatus()){
				
				if (bqApp.getCreateTime().getTime() <p.getUpdateTime().getTime()){
					return "打包已完成，不需要重复申请。";
				} else {
					// 包有更新
				}
			}
			// 上次打包地址仍可以下载，只更新状态，不重置download_url列
			pkg.setStatus(PkgStatusEnum.未打包.getStatus());
			pkg.setPid(p.getPid());
			mapper.updateAppPkg(pkg);
		} else {
			String appName=bqApp.getAppName();
			pkg.setAppName(appName);
			pkg.setStatus(PkgStatusEnum.未打包.getStatus());
			pkg.setAppId(this.appId);
			mapper.addAppPkg(pkg);
		}
		
		return null;
	}

	@Override
	public AppPkg selectAppPkg(long channelId) {
		
		final AppPkg selectAppPkg = mapper.selectAppPkg(channelId, this.appId);
		// 查询包是否有更新
		
		if (selectAppPkg!=null && selectAppPkg.getStatus()==PkgStatusEnum.已打包.getStatus()){
			BqApp bqApp = mapper.selectBqApp(this.appId);
			if (bqApp==null){ // 官方包还没有上传，设置为一种驳回状态
				
			} else {
				if (bqApp.getUpdateTime().getTime()>selectAppPkg.getUpdateTime().getTime()){
					// 包有更新时强制要求重新申请打包
					selectAppPkg.setStatus(PkgStatusEnum.包有更新.getStatus());
					selectAppPkg.setDownloadUrl(null);
					mapper.updateAppPkg(selectAppPkg);
				}
			}
		}
		return selectAppPkg;
	}

	/**
	 * 一个渠道可能关联多个推广分类
	 * 若两个推广分类中都包含同一款游戏，则一个分类下的打包，会将另一个分类的下载地址、状态给填充上。
	 */
	@Override
	public String gamePkg(long channelId, long[] gameIds) {
		
		Paginator paginator = new Paginator();
		paginator.setStart(-1);
		// 校验gameId是渠道下面的游戏
		List<Game> list =mapper.selectGameList(null, channelId, paginator);
		Set<Long> gameIdSet =new HashSet<>(list.size());
		for (Game g : list){
			gameIdSet.add(g.getGameId());
		}
		List<Game> needPkgList = new ArrayList<>();
		for (long gameId : gameIds){
			if (!gameIdSet.contains(gameId)){
				logger.error("渠道channelId={}，打算申请打包游戏appId={}被拒绝",channelId,gameId);
				needPkgList.clear();
				return "Operation is rejected";
			}
			// 若该游戏正在打包，拒绝请求
			Game game = new Game();
			game.setGameId(gameId);
			MerchantAppInfo app = mapper.selectApp(gameId);
//			// 游戏上架，可以保证已上传包体
//			if (app==null){ //app.getStatus()!=AppStatusEnum.shelf_finish.getStatus()
//				return "游戏gameId="+gameId +"还没有上架，不允许申请打包";
//			}
			game.setGameCode(app.getAppCode());
			// 使用limit 1只查询一条记录，未打包记录靠前
			Game g = mapper.selectGamePkg(channelId, gameId);
			if (g!=null){
				int status = g.getStatus();
				// 查询列表时已分析出包体是否有更新
				if (status==PkgStatusEnum.已打包.getStatus() || status==PkgStatusEnum.正在打包.getStatus()){
					needPkgList.clear();
					return "游戏："+app.getAppName() +"已打过包或正在打包中，不允许重复申请";
				}
				// 做为判断是否为首次申请
				/**
				 * 每次申请先删除过去的记录，保留打包历史
				 */
				//game.setId(g.getId());
				mapper.deleteGamePkg(g.getId());
			}
			
			
			needPkgList.add(game);
		}
		logger.info("渠道包游戏申请打包，gameIds=" +Arrays.toString(gameIds));
		for (Game g :needPkgList){
			doSingleGamePkg(channelId, g);
		}
		return null;
	}

	@Autowired
	private SecondChannelMapper channelMapper;
	
	private void doSingleGamePkg(final long channelId, final Game g){
		// 先添加记录，设置为正在打包
		logger.info("渠道包游戏开始打包,channelId={}, gameId={}", channelId, g.getGameId());
		// 设置为正在打包状态会自动重置download_url列
		
		g.setStatus(PkgStatusEnum.正在打包.getStatus());
		g.setChannelId(channelId);
		mapper.addGamePkg(g);
		final long appId = g.getGameId();
		Runnable pkgTask = new Runnable() {
			
			@Override
			public void run() {
				Pkg pkg = mapper.selectPkg(g.getGameCode());
				String appCode=pkg.getAppCode();
				String filePath=FtpConfig.getPkgUploadPath();
				FirstChannel fChannel = channelMapper.select(channelId, 0);
				String channelCode=fChannel.getChannelCode();//String.format(CHANNEL_CODE_PREFIX + "%07d", channelId);
				String downloadUrl=pkg.getJiaguPath();
				AutoPackTask packTask = new AutoPackTask(appCode, filePath,
						channelCode, downloadUrl, 2);
				FutureTask<PackResult> task = new FutureTask<>(packTask);
				new Thread(task).start();
				try {
					PackResult result=task.get(30, TimeUnit.MINUTES);
					// 打包结束，更新为已打包状态
					if (result.isSuccess()){
						g.setStatus(PkgStatusEnum.已打包.getStatus());
						g.setDownloadUrl(result.getDownloadUrl());
					} else {
						g.setStatus(PkgStatusEnum.打包失败.getStatus());
					}
					//mapper.updateGamePkg(g);
				} catch (Exception e) {
					logger.error("渠道包游戏打包出错，channelId=" +channelId +", appId=" +appId, e);
					// 超时后设置为打包失败
					g.setStatus(PkgStatusEnum.打包失败.getStatus());
				}finally {
					mapper.updateGamePkg(g);
				}
				
			}
		};
		pool.execute(pkgTask);
	}

}
