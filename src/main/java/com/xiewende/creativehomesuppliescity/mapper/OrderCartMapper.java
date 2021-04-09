package com.xiewende.creativehomesuppliescity.mapper;

import java.util.List;

import com.xiewende.creativehomesuppliescity.pojo.OrderCart;
import com.xiewende.creativehomesuppliescity.pojo.OrderCartExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;
@Mapper
public interface OrderCartMapper {
    int countByExample(OrderCartExample example);

    int deleteByExample(OrderCartExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(OrderCart record);

    int insertSelective(OrderCart record);

    List<OrderCart> selectByExample(OrderCartExample example);

    OrderCart selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") OrderCart record, @Param("example") OrderCartExample example);

    int updateByExample(@Param("record") OrderCart record, @Param("example") OrderCartExample example);

    int updateByPrimaryKeySelective(OrderCart record);

    int updateByPrimaryKey(OrderCart record);
}