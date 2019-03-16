package cn.appsys.service.AppInfo;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.appsys.pojo.AppInfo;

public interface AppService {

	
	public int del(@Param(value="id") Integer id);
	
	public int add(AppInfo appInfo);
	
	
	public int getAppInfoCount(
			   @Param(value="softwareName")String querySoftwareName,
			   @Param(value="status")Integer queryStatus,
			   @Param(value="categoryLevel1")Integer queryCategoryLevel1,
			   @Param(value="categoryLevel2")Integer queryCategoryLevel2,
			   @Param(value="categoryLevel3")Integer queryCategoryLevel3,
			   @Param(value="flatformId")Integer queryFlatformId,
			   @Param(value="devId")Integer devId)throws Exception;
	
	List<AppInfo> getInfoList(
			   @Param(value="softwareName")String querySoftwareName,
			   @Param(value="status")Integer queryStatus,
			   @Param(value="categoryLevel1")Integer queryCategoryLevel1,
			   @Param(value="categoryLevel2")Integer queryCategoryLevel2,
			   @Param(value="categoryLevel3")Integer queryCategoryLevel3,
			   @Param(value="flatformId")Integer queryFlatformId,
			   @Param(value="devId")Integer devId,
			   @Param(value="pageNo")Integer currcountPage,
			   @Param(value="pageSize")Integer pageSize)throws Exception;
	
	AppInfo getApp(@Param("aid")Integer aid);
	
	/*
	 * 更新APP状态
	 */
	boolean upAppStatus(@Param("status")Integer status,@Param("id")Integer id);
	
	/*
	 * 更新版本
	 */
	int upAppVid(@Param("vid") Integer vid,@Param("id")Integer id);
	
	
	/*
	 * 修改APP
	 */
	int upApp(AppInfo appInfo);
	
	
}
