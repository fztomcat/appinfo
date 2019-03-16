package cn.appsys.contorller.developer;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;


import cn.appsys.pojo.AppCategory;
import cn.appsys.pojo.AppInfo;
import cn.appsys.pojo.AppVersion;
import cn.appsys.pojo.DataDictionary;
import cn.appsys.pojo.DevUser;
import cn.appsys.service.AppInfo.AppService;
import cn.appsys.service.AppInfo.CategoryService;
import cn.appsys.service.AppInfo.DicTionAryService;
import cn.appsys.service.AppInfo.VerSionService;
import cn.appsys.tools.Constants;
import cn.appsys.tools.PageSupport;


@Controller
@RequestMapping("/devApp")
public class devAppInfo {

	    //App接口
		@Resource(name="appService")
		private AppService as;
	
	    //数据字典接口
		@Resource(name="dicTionAryService")
		private DicTionAryService ds;
		
		//软件类型
		@Resource(name="categoryService")
		private CategoryService cs;
		
		//版本信息
		@Resource(name="verSionService")
		private VerSionService vs;
		
		/*
		 * 删除APP及其版本
		 */
		@RequestMapping("/delapp.json")
		@ResponseBody
		public Object delApp(@RequestParam(value="id",required=false)String id){
			
			Map<String, String> map=new HashMap<String, String>();
			
			
			if(as.del(Integer.parseInt(id))>0&&vs.delVersion(Integer.parseInt(id))>0){
				map.put("delResult", "true");
				return map;
			}
			map.put("delResult", "false");
			return map;
		}
		
		/*
		 * 修改版本
		 */
		@RequestMapping("/appversionmodifysave")
		public String appinfomodifysave(AppVersion appVersion){
			
			if(vs.upVersion(appVersion)>0){
				
				return "redirect:devList";
			}
			
			return "redirect:appversionmodify";
		}
		
		
		/*
		 * 修改版本跳转
		 */
		@RequestMapping("/appversionmodify")
		public String appversionmodify(Model model,
				@RequestParam(value="vid",required=false)String vid,
				@RequestParam(value="aid",required=false)String aid){
			
            List<AppVersion> vlist=vs.getVersionList(Integer.parseInt(aid));
            AppVersion appversion=vs.getVersion(Integer.parseInt(vid));
            
            model.addAttribute("appVersionList", vlist);
			model.addAttribute("appVersion", appversion);
			
			
			return "developer/appversionmodify";
		}
		
		
		/*
		 * 修改APP
		 */
		@RequestMapping("/appinfomodifysave")
		public String appinfomodifysave(AppInfo appInfo){
			
			if(as.upApp(appInfo)>0){
				return "redirect:devList";
			}
			
			return "";
		}
       
