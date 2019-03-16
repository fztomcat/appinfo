package cn.appsys.service.AppInfo;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.appsys.pojo.AppVersion;

public interface VerSionService {
	
	
	int delVersion(@Param(value="appId") Integer aid);

	AppVersion getVersion(@Param("vid")Integer vid);
	
	List<AppVersion> getVersionList(@Param("appId")Integer appId);
	
	public int add(AppVersion appVersion);
	
	public int upVersion(AppVersion appVersion);
}
