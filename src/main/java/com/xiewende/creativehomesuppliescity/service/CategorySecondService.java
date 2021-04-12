package com.xiewende.creativehomesuppliescity.service;

import com.xiewende.creativehomesuppliescity.pojo.CategorySecond;
import io.swagger.models.auth.In;

import java.util.List;

/**
 * @create 2021-04-12  17:36
 */
public interface CategorySecondService {

    //添加
    public Integer insertCategorySecond(CategorySecond categorySecond,String categoryFirstName);

    //修改
    public Integer updateCategorySecond(CategorySecond categorySecond,String categoryFirstName);

    //删除
    public Integer deleteCategorySecond(Integer id);

    //查询全部
    public List<CategorySecond> listAllCategorySecond();

    //id查
    public CategorySecond findCategorySecondById(Integer id);

}
