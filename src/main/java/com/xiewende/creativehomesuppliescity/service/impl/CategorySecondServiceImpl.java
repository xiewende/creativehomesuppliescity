package com.xiewende.creativehomesuppliescity.service.impl;

import com.xiewende.creativehomesuppliescity.mapper.CategoryFirstMapper;
import com.xiewende.creativehomesuppliescity.mapper.CategorySecondMapper;
import com.xiewende.creativehomesuppliescity.pojo.CategoryFirst;
import com.xiewende.creativehomesuppliescity.pojo.CategoryFirstExample;
import com.xiewende.creativehomesuppliescity.pojo.CategorySecond;
import com.xiewende.creativehomesuppliescity.pojo.CategorySecondExample;
import com.xiewende.creativehomesuppliescity.service.CategorySecondService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @create 2021-04-12  17:39
 */
@Service
public class CategorySecondServiceImpl implements CategorySecondService {

    @Autowired
    private CategorySecondMapper categorySecondMapper;

    @Autowired
    public CategoryFirstMapper categoryFirstMapper;

    @Override
    public Integer insertCategorySecond(CategorySecond categorySecond,String categoryFirstName) {
        //去重
        CategorySecondExample categorySecondExample = new CategorySecondExample();
        CategorySecondExample.Criteria criteria = categorySecondExample.createCriteria();
        criteria.andCategoryNameEqualTo(categorySecond.getCategoryName());
        criteria.andIsdeleteEqualTo(0);
        List<CategorySecond> categorySeconds = categorySecondMapper.selectByExample(categorySecondExample);
        if(categorySeconds.size()>0) return 0;
        //查询一级id
        CategoryFirstExample categoryFirstExample = new CategoryFirstExample();
        CategoryFirstExample.Criteria criteria1 = categoryFirstExample.createCriteria();
        criteria1.andCategoryNameEqualTo(categoryFirstName);
        List<CategoryFirst> categoryFirsts = categoryFirstMapper.selectByExample(categoryFirstExample);
        //封装给二级
        categorySecond.setCategoryFirstId(categoryFirsts.get(0).getId());
        return categorySecondMapper.insert(categorySecond);
    }

    @Override
    public Integer updateCategorySecond(CategorySecond categorySecond,String categoryFirstName) {
        //去重
        CategorySecondExample categorySecondExample = new CategorySecondExample();
        CategorySecondExample.Criteria criteria = categorySecondExample.createCriteria();
        criteria.andCategoryNameEqualTo(categorySecond.getCategoryName());
        criteria.andIdNotEqualTo(categorySecond.getId());
        criteria.andIsdeleteEqualTo(0);
        List<CategorySecond> categorySeconds = categorySecondMapper.selectByExample(categorySecondExample);
        if(categorySeconds.size()>0) return 0;
        //查询一级id
        CategoryFirstExample categoryFirstExample = new CategoryFirstExample();
        CategoryFirstExample.Criteria criteria1 = categoryFirstExample.createCriteria();
        criteria1.andCategoryNameEqualTo(categoryFirstName);
        List<CategoryFirst> categoryFirsts = categoryFirstMapper.selectByExample(categoryFirstExample);
        //封装给二级
        categorySecond.setCategoryFirstId(categoryFirsts.get(0).getId());
        return categorySecondMapper.updateByPrimaryKey(categorySecond);
    }

    @Override
    public Integer deleteCategorySecond(Integer id) {
        CategorySecond categorySecond = categorySecondMapper.selectByPrimaryKey(id);
        categorySecond.setIsdelete(1);
        return categorySecondMapper.updateByPrimaryKey(categorySecond);
    }

    @Override
    public List<CategorySecond> listAllCategorySecond(String firstId) {
        CategorySecondExample categorySecondExample = new CategorySecondExample();
        CategorySecondExample.Criteria criteria = categorySecondExample.createCriteria();
        criteria.andIsdeleteEqualTo(0);
        if(firstId != null) criteria.andCategoryFirstIdEqualTo(Integer.valueOf(firstId));
        List<CategorySecond> categorySeconds = categorySecondMapper.selectByExample(categorySecondExample);
        //封装一级分类
        for(CategorySecond categorySecond:categorySeconds){
            CategoryFirst categoryFirst = categoryFirstMapper.selectByPrimaryKey(categorySecond.getCategoryFirstId());
            categorySecond.setCategoryFirst(categoryFirst);
        }
        return categorySeconds;
    }

    @Override
    public CategorySecond findCategorySecondById(Integer id) {
        CategorySecond categorySecond = categorySecondMapper.selectByPrimaryKey(id);
        categorySecond.setCategoryFirst(categoryFirstMapper.selectByPrimaryKey(categorySecond.getCategoryFirstId()));
        return categorySecond;
    }
}