		/*
		 * app修改页面
		 */
		@RequestMapping("/appinfomodify")
		public String appinfomodify(
				@RequestParam(value="id",required=false) String id,
				Model model){
			
			AppInfo appinfo=as.getApp(Integer.parseInt(id));
			
			model.addAttribute("appInfo", appinfo);
			
			return "developer/appinfomodify";
		}
		
		
		/*
		 * 显示版本
		 * 跳转新增版本页面
		 */
		@RequestMapping("/appversionadd")
		public String appversionadd(AppVersion appVersion,Model model,
				@RequestParam("id")String id){
			List<AppVersion> vlist=vs.getVersionList(Integer.parseInt(id));
	        
			model.addAttribute("appVersionList", vlist);
			appVersion.setAppId(Integer.parseInt(id));
			return "developer/appversionadd";
		}
		
		
		/*
		 * 跳转新增APP页面
		 */
		@RequestMapping("/appinfoadd")
		public String addAppViwe(AppInfo appinfo,Model model){
			
			List<DataDictionary> dicList=ds.getDicList("APP_FLATFORM");
			List<AppCategory> cateList=cs.getCateList(null);
			model.addAttribute("categoryLevel1List", cateList);
			model.addAttribute("flatFormList", dicList);
			return "developer/appinfoadd";
		}
		/*
		 * 新增APP
		 */
		@RequestMapping("/appinfoaddsave")
		public String addApp(AppInfo appInfo,HttpSession session,HttpServletRequest request,
				@RequestParam(value="a_logoPicPath",required=false)MultipartFile attach){
			String logoPicPath =  null;
			String logoLocPath =  null;
			if(!attach.isEmpty()){
				String path = request.getSession().getServletContext().getRealPath("statics"+File.separator+"uploadfiles");
				String oldFileName = attach.getOriginalFilename();//原文件名
				String prefix = FilenameUtils.getExtension(oldFileName);//原文件后缀
				int filesize = 500000;
				if(attach.getSize() > filesize){//上传大小不得超过 50k
					request.setAttribute("fileUploadError", Constants.FILEUPLOAD_ERROR_4);
					return "developer/appinfoadd";
	            }else if(prefix.equalsIgnoreCase("jpg") || prefix.equalsIgnoreCase("png") 
				   ||prefix.equalsIgnoreCase("jepg") || prefix.equalsIgnoreCase("pneg")){//上传图片格式
					 String fileName = appInfo.getAPKName() + ".jpg";//上传LOGO图片命名:apk名称.apk
					 File targetFile = new File(path,fileName);
					 if(!targetFile.exists()){
						 targetFile.mkdirs();
					 }
					 try {
						attach.transferTo(targetFile);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						request.setAttribute("fileUploadError", Constants.FILEUPLOAD_ERROR_2);
						return "developer/appinfoadd";
					} 
					 logoPicPath = request.getContextPath()+"/statics/uploadfiles/"+fileName;
					 logoLocPath = path+File.separator+fileName;
				}else{
					request.setAttribute("fileUploadError", Constants.FILEUPLOAD_ERROR_3);
					return "developer/appinfoadd";
				}
			}
			appInfo.setCreatedBy(((DevUser)session.getAttribute(Constants.DEV_USER_SESSION)).getId());
			appInfo.setCreationDate(new Date());
			appInfo.setLogoPicPath(logoPicPath);
			appInfo.setLogoLocPath(logoLocPath);
			appInfo.setDevId(((DevUser)session.getAttribute(Constants.DEV_USER_SESSION)).getId());
			appInfo.setStatus(1);
			try {
				if(as.add(appInfo)>0){
					return "redirect:devList";
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			return "";
		}
		
		
		/*
		 * 新增版本
		 */
		@RequestMapping("/addversion")
		public String addVersion(
				HttpServletRequest request
				,@RequestParam(value="a_downloadLink",required=false)MultipartFile attach,
				AppVersion appVersion){
			HttpSession session=request.getSession();
			String downloadLink =  null;
			String apkLocPath = null;
			String apkFileName = null;
			if(!attach.isEmpty()){
				String path = request.getSession().getServletContext().getRealPath("statics"+File.separator+"uploadfiles");
				String oldFileName = attach.getOriginalFilename();//原文件名
				String prefix = FilenameUtils.getExtension(oldFileName);//原文件后缀
				if(prefix.equals("jpg")){//apk文件命名：apk名称+版本号+.apk
					    String apkName = null;
						apkName = as.getApp(appVersion.getAppId()).getAPKName();
								
					 apkFileName = apkName + "-" +appVersion.getVersionNo() + ".apk";
					 File targetFile = new File(path,apkFileName);
					 if(!targetFile.exists()){
						 targetFile.mkdirs();
					 }
					 try {
						attach.transferTo(targetFile);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						
					} 
					downloadLink = request.getContextPath()+"/statics/uploadfiles/"+apkFileName;
					apkLocPath = path+File.separator+apkFileName;
				}
			}
			appVersion.setCreatedBy(((DevUser)session.getAttribute(Constants.DEV_USER_SESSION)).getId());
			appVersion.setCreationDate(new Date());
			appVersion.setDownloadLink(downloadLink);
			appVersion.setApkLocPath(apkLocPath);
			appVersion.setApkFileName(apkFileName);
			try {
				int num=vs.add(appVersion);
				if(num>0){
					as.upAppStatus(1, appVersion.getAppId());
					as.upAppVid(appVersion.getId(), appVersion.getAppId());
					return "redirect:/devApp/devList";
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return "";
		}
		
		/*
		 * 查看
		 */
		@RequestMapping(value="/appview/{appinfoid}")
		public String AppView(Model model,
				@PathVariable String appinfoid){
			
			AppInfo appinfo=as.getApp(Integer.parseInt(appinfoid));
			List<AppVersion> vlist=vs.getVersionList(Integer.parseInt(appinfoid));
			model.addAttribute("appVersionList", vlist);
			model.addAttribute("appInfo", appinfo);
			
			return "developer/appinfoview";
		}
		
		
		/*
		 * 上架下架
		 */
		@RequestMapping(value="/{appid}/sale",method=RequestMethod.PUT)
		@ResponseBody
		public Object sale(@PathVariable String appid,HttpSession session){
			Map<String, String> map=new HashMap<String, String>();
	    	
	    	AppInfo app=as.getApp(Integer.parseInt(appid));
	    	
	    	Integer status=app.getStatus();
	    	
	    	boolean algn=false;
	    	
	    	if (status==4) {
	    		algn=as.upAppStatus(5, Integer.parseInt(appid));
			}else if(status==5){
				algn=as.upAppStatus(4, Integer.parseInt(appid));
			}
	    	
	    	if(algn){
	    		map.put("errorCode", "0");
	    		map.put("resultMsg", "success");
	    	}else{
	    		map.put("errorCode", "exception000001");
	    	}
			return map;
		}
	
		/*
		 * APP集合
		 */
	@RequestMapping("/devList")
	public String devList(Model model,
			@RequestParam(value="queryStatus",required=false) String _queryStatus,
			@RequestParam(value="pageIndex",required=false) String pageIndex,
			@RequestParam(value="querySoftwareName",required=false) String querySoftwareName,
			@RequestParam(value="queryCategoryLevel1",required=false) String _queryCategoryLevel1,
			@RequestParam(value="queryCategoryLevel2",required=false) String _queryCategoryLevel2,
			@RequestParam(value="queryCategoryLevel3",required=false) String _queryCategoryLevel3,
			@RequestParam(value="queryFlatformId",required=false) String _queryFlatformId){
		
		        //转换类型
				Integer currPageNo=1;
				Integer queryCategoryLevel1=null;
				Integer queryCategoryLevel2=null;
				Integer queryCategoryLevel3=null;
				Integer queryFlatformId=null;
				Integer queryStatus=null;
				
				if(_queryFlatformId!=null&&!_queryFlatformId.equals("")){
					queryFlatformId=Integer.parseInt(_queryFlatformId);
				}
				if(_queryStatus!=null&&!_queryStatus.equals("")){
					queryStatus=Integer.parseInt(_queryStatus);
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
					pageCount=as.getAppInfoCount(querySoftwareName, queryStatus, queryCategoryLevel1, queryCategoryLevel2, queryCategoryLevel3, queryFlatformId,null);
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
				List<DataDictionary> dicList2=null;
				List<AppCategory> cateList=null;
				List<AppCategory> cateList2=null;
				List<AppCategory> cateList3=null;
				
				//所属平台集合
				dicList=ds.getDicList("APP_FLATFORM");
				dicList2=ds.getDicList("APP_STATUS");
				
				//应用类别
				if (queryCategoryLevel2!=null) {
					cateList2=cs.getCateList(queryCategoryLevel1);
				}
		        if (queryCategoryLevel3!=null) {
		        	cateList3=cs.getCateList(queryCategoryLevel2);
				}
				cateList=cs.getCateList(null);
				
				try {
			    appList=as.getInfoList(querySoftwareName, queryStatus, queryCategoryLevel1,
							queryCategoryLevel2, queryCategoryLevel3, queryFlatformId,
							null, (page.getCurrentPageNo()-1)*page.getPageSize(),page.getPageSize());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				model.addAttribute("queryStatus", queryStatus);
				model.addAttribute("querySoftwareName", querySoftwareName);
				model.addAttribute("queryFlatformId", queryFlatformId);
				model.addAttribute("queryCategoryLevel1", queryCategoryLevel1);
				model.addAttribute("queryCategoryLevel2", queryCategoryLevel2);
				model.addAttribute("queryCategoryLevel3", queryCategoryLevel3);
				model.addAttribute("appInfoList", appList);
				model.addAttribute("pages", page);
				model.addAttribute("categoryLevel1List", cateList);
				model.addAttribute("flatFormList", dicList);
				model.addAttribute("statusList", dicList2);
				model.addAttribute("categoryLevel2List", cateList2);
				model.addAttribute("categoryLevel3List", cateList3);
		
		
		
		
		return "developer/appinfolist";
	}
	
	
	/*
	 * 平台
	 */
	@RequestMapping("/datadictionarylist.json")
	@ResponseBody
	public Object datadictionarylist(@RequestParam(value="tcode",required=false)String tcode){
		
			List<DataDictionary> dalist=ds.getDicList(tcode);
			return dalist;

	}
	/**
	 * 按照父类查询类型子集
	 * @param pid
	 * @return
	 */
	@RequestMapping("/categorylevellist.json")
	@ResponseBody
	public Object getcategorylevellist(@RequestParam(value="pid",required=false)String pid){
		Integer parentId=null;
		
		if(pid!=null&&!pid.equals("")){
			 parentId=Integer.parseInt(pid);
		}
		List<AppCategory> cateList=cs.getCateList(parentId);
		
		return cateList;
	}
	
}
