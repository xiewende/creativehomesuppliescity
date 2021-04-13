package com.xiewende.creativehomesuppliescity.mapper;

import java.util.List;

import com.xiewende.creativehomesuppliescity.pojo.CategorySecond;
import com.xiewende.creativehomesuppliescity.pojo.CategorySecondExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;
@Mapper
public interface CategorySecondMapper {

    //根据名称模糊查询id的集合
    List<Integer> selectIdOfLikeCategoryName(String categorySecondName);

    //跟据一级分类的名称。模糊查询其下面的全部二级分类id
    List<Integer> selectIdOfLikeFirstName(String categoryFirstName);

    int countByExample(CategorySecondExample example);

    int deleteByExample(CategorySecondExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CategorySecond record);

    int insertSelective(CategorySecond record);

    List<CategorySecond> selectByExample(CategorySecondExample example);

    CategorySecond selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CategorySecond record, @Param("example") CategorySecondExample example);

    int updateByExample(@Param("record") CategorySecond record, @Param("example") CategorySecondExample example);

    int updateByPrimaryKeySelective(CategorySecond record);

    int updateByPrimaryKey(CategorySecond record);
}