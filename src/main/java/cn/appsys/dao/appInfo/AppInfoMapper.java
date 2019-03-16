package cn.appsys.dao.appInfo;

import java.util.List;


import org.apache.ibatis.annotations.Param;

import cn.appsys.pojo.AppInfo;

/**
 * APP mapper接口
 * @author Lenovo
 *
 */
public interface AppInfoMapper {
	
	
	
	public int del(@Param(value="id") Integer id);
	
	/*
	 * 新增APP
	 */
	public int addAPP(AppInfo appInfo);

	/*
	 * 查询总记录数
	 */
	public int getAppInfoCount(
			   @Param(value="softwareName")String querySoftwareName,
			   @Param(value="status")Integer queryStatus,
			   @Param(value="categoryLevel1")Integer queryCategoryLevel1,
			   @Param(value="categoryLevel2")Integer queryCategoryLevel2,
			   @Param(value="categoryLevel3")Integer queryCategoryLevel3,
			   @Param(value="flatformId")Integer queryFlatformId,
			   @Param(value="devId")Integer devId)throws Exception;
	
	/*
	 * App集合
	 */
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
	
	/*
	 * 按ID获得APP
	 */
	AppInfo getApp(@Param("aid")Integer aid);
	
	
	/*
	 * 更新APP状态
	 */
	int upAppStatus(@Param("status")Integer status,@Param("id")Integer id);
	
	/*
	 * 更新版本
	 */
	int upAppVid(@Param("vid") Integer vid,@Param("id")Integer id);
	
	/*
	 * 修改APP
	 */
	int upApp(AppInfo appInfo);
	
}
