package com.xiewende.creativehomesuppliescity.mapper;

import java.util.List;

import com.xiewende.creativehomesuppliescity.pojo.CategoryFirst;
import com.xiewende.creativehomesuppliescity.pojo.CategoryFirstExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;
@Mapper
public interface CategoryFirstMapper {
    int countByExample(CategoryFirstExample example);

    int deleteByExample(CategoryFirstExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CategoryFirst record);

    int insertSelective(CategoryFirst record);

    List<CategoryFirst> selectByExample(CategoryFirstExample example);

    CategoryFirst selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CategoryFirst record, @Param("example") CategoryFirstExample example);

    int updateByExample(@Param("record") CategoryFirst record, @Param("example") CategoryFirstExample example);

    int updateByPrimaryKeySelective(CategoryFirst record);

    int updateByPrimaryKey(CategoryFirst record);
}