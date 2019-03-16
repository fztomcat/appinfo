package cn.appsys.service.AppInfo;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.appsys.pojo.DataDictionary;

public interface DicTionAryService {
   
	List<DataDictionary> getDicList(@Param("typeCode")String typeCode);
	
}
