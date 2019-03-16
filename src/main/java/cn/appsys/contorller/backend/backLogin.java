package cn.appsys.contorller.backend;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import cn.appsys.pojo.BackendUser;
import cn.appsys.service.backenduser.BackendUserService;
import cn.appsys.tools.Constants;

@Controller
@RequestMapping("/manager")
public class backLogin {
	
	@Resource(name="backendUserService")
	private BackendUserService bs;
	

	@RequestMapping("/login.html")
	public String login(){
		
		
		return "backendlogin";
	}
	
	@RequestMapping("/dologin.html")
	public String dologin(@RequestParam(value="userCode",required=false)String userCode,
			@RequestParam(value="userPassword",required=false)String userPassword,
			HttpSession session){
		BackendUser bu=bs.doLogin(userCode, userPassword);
		
		session.setAttribute(Constants.USER_SESSION, bu);
		
		return "backend/main";
	}
	
	@RequestMapping("/backclear.html")
	public String clear(HttpSession session){
		
		session.removeAttribute(Constants.USER_SESSION);
		
		return "redirect:login.html";
	}
	
	@RequestMapping("/backList")
	public String backList(
			@RequestParam(value="querySoftwareName",required=false) String querySoftwareName,
			@RequestParam(value="queryFlatformId",required=false) String queryFlatformId,
			@RequestParam(value="queryCategoryLevel1",required=false) String queryCategoryLevel1,
			@RequestParam(value="queryCategoryLevel2",required=false) String queryCategoryLevel2,
			@RequestParam(value="queryCategoryLevel3",required=false) String queryCategoryLevel3,
			HttpServletRequest request,
			@RequestParam(value="pageIndex",required=false) String pageIndex
			){
		int currCountPageNo=1;
		
		if (pageIndex!=null) {
			currCountPageNo=Integer.parseInt(pageIndex);
		}
		
		
		
		return "";
	}
	
}
