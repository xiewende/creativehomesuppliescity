package com.xiewende.creativehomesuppliescity.service.impl;

import com.xiewende.creativehomesuppliescity.mapper.UserMapper;
import com.xiewende.creativehomesuppliescity.pojo.User;
import com.xiewende.creativehomesuppliescity.pojo.UserExample;
import com.xiewende.creativehomesuppliescity.service.UserService;
import com.xiewende.creativehomesuppliescity.utils.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @create 2021-04-10  11:42
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    //添加用户
    public Integer insertUser(User user) {

        //查重 用户名是唯一的
        UserExample userExample = new UserExample();
        UserExample.Criteria userExampleCriteria = userExample.createCriteria();
        userExampleCriteria.andUserNameEqualTo(user.getUserName());
        userExampleCriteria.andIsdeleteEqualTo(0); //没有删除
        List<User> userList = userMapper.selectByExample(userExample);
        //若存在
        if(userList.size()>0) return 0;
        //若不存在添加
        return userMapper.insertSelective(user);
    }

    @Override
    public Integer updatePassword(Integer id, String oldPassword, String newPassword) {
        //先判断旧密码是否输入正确
        User user = userMapper.selectByPrimaryKey(id);
        if(!user.getPassword().equals(MD5Utils.getMD5passwprd(oldPassword))) return 0; //旧密码错误
        //换密码
        user.setPassword(MD5Utils.getMD5passwprd(newPassword));
        //进行修改
        return userMapper.updateByPrimaryKey(user);
    }

    @Override
    public Integer updateOther(User user) {

        //查看是否重复  名字
        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        criteria.andUserNameEqualTo(user.getUserName());
        criteria.andIsdeleteEqualTo(0); //没有删除
        criteria.andIdNotEqualTo(user.getId());
        List<User> list = userMapper.selectByExample(userExample);
        if(list.size()>0) return 0; //有重复

        //直接修改
        return userMapper.updateByPrimaryKey(user);
    }

    @Override
    public Integer deleteUser(Integer id) {
        //逻辑 删除
        User user = userMapper.selectByPrimaryKey(id);
        user.setIsdelete(1);
        return userMapper.updateByPrimaryKey(user);
    }

    @Override
    public List<User> listUserOrName(String userName) {
        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        criteria.andIsdeleteEqualTo(0);//没有删除的
        //若有userName ，则按照条件查询
        if(userName != null){
            criteria.andUserNameLike("%"+userName+"%");
        }
        //执行查询
        List<User> list = userMapper.selectByExample(userExample);
        return list;
    }

    @Override
    public User selectUserById(Integer id) {
        return userMapper.selectByPrimaryKey(id);
    }
}
