package com.xiewende.creativehomesuppliescity.service;

import com.xiewende.creativehomesuppliescity.pojo.Program;

import java.util.List;

/**
 * @create 2021-04-18  15:54
 */
public interface ProgramService {

    //用户定制
    public Integer insertProgram(Integer userId,Integer designerId,String goodName,String size,String otherDemand);

    //用户查看自己的定制
    public List<Program> selectProgramByUserId(Integer userId);

    //用户接受这个定制
    public Integer receiveThisProgram(Integer id);

    //用户拒绝这个定制
    public Integer rejectThisProgram(Integer id);

    //设计师查看向自己的定制内容
    public List<Program> selectProgramByDesignerId(Integer designerId,String isNew);

    //设计师完成设计,提交设计图等
    public Integer finishThisProgram(Integer id,String finishPic,String finishIdea);

    //管理员查询全部的设计方案
    public List<Program> adminSelectAllProgram(String goodsName,String designerName,String userName);

    //管理员删除某一个定制
    public Integer deeleteProgram(Integer id);
}
