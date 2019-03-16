package cn.appsys.contorller.backend;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.appsys.pojo.AppCategory;
import cn.appsys.pojo.AppInfo;
import cn.appsys.pojo.AppVersion;
import cn.appsys.pojo.DataDictionary;
import cn.appsys.service.AppInfo.AppService;
import cn.appsys.service.AppInfo.CategoryService;
import cn.appsys.service.AppInfo.DicTionAryService;
import cn.appsys.service.AppInfo.VerSionService;
import cn.appsys.tools.Constants;
import cn.appsys.tools.PageSupport;

@Controller
@RequestMapping("/backApp")
public class backAppInfo {
	
	//版本信息接口
	@Resource(name="verSionService")
    private VerSionService vs;
	
	
	//App接口
	@Resource(name="appService")
	private AppService as;
	
	//数据字典接口
	@Resource(name="dicTionAryService")
	private DicTionAryService ds;
	
	//软件类型
	@Resource(name="categoryService")
	private CategoryService cs;
	
	/**
	 * 审核查看
	 * @param appId
	 * @param versionId
	 * @param model
	 * @return
	 */
	@RequestMapping("/check.html")
	public String check(@RequestParam(value="aid",required=false) String appId,
			   @RequestParam(value="vid",required=false) String versionId,
			   Model model){
		AppInfo ai =as.getApp(Integer.parseInt(appId));
		
		AppVersion av=vs.getVersion(Integer.parseInt(versionId));
		
		model.addAttribute("appInfo", ai);
		model.addAttribute("appVersion", av);
		return "backend/appcheck";
	}
	
	@RequestMapping("/checksave.html")
	public String checksave(AppInfo app){
		
		if(!as.upAppStatus(app.getStatus(), app.getId())){
			
		}
		
		return "redirect:getAppList.html";
	}
	
	
	/**
	 * 查询后台APP集合页面
	 * @param request 
	 * @param pageIndex 当前页面
	 * @param querySoftwareName 项目名称（模糊查询）
	 * @param _queryCategoryLevel1 一级分类
	 * @param _queryCategoryLevel2二级分类
	 * @param _queryCategoryLevel3三级分类
	 * @param _queryFlatformId
	 * @return
	 */
	@RequestMapping(value="/getAppList.html")
	public String getAppList(HttpServletRequest request,
			@RequestParam(value="pageIndex",required=false) String pageIndex,
			@RequestParam(value="querySoftwareName",required=false) String querySoftwareName,
			@RequestParam(value="queryCategoryLevel1",required=false) String _queryCategoryLevel1,
			@RequestParam(value="queryCategoryLevel2",required=false) String _queryCategoryLevel2,
			@RequestParam(value="queryCategoryLevel3",required=false) String _queryCategoryLevel3,
			@RequestParam(value="queryFlatformId",required=false) String _queryFlatformId
			){
		
		//转换类型
		Integer currPageNo=1;
		Integer queryCategoryLevel1=null;
		Integer queryCategoryLevel2=null;
		Integer queryCategoryLevel3=null;
		Integer queryFlatformId=null;
		
		if(_queryFlatformId!=null&&!_queryFlatformId.equals("")){
			queryFlatformId=Integer.parseInt(_queryFlatformId);
		}
		if(_queryCategoryLevel1!=null&&!_queryCategoryLevel1.equals("")){
			queryCategoryLevel1=Integer.parseInt(_queryCategoryLevel1);
		}
		if(_queryCategoryLevel2!=null&&!_queryCategoryLevel2.equals("")){
			queryCategoryLevel2=Integer.parseInt(_queryCategoryLevel2);
		}
		if(_queryCategoryLevel3!=null&&!_queryCategoryLevel3.equals("")){
			queryCategoryLevel3=Integer.parseInt(_queryCategoryLevel3);
		}
		if(pageIndex!=null&&!pageIndex.equals("")){
			currPageNo=Integer.parseInt(pageIndex);
		}

		int pageSize=Constants.pageSize;
		
		//获得总数量
		int pageCount=0;
		try {
			pageCount=as.getAppInfoCount(querySoftwareName, 1, queryCategoryLevel1, queryCategoryLevel2, queryCategoryLevel3, queryFlatformId,null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//分页
		PageSupport page=new PageSupport();
		page.setCurrentPageNo(currPageNo);
		page.setPageSize(pageSize);
		page.setTotalCount(pageCount);
		
		
		//集合
		List<AppInfo> appList=null;
		List<DataDictionary> dicList=null;
		List<AppCategory> cateList=null;
		List<AppCategory> cateList2=null;
		List<AppCategory> cateList3=null;
		
		//所属平台集合
		dicList=ds.getDicList("APP_FLATFORM");
		
		//应用类别
		if (queryCategoryLevel2!=null) {
			cateList2=cs.getCateList(queryCategoryLevel1);
		}
        if (queryCategoryLevel3!=null) {
        	cateList3=cs.getCateList(queryCategoryLevel2);
		}
		cateList=cs.getCateList(null);
		
		try {
	    appList=as.getInfoList(querySoftwareName, 1, queryCategoryLevel1,
					queryCategoryLevel2, queryCategoryLevel3, queryFlatformId,
					null, (page.getCurrentPageNo()-1)*page.getPageSize(),page.getPageSize());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		request.setAttribute("querySoftwareName", querySoftwareName);
		request.setAttribute("queryFlatformId", queryFlatformId);
		request.setAttribute("queryCategoryLevel1", queryCategoryLevel1);
		request.setAttribute("queryCategoryLevel2", queryCategoryLevel2);
		request.setAttribute("queryCategoryLevel3", queryCategoryLevel3);
		request.setAttribute("appInfoList", appList);
		request.setAttribute("pages", page);
		request.setAttribute("flatFormList", dicList);
		request.setAttribute("categoryLevel1List", cateList);
		request.setAttribute("categoryLevel2List", cateList2);
		request.setAttribute("categoryLevel3List", cateList3);
		return "backend/applist";
	}
	
	/**
	 * 按照父类查询类型子集
	 * @param pid
	 * @return
	 */
	@RequestMapping("/categorylevellist.json")
	@ResponseBody
	public List<AppCategory> getcategorylevellist(@RequestParam(value="pid",required=false)String pid){
		
		Integer parentId=Integer.parseInt(pid);
		
		List<AppCategory> cateList=cs.getCateList(parentId);
		
		return cateList;
	}
	
	
}
