package cn.appsys.dao.backenduser;

import org.apache.ibatis.annotations.Param;

import cn.appsys.pojo.BackendUser;

public interface BackendUserMapper {

	BackendUser doLogin(@Param("userCode")String userCode,@Param("userPassword")String userPassword);//后台登录
	
	
}
