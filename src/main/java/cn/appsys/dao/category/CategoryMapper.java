package cn.appsys.dao.category;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.appsys.pojo.AppCategory;

public interface CategoryMapper {
	
	List<AppCategory> getCateList(@Param("parentId")Integer parentId);//查询子分类
}
