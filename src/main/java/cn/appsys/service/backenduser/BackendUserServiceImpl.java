package cn.appsys.service.backenduser;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.appsys.dao.backenduser.BackendUserMapper;
import cn.appsys.pojo.BackendUser;

@Service("backendUserService")
public class BackendUserServiceImpl implements BackendUserService {

	@Resource(name="backendUserMapper")
	private BackendUserMapper backendUserMapper;
	
	public BackendUser doLogin(String userCode, String userPassword) {
		// TODO Auto-generated method stub
		return backendUserMapper.doLogin(userCode, userPassword);
	}

}
