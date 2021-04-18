package com.xiewende.creativehomesuppliescity.service.impl;

import com.xiewende.creativehomesuppliescity.mapper.DesignerMapper;
import com.xiewende.creativehomesuppliescity.mapper.FinnishProgramMapper;
import com.xiewende.creativehomesuppliescity.mapper.ProgramMapper;
import com.xiewende.creativehomesuppliescity.mapper.UserMapper;
import com.xiewende.creativehomesuppliescity.pojo.FinnishProgram;
import com.xiewende.creativehomesuppliescity.pojo.Program;
import com.xiewende.creativehomesuppliescity.pojo.ProgramExample;
import com.xiewende.creativehomesuppliescity.service.ProgramService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @create 2021-04-18  16:05
 */
@Service
public class ProgramServiceImpl implements ProgramService {

    @Autowired
    private ProgramMapper programMapper;
    @Autowired
    private FinnishProgramMapper finnishProgramMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private DesignerMapper designerMapper;
    @Override
    public Integer insertProgram(Integer userId,Integer designerId,String goodName, String size, String otherDemand) {
        //创建对象
        Program program = new Program();
        program.setUserId(userId);
        program.setDesignerId(designerId);
        program.setGoodName(goodName);
        program.setSize(size);
        program.setOtherDemand(otherDemand);

        //其他信息
        program.setStatus(0); //请求提交成功
        program.setCreateTime(new Date());
        program.setIsdelete(0);

        //执行插入
        return programMapper.insert(program);
    }

    @Override
    public List<Program> selectProgramByUserId(Integer userId) {
        ProgramExample programExample = new ProgramExample();
        ProgramExample.Criteria criteria = programExample.createCriteria();
        criteria.andUserIdEqualTo(userId);
        criteria.andIsdeleteEqualTo(0);
        List<Program> programs = programMapper.selectByExample(programExample);
        //封装设计师
        for(Program program : programs){
            program.setDesigner(designerMapper.selectByPrimaryKey(program.getDesignerId()));
        }
        return programs;
    }

    @Override
    public Integer receiveThisProgram(Integer id) {
        Program program = programMapper.selectByPrimaryKey(id);
        program.setIsAccept(1); // 接受
        programMapper.updateByPrimaryKey(program);

        //添加到成功案例中
        FinnishProgram finnishProgram = new FinnishProgram();
        finnishProgram.setUserId(program.getUserId());
        finnishProgram.setDesignerId(program.getDesignerId());
        finnishProgram.setGoodName(program.getGoodName());
        finnishProgram.setSize(program.getSize());
        finnishProgram.setOtherDemand(program.getOtherDemand());
        finnishProgram.setFinishPic(program.getFinishPic());
        finnishProgram.setFinisnIdea(program.getFinisnIdea());
        finnishProgram.setCreateTime(program.getCreateTime());
        finnishProgram.setFinishTime(program.getFinishTime());
        finnishProgram.setIsdelete(0);
        finnishProgramMapper.insert(finnishProgram);

        return 1;
    }

    @Override
    public Integer rejectThisProgram(Integer id) {
        Program program = programMapper.selectByPrimaryKey(id);
        program.setIsAccept(0); //拒绝
        return programMapper.updateByPrimaryKey(program);
    }

    @Override
    public List<Program> selectProgramByDesignerId(Integer designerId, String isNew) {
        ProgramExample programExample = new ProgramExample();
        ProgramExample.Criteria criteria = programExample.createCriteria();
        criteria.andIsdeleteEqualTo(0);
        if("是".equals(isNew)){
            criteria.andStatusEqualTo(0);
        }
        //执行查询
        List<Program> programs = programMapper.selectByExample(programExample);
        //封装user
        for(Program program : programs){
            program.setUser(userMapper.selectByPrimaryKey(program.getUserId()));
        }
        return programs;
    }

    @Override
    public Integer finishThisProgram(Integer id, String finishPic, String finishIdea) {
        Program program = programMapper.selectByPrimaryKey(id);
        program.setFinishPic(finishPic);
        program.setFinisnIdea(finishIdea);
        program.setFinishTime(new Date());
        program.setStatus(1); // 完成设计
        return programMapper.updateByPrimaryKey(program);
    }
}
