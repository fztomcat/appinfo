package cn.appsys.service.AppInfo;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.appsys.dao.dictionary.DicTionAryMapper;
import cn.appsys.pojo.DataDictionary;
@Service("dicTionAryService")
public class DicTionAryServiceImpl implements DicTionAryService {

	@Resource(name="dicTionAryMapper")
	private DicTionAryMapper dicTionAryMapper;
	
	public List<DataDictionary> getDicList(String typeCode) {
		
		return dicTionAryMapper.getDicList(typeCode);
	}

}
