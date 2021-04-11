package com.xiewende.creativehomesuppliescity.service;

import com.xiewende.creativehomesuppliescity.pojo.Style;

import java.util.List;

/**
 * @create 2021-04-10  21:34
 */
public interface StyleService {

    //添加风格
    public Integer insertStyle(Style style);

    //修改
    public Integer updateStyle(Style style);

    //删除
    public Integer deleteStyle(Integer id);

    //查询全部
    public List<Style> listAllStyle();

    //更具id查
    public Style selectStyleById(Integer id);
}
