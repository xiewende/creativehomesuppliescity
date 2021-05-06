package com.xiewende.creativehomesuppliescity.service.impl;

import com.xiewende.creativehomesuppliescity.mapper.DesignerMapper;
import com.xiewende.creativehomesuppliescity.mapper.FinnishProgramMapper;
import com.xiewende.creativehomesuppliescity.mapper.UserMapper;
import com.xiewende.creativehomesuppliescity.pojo.Designer;
import com.xiewende.creativehomesuppliescity.pojo.FinnishProgram;
import com.xiewende.creativehomesuppliescity.pojo.FinnishProgramExample;
import com.xiewende.creativehomesuppliescity.service.FinnishProgramService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * @create 2021-05-06  16:38
 */
@Service
public class FinnishProgramServiceImpl implements FinnishProgramService {

    @Autowired
    private FinnishProgramMapper finnishProgramMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private DesignerMapper designerMapper;

    @Override
    public List<FinnishProgram> listAllFinnishProgramWithSomeDemand(String userName, String designerName) {

        FinnishProgramExample finnishProgramExample = new FinnishProgramExample();
        FinnishProgramExample.Criteria criteria = finnishProgramExample.createCriteria();
        criteria.andIsdeleteEqualTo(0);//没有删除

        //封装查询条件
        if(userName != null){
            List<Integer> list = userMapper.selectIdOfLikeUserName(userName);
            if(list.size()>0) criteria.andUserIdIn(list);
        }

        if(designerName != null){
            List<Integer> list1 = designerMapper.selectIdOfLikeDesignerName(designerName);
            if(list1.size()>0) criteria.andDesignerIdIn(list1);
        }

        //执行查询
        List<FinnishProgram> finnishPrograms = finnishProgramMapper.selectByExample(finnishProgramExample);
        //封装用户 and 设计师
        for(FinnishProgram finnishProgram : finnishPrograms){
            //用户
            finnishProgram.setUser(userMapper.selectByPrimaryKey(finnishProgram.getUserId()));
            //设计师
            finnishProgram.setDesigner(designerMapper.selectByPrimaryKey(finnishProgram.getDesignerId()));
            //两个时间
            if (finnishProgram.getCreateTime() != null) {
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String dateString = formatter.format(finnishProgram.getCreateTime());
                finnishProgram.setCreateTimeStr(dateString);
            }
            if (finnishProgram.getFinishTime() != null) {
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String dateString1 = formatter.format(finnishProgram.getFinishTime());
                finnishProgram.setFinishTimeStr(dateString1);
            }
        }

        return finnishPrograms;
    }

    @Override
    public Integer deleteFinnishProgram(Integer id) {
        FinnishProgram finnishProgram = finnishProgramMapper.selectByPrimaryKey(id);
        finnishProgram.setIsdelete(1);
        return finnishProgramMapper.updateByPrimaryKey(finnishProgram);
    }
}
