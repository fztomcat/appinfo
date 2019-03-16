package cn.appsys.service.AppInfo;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.appsys.dao.category.CategoryMapper;
import cn.appsys.pojo.AppCategory;
@Service("categoryService")
public class CategoryServiceImpl implements CategoryService {

	@Resource(name="categoryMapper")
	private CategoryMapper categoryMapper;
	
	public List<AppCategory> getCateList(Integer parentId) {
		// TODO Auto-generated method stub
		return categoryMapper.getCateList(parentId);
	}

}
