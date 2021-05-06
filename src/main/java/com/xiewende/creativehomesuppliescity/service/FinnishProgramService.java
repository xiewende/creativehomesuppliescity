package com.xiewende.creativehomesuppliescity.service;

import com.xiewende.creativehomesuppliescity.pojo.FinnishProgram;

import java.util.List;

/**
 * @create 2021-05-06  16:30
 */
public interface FinnishProgramService {

    //展示
    public List<FinnishProgram> listAllFinnishProgramWithSomeDemand(String userName,String designerName);

    //删除
    public Integer deleteFinnishProgram(Integer id);
}
