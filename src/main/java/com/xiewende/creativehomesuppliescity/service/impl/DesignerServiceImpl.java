package com.xiewende.creativehomesuppliescity.service.impl;

import com.sun.xml.internal.bind.v2.TODO;
import com.xiewende.creativehomesuppliescity.mapper.DesignerMapper;
import com.xiewende.creativehomesuppliescity.pojo.Designer;
import com.xiewende.creativehomesuppliescity.pojo.DesignerExample;
import com.xiewende.creativehomesuppliescity.pojo.User;
import com.xiewende.creativehomesuppliescity.service.DesignerService;
import com.xiewende.creativehomesuppliescity.utils.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @create 2021-04-10  22:30
 */
@Service
public class DesignerServiceImpl implements DesignerService {

    @Autowired
    private DesignerMapper designerMapper;

    @Override
    public Integer InsertDesigner(Designer designer) {
        //查重 名字唯一
        DesignerExample designerExample = new DesignerExample();
        DesignerExample.Criteria criteria = designerExample.createCriteria();
        criteria.andDesignerNameEqualTo(designer.getDesignerName());
        List<Designer> designers = designerMapper.selectByExample(designerExample);
        if(designers.size()>0) return 0;
        return designerMapper.insert(designer);
    }

    @Override
    public Integer updateDesigner(Designer designer) {
        //查重 名字唯一
        DesignerExample designerExample = new DesignerExample();
        DesignerExample.Criteria criteria = designerExample.createCriteria();
        criteria.andDesignerNameEqualTo(designer.getDesignerName());
        List<Designer> designers = designerMapper.selectByExample(designerExample);
        if(designers.size()>0) return 0;
        return designerMapper.updateByPrimaryKey(designer);
    }

    @Override
    public Integer deteleDesigner(Integer id) {
        Designer designer = designerMapper.selectByPrimaryKey(id);
        designer.setIsdelete(1);
        return designerMapper.updateByPrimaryKey(designer);
    }

    @Override
    //TODO  还要将风格查全部 模糊查询后面两个
    public List<Designer> listDesigner(String professionalTitle, String type) {

        DesignerExample designerExample = new DesignerExample();
        DesignerExample.Criteria criteria = designerExample.createCriteria();
        criteria.andIsdeleteEqualTo(0); //没有删除
        if(professionalTitle != null) criteria.andProfessionalTitleLike("%"+professionalTitle+"%");
        if(type != null) criteria.andTypeLike("%"+type+"%");
        //执行查询
        List<Designer> designers = designerMapper.selectByExample(designerExample);
        //将外键风格的补齐
        return designers;

    }

    @Override
    public Designer selectDesignerById(Integer id) {

        //修改人气
        Designer designer = designerMapper.selectByPrimaryKey(id);
        designer.setPopularity(designer.getPopularity()+1);
        designerMapper.updateByPrimaryKey(designer);
        //TODO 查询设计详情的时候要将成功案例查询
        return designer;
    }

    @Override
    public Integer updatePassword(Integer id, String oldPassword, String newPassword) {
        //先判断旧密码是否输入正确
        Designer designer = designerMapper.selectByPrimaryKey(id);
        if(!designer.getPassword().equals(MD5Utils.getMD5passwprd(oldPassword))) return 0; //旧密码错误
        //换密码
        designer.setPassword(MD5Utils.getMD5passwprd(newPassword));
        //进行修改
        return designerMapper.updateByPrimaryKey(designer);
    }
}
