package cn.appsys.service.AppInfo;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.appsys.dao.version.VerSionMapper;
import cn.appsys.pojo.AppVersion;
@Service("verSionService")
public class VerSionServiceImpl implements VerSionService {

	@Resource(name="verSionMapper")
	private VerSionMapper verSionMapper;
	
	public AppVersion getVersion(Integer vid) {
		// TODO Auto-generated method stub
		return verSionMapper.getVersion(vid);
	}

	@Override
	public List<AppVersion> getVersionList(Integer appId) {
		// TODO Auto-generated method stub
		return verSionMapper.getVersionList(appId);
	}

	@Override
	public int add(AppVersion appVersion) {

		return verSionMapper.add(appVersion);
	}

	@Override
	public int upVersion(AppVersion appVersion) {
		// TODO Auto-generated method stub
		return verSionMapper.upVersion(appVersion);
	}

	@Override
	public int delVersion(Integer aid) {
		// TODO Auto-generated method stub
		return verSionMapper.delVersion(aid);
	}

}
