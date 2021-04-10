package com.xiewende.creativehomesuppliescity.service;

import com.xiewende.creativehomesuppliescity.pojo.User;
import io.swagger.models.auth.In;

import java.util.List;

/**
 * @create 2021-04-10  11:40
 */
public interface UserService {

    //添加用户
    public Integer insertUser(User user);

    //修改密码
    public Integer updatePassword(Integer id,String oldPassword,String newPassword);

    //修改其他信息
    public Integer updateOther(User user);

    //删除
    public Integer deleteUser(Integer id);

    //查询 有条件则模糊 没有则全部
    public List<User> listUserOrName(String userName);

    //根据id查询一位用户
    public User selectUserById(Integer id);

}
