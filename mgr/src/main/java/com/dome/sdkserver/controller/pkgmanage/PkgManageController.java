package com.dome.sdkserver.controller.pkgmanage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dome.sdkserver.bo.MerchantAppInfo;
import com.dome.sdkserver.bo.Paginator;
import com.dome.sdkserver.bo.PkgVo;
import com.dome.sdkserver.bo.SearchMerchantAppBo;
import com.dome.sdkserver.bo.pkgmanage.Pkg;
import com.dome.sdkserver.controller.BaseController;
import com.dome.sdkserver.service.ChargePointService;
import com.dome.sdkserver.service.MerchantAppAuditService;
import com.dome.sdkserver.service.PkgManageService;
import com.dome.sdkserver.util.PaginatorUtils;
import com.dome.sdkserver.util.PropertiesUtil;
import com.dome.sdkserver.view.AjaxResult;
import com.dome.sdkserver.vo.MerchantAppVo;

@Controller
@RequestMapping("/pkgmanage")
public class PkgManageController extends BaseController{


    @Resource
	private PropertiesUtil domainConfig;
    
    @Resource
    private PkgManageService pkgManageServiceImpl;
    
    @Resource
	private MerchantAppAuditService merchantAppAuditServiceImpl;
    
    @Resource
	private ChargePointService chargePointServiceImpl;

//    /**
//     * 需要在mvc中加载pkg.properties，Controller类中才能获取到配置
//     */
//    @Value("${domesdk.pkg.download.domain}")
//    private String downloadDomain;
//    
//    @Value("${domesdk.pkg.upload.path}")
//    private String uploadPath;
	
    @RequestMapping("/view")
    @ResponseBody
    public AjaxResult queryPkg(String appCode){
    	Pkg p = pkgManageServiceImpl.query(appCode);
    	if (p == null) {
    		return AjaxResult.failed("应用没有上传过包体");
    	}
    	// 封装PkgVo
    	
    	return AjaxResult.success(generatePkgVo(p));
    }
    
    // 转换为视图层PkgVo对象
    private PkgVo generatePkgVo(Pkg pkg){
    	PkgVo vo = new PkgVo();
    	BeanUtils.copyProperties(pkg, vo);
    	vo.setDownloadUrl(pkg.getUploadPath());
    	return vo;
    }

    @RequestMapping("/history")
    @ResponseBody
    public AjaxResult queryHistory(String appCode, HttpServletRequest request){
    	
    	List<PkgVo> pkgList = pkgManageServiceImpl.queryHistory(appCode);
    	if (CollectionUtils.isEmpty(pkgList)) {
    		return AjaxResult.failed("没有查询到包体上传记录");
    		
    	}
    	// 使用假分页
		int pageSize = getPageSize(request);
		int count = pkgList.size();
		PaginatorUtils paginatorUtils = new PaginatorUtils(pageSize);
		int start = PaginatorUtils.getStart(paginatorUtils.executePage(request, count));
		List<PkgVo> newPkgList = pkgList.subList(start, ((start + pageSize)> count ? count : start + pageSize));
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("totalCount", count);
		dataMap.put("pkgList", newPkgList);
    	return AjaxResult.success(dataMap);
    }
    
    /**
     * 仅展示已上传过包体的应用
     * @return
     */
    @RequestMapping("/appList")
    @ResponseBody
    public AjaxResult appList(SearchMerchantAppBo searchMerchantAppBo, Paginator paginator,
    		HttpServletRequest request){
    	int count = pkgManageServiceImpl.getAppInfoCountByCondition(searchMerchantAppBo);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		List<MerchantAppVo> merchantAppList = new ArrayList<MerchantAppVo>();
		if (count > 0) {
			paginator=Paginator.handlePage(paginator, count, request);
			searchMerchantAppBo.setStart(paginator.getStart());
			searchMerchantAppBo.setSize(paginator.getPageSize());
			List<MerchantAppInfo> list = pkgManageServiceImpl.getAppInfoByCondition(searchMerchantAppBo);
			for (MerchantAppInfo app : list){
				
				Pkg pkg = pkgManageServiceImpl.query(app.getAppCode());
				MerchantAppVo appVo = new MerchantAppVo(app, pkg);
				merchantAppList.add(appVo);
			}
			
		}
		dataMap.put("totalCount", count);
		dataMap.put("appList", merchantAppList);
		return AjaxResult.success(dataMap);
    }
}
