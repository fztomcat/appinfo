package cn.appsys.service.dveuser;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.appsys.dao.dveuser.DveUserMapper;
import cn.appsys.pojo.DevUser;

@Service("devUserService")
public class DevUserServiceImpl implements DevUserService {

	@Resource(name="dveUserMapper")
	private DveUserMapper dveUserMapper;
	
	@Override
	public DevUser doLogin(String devCode, String devPassword) {
		// TODO Auto-generated method stub
		return dveUserMapper.doLogin(devCode, devPassword);
	}

}
