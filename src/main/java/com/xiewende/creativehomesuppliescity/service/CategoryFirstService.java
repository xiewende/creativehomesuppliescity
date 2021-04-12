package com.xiewende.creativehomesuppliescity.service;

import com.xiewende.creativehomesuppliescity.pojo.CategoryFirst;

import java.util.List;

/**
 * @create 2021-04-12  11:28
 */
public interface CategoryFirstService {

    //添加
    public Integer insertCategoryFirst(CategoryFirst categoryFirst);

    //修改
    public Integer updateCategoryFirst(CategoryFirst categoryFirst);

    //删除
    public Integer deeteCategoryFirst(Integer id);

    //查询全部
    List<CategoryFirst> listAllCategoryFirst();

    //根据id查
    public  CategoryFirst findCategoryFirstById(Integer id);
}
