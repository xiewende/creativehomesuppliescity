package com.xiewende.creativehomesuppliescity.mapper;

import java.util.List;

import com.xiewende.creativehomesuppliescity.pojo.Style;
import com.xiewende.creativehomesuppliescity.pojo.StyleExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;
@Mapper
public interface StyleMapper {

    //根据名称模糊查询id的集合
    List<Integer> selectIdOfLikeStyleName(String styleName);

    int countByExample(StyleExample example);

    int deleteByExample(StyleExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Style record);

    int insertSelective(Style record);

    List<Style> selectByExample(StyleExample example);

    Style selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Style record, @Param("example") StyleExample example);

    int updateByExample(@Param("record") Style record, @Param("example") StyleExample example);

    int updateByPrimaryKeySelective(Style record);

    int updateByPrimaryKey(Style record);
}