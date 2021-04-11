package com.xiewende.creativehomesuppliescity.service;

import com.xiewende.creativehomesuppliescity.pojo.Designer;
import io.swagger.models.auth.In;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.List;

/**
 * @create 2021-04-10  21:13
 */
public interface DesignerService {

    //添加设计师
    public Integer InsertDesigner(Designer designer);

    //修改
    public Integer updateDesigner(Designer designer);

    //删除
    public Integer deteleDesigner(Integer id);

    //查询，根据职称和风格进行条件查询 模糊查询
    public List<Designer> listDesigner(String professionalTitle,String type);

    //根据id查询一个设计师
    public Designer selectDesignerById(Integer id);

    //查询设计师详情
    public Designer selectDesignerDeatils(Integer id);

    //修改密码
    public Integer updatePassword(Integer id,String oldPassword,String newPassword);
}
