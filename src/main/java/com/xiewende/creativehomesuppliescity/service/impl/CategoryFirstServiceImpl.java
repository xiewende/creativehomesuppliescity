package com.xiewende.creativehomesuppliescity.service.impl;

import com.xiewende.creativehomesuppliescity.mapper.CategoryFirstMapper;
import com.xiewende.creativehomesuppliescity.mapper.CategorySecondMapper;
import com.xiewende.creativehomesuppliescity.pojo.CategoryFirst;
import com.xiewende.creativehomesuppliescity.pojo.CategoryFirstExample;
import com.xiewende.creativehomesuppliescity.pojo.CategorySecond;
import com.xiewende.creativehomesuppliescity.pojo.CategorySecondExample;
import com.xiewende.creativehomesuppliescity.service.CategoryFirstService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @create 2021-04-12  11:31
 */
@Service
public class CategoryFirstServiceImpl implements CategoryFirstService {

    @Autowired
    private CategoryFirstMapper categoryFirstMapper;

    @Autowired
    private CategorySecondMapper categorySecondMapper;

    @Override
    public Integer insertCategoryFirst(CategoryFirst categoryFirst) {
        //查重
        CategoryFirstExample categoryFirstExample = new CategoryFirstExample();
        CategoryFirstExample.Criteria criteria = categoryFirstExample.createCriteria();
        criteria.andCategoryNameEqualTo(categoryFirst.getCategoryName());
        List<CategoryFirst> categoryFirsts = categoryFirstMapper.selectByExample(categoryFirstExample);
        if(categoryFirsts.size()>0) return 0;
        return categoryFirstMapper.insert(categoryFirst);
    }

    @Override
    public Integer updateCategoryFirst(CategoryFirst categoryFirst) {
        //查重
        CategoryFirstExample categoryFirstExample = new CategoryFirstExample();
        CategoryFirstExample.Criteria criteria = categoryFirstExample.createCriteria();
        criteria.andCategoryNameEqualTo(categoryFirst.getCategoryName());
        criteria.andIdNotEqualTo(categoryFirst.getId());
        List<CategoryFirst> categoryFirsts = categoryFirstMapper.selectByExample(categoryFirstExample);
        if(categoryFirsts.size()>0) return 0;
        return categoryFirstMapper.updateByPrimaryKey(categoryFirst);
    }

    @Override
    public Integer deeteCategoryFirst(Integer id) {
        CategoryFirst categoryFirst = categoryFirstMapper.selectByPrimaryKey(id);
        categoryFirst.setIsdelete(1);
        return categoryFirstMapper.updateByPrimaryKey(categoryFirst);
    }

    @Override
    public List<CategoryFirst> listAllCategoryFirst() {
        CategoryFirstExample categoryFirstExample = new CategoryFirstExample();
        CategoryFirstExample.Criteria criteria = categoryFirstExample.createCriteria();
        criteria.andIsdeleteEqualTo(0);
        List<CategoryFirst> categoryFirsts = categoryFirstMapper.selectByExample(categoryFirstExample);
        //找对应的二级标题
        for(CategoryFirst categoryFirst : categoryFirsts){
            CategorySecondExample categorySecondExample = new CategorySecondExample();
            CategorySecondExample.Criteria criteria1 = categorySecondExample.createCriteria();
            criteria1.andCategoryFirstIdEqualTo(categoryFirst.getId());
            criteria1.andIsdeleteEqualTo(0);
            List<CategorySecond> categorySeconds = categorySecondMapper.selectByExample(categorySecondExample);
            categoryFirst.setCategorySeconds(categorySeconds);
        }
        return categoryFirsts;
    }

    @Override
    public CategoryFirst findCategoryFirstById(Integer id) {

        CategoryFirst categoryFirst = categoryFirstMapper.selectByPrimaryKey(id);
        //找对应的二级标题
        CategorySecondExample categorySecondExample = new CategorySecondExample();
        CategorySecondExample.Criteria criteria1 = categorySecondExample.createCriteria();
        criteria1.andCategoryFirstIdEqualTo(categoryFirst.getId());
        criteria1.andIsdeleteEqualTo(0);
        List<CategorySecond> categorySeconds = categorySecondMapper.selectByExample(categorySecondExample);
        categoryFirst.setCategorySeconds(categorySeconds);
        return categoryFirst;
    }
}
