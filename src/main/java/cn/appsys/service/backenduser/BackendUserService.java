package cn.appsys.service.backenduser;

import cn.appsys.pojo.BackendUser;

public interface BackendUserService {

	BackendUser doLogin(String userCode,String userPassword);//后台登录
}
