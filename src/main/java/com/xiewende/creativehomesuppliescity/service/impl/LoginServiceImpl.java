package com.xiewende.creativehomesuppliescity.service.impl;

import com.xiewende.creativehomesuppliescity.mapper.DesignerMapper;
import com.xiewende.creativehomesuppliescity.mapper.UserMapper;
import com.xiewende.creativehomesuppliescity.pojo.Designer;
import com.xiewende.creativehomesuppliescity.pojo.DesignerExample;
import com.xiewende.creativehomesuppliescity.pojo.User;
import com.xiewende.creativehomesuppliescity.pojo.UserExample;
import com.xiewende.creativehomesuppliescity.service.LoginService;
import com.xiewende.creativehomesuppliescity.utils.ConstantProperties;
import com.xiewende.creativehomesuppliescity.utils.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.security.provider.MD5;

import java.util.List;

/**
 * @create 2021-04-19  12:08
 */
@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private DesignerMapper designerMapper;

    @Autowired
    private ConstantProperties properties;

    @Override
    public User userLogin(String userName, String password) {

        //判断用户在不在
        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        criteria.andUserNameEqualTo(userName);
        List<User> users = userMapper.selectByExample(userExample);
        if(users == null || users.size() == 0) return null;

        //判断密码是否正确
        password = MD5Utils.getMD5passwprd(password);
        User user = users.get(0);
        if(!password.equals(user.getPassword()))return null;

        return user;
    }

    @Override
    public Designer designerLogin(String designerName, String password) {
        DesignerExample designerExample = new DesignerExample();
        DesignerExample.Criteria criteria = designerExample.createCriteria();
        criteria.andDesignerNameEqualTo(designerName);
        List<Designer> designers = designerMapper.selectByExample(designerExample);
        if(designers == null || designers.size() == 0) return null;

        password = MD5Utils.getMD5passwprd(password);
        Designer designer = designers.get(0);
        if(!password.equals(designer.getPassword())) return null;
        return designer;
    }

    @Override
    public Integer adminLogin(String adminName, String psssword) {
        if(adminName.equals(properties.getAdminName()) && psssword.equals(properties.getAdminPassword())){
            return 1;
        }
        return 0;
    }
}
