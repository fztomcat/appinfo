package cn.appsys.dao.dveuser;

import org.apache.ibatis.annotations.Param;

import cn.appsys.pojo.DevUser;

public interface DveUserMapper {

	//开发者登录
	DevUser doLogin(@Param("devCode") String devCode,@Param("devPassword")String devPassword);
}
