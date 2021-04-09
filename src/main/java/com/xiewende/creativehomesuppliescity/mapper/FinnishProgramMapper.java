package com.xiewende.creativehomesuppliescity.mapper;

import java.util.List;

import com.xiewende.creativehomesuppliescity.pojo.FinnishProgram;
import com.xiewende.creativehomesuppliescity.pojo.FinnishProgramExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;
@Mapper
public interface FinnishProgramMapper {
    int countByExample(FinnishProgramExample example);

    int deleteByExample(FinnishProgramExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(FinnishProgram record);

    int insertSelective(FinnishProgram record);

    List<FinnishProgram> selectByExample(FinnishProgramExample example);

    FinnishProgram selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") FinnishProgram record, @Param("example") FinnishProgramExample example);

    int updateByExample(@Param("record") FinnishProgram record, @Param("example") FinnishProgramExample example);

    int updateByPrimaryKeySelective(FinnishProgram record);

    int updateByPrimaryKey(FinnishProgram record);
}