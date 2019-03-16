package cn.appsys.contorller.developer;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import cn.appsys.pojo.DevUser;
import cn.appsys.service.dveuser.DevUserService;
import cn.appsys.tools.Constants;

@Controller
@RequestMapping("/dev")
public class devLogin {

	@Resource(name="devUserService")
	private DevUserService devUserService;
	
	@RequestMapping("/login.html")
	public String login(){
		
		
		return "devlogin";
	}
	
	
	@RequestMapping("/devclear.html")
	public String clear(HttpSession session){
		
		session.removeAttribute(Constants.DEV_USER_SESSION);
		
		return "redirect:login.html";
	}
	
	@RequestMapping("/doLogin.html")
	public String doLogin(HttpSession session,
			@RequestParam(value="devCode",required=false)String devCode,
			@RequestParam(value="devPassword",required=false)String devPassword){
		
		DevUser du=devUserService.doLogin(devCode, devPassword);
		
		session.setAttribute(Constants.DEV_USER_SESSION, du);
		
		if (du!=null) {
			return "developer/main";
		}
           
		return "redirect:login.html";
	}
}
