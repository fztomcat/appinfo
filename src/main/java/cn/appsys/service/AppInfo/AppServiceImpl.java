package cn.appsys.service.AppInfo;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.appsys.dao.appInfo.AppInfoMapper;
import cn.appsys.pojo.AppInfo;

@Service("appService")
public class AppServiceImpl implements AppService {

	@Resource(name="appInfoMapper")
	private AppInfoMapper appInfoMapper;
	
	
	@Override
	public int getAppInfoCount(String querySoftwareName, Integer queryStatus,
			Integer queryCategoryLevel1, Integer queryCategoryLevel2,
			Integer queryCategoryLevel3, Integer queryFlatformId, Integer devId)
			throws Exception {
		// TODO Auto-generated method stub
		return appInfoMapper.getAppInfoCount(querySoftwareName, queryStatus, queryCategoryLevel1, queryCategoryLevel2, queryCategoryLevel3, queryFlatformId, devId);
	}

	@Override
	public List<AppInfo> getInfoList(String querySoftwareName,
			Integer queryStatus, Integer queryCategoryLevel1,
			Integer queryCategoryLevel2, Integer queryCategoryLevel3,
			Integer queryFlatformId, Integer devId, Integer currcountPage,
			Integer pageSize) throws Exception {
		// TODO Auto-generated method stub
		return appInfoMapper.getInfoList(querySoftwareName, queryStatus, queryCategoryLevel1, queryCategoryLevel2, queryCategoryLevel3, queryFlatformId, devId, currcountPage, pageSize);
	}

	@Override
	public AppInfo getApp(Integer aid) {
		// TODO Auto-generated method stub
		return appInfoMapper.getApp(aid);
	}

	@Override
	public boolean upAppStatus(Integer status, Integer id) {
		boolean algn=false;
		if(appInfoMapper.upAppStatus(status, id)>0){
			algn=true;
		}
		
		return algn;
	}

	@Override
	public int upAppVid(Integer vid, Integer id) {
		// TODO Auto-generated method stub
		return appInfoMapper.upAppVid(vid, id);
	}

	@Override
	public int upApp(AppInfo appInfo) {
		// TODO Auto-generated method stub
		return appInfoMapper.upApp(appInfo);
	}

	@Override
	public int add(AppInfo appInfo) {
		// TODO Auto-generated method stub
		return appInfoMapper.addAPP(appInfo);
	}

	@Override
	public int del(Integer id) {
		// TODO Auto-generated method stub
		return appInfoMapper.del(id);
	}

}
