package com.xiewende.creativehomesuppliescity.service;

import com.xiewende.creativehomesuppliescity.pojo.Designer;
import com.xiewende.creativehomesuppliescity.pojo.User;

/**
 * @create 2021-04-19  11:47
 */
public interface LoginService {

    //普通用户登录
    public User userLogin(String userName,String password);

    //设计师登录
    public Designer designerLogin(String designerName,String password);

    //管理员登录
    public Integer adminLogin(String adminName,String psssword);
}
