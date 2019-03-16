package cn.appsys.dao.version;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.appsys.pojo.AppVersion;

public interface VerSionMapper {
	
	int delVersion(@Param(value="appId") Integer aid);

	AppVersion getVersion(@Param("vid")Integer vid);
	
	List<AppVersion> getVersionList(@Param("id")Integer appId);
	
	public int add(AppVersion appVersion);
	
	public int upVersion(AppVersion appVersion);
}
