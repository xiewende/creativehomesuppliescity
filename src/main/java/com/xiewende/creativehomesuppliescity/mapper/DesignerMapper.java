package com.xiewende.creativehomesuppliescity.mapper;

import java.util.List;

import com.xiewende.creativehomesuppliescity.pojo.Designer;
import com.xiewende.creativehomesuppliescity.pojo.DesignerExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;
@Mapper
public interface DesignerMapper {

    List<Integer> selectIdOfLikeDesignerName(String designerName);

    int countByExample(DesignerExample example);

    int deleteByExample(DesignerExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Designer record);

    int insertSelective(Designer record);

    List<Designer> selectByExample(DesignerExample example);

    Designer selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Designer record, @Param("example") DesignerExample example);

    int updateByExample(@Param("record") Designer record, @Param("example") DesignerExample example);

    int updateByPrimaryKeySelective(Designer record);

    int updateByPrimaryKey(Designer record);
}