package com.xiewende.creativehomesuppliescity;

import com.xiewende.creativehomesuppliescity.mapper.*;
import com.xiewende.creativehomesuppliescity.pojo.User;
import com.xiewende.creativehomesuppliescity.utils.ConstantProperties;
import com.xiewende.creativehomesuppliescity.utils.MD5Utils;
import com.xiewende.creativehomesuppliescity.utils.RandomGetString;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


@SpringBootTest
class CreativehomesuppliescityApplicationTests {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ConstantProperties properties;

    @Autowired
    private CategorySecondMapper categorySecondMapper;

    @Autowired
    private StyleMapper styleMapper;
    @Autowired
    private BrandMapper brandMapper;

    @Autowired
    private GoodsMapper goodsMapper;




    //测试添加一个用户
    @Test
    void contextLoads() {
        //建立一个用户
        User user = new User();
        user.setUserName("张三");
        user.setPassword("123456");
        user.setIphone("17876208191");
        user.setGender(0); //0男1女
        user.setEmail("2276230432@qq.com");
        user.setAddress("广东省河源市连平县上坪镇小水村");
        user.setImage("我的小头像");

        int insert = userMapper.insert(user);
        System.out.println(insert);
        //实现添加一条数据后，可以在这个对象上实时拿到数据库的自增逐渐id的值，在订单那边需要
        //要在xml，对应修改一点设置
        System.out.println(user.getId());

    }

    //测试常量
    @Test
    void test1(){
        System.out.println(properties.getName());
    }

    //测试MD5 utils
    @Test
    void test2(){
        System.out.println(MD5Utils.getMD5passwprd("000000"));
    }

    //模糊查询id
    @Test
    void test3(){
       /* List<Integer> list = categorySecondMapper.selectIdOfLikeCategoryName("电");
        System.out.println(list.toString());

        List<Integer> list1 = styleMapper.selectIdOfLikeStyleName("风");
        System.out.println(list1.toString());

        List<Integer> list2 = brandMapper.selectIdOfLikeBrandName("具");
        System.out.println(list2.toString());

        List<Integer> list3 = categorySecondMapper.selectIdOfLikeFirstName("客");
        System.out.println(list3.toString());*/
        List<Integer> list = userMapper.selectIdOfLikeUserName("王");
        System.out.println(list.toString());

        List<Integer> list1 = goodsMapper.selectIdOfLikeGoodName("餐");
        System.out.println(list1.toString());

    }

    @Test
    void test5(){
        String s = RandomGetString.getOrderNum();
        System.out.println(s);

        String s1 = RandomGetString.getOrderNum();
        System.out.println(s1);
    }

    //时间格式的转换
    @Test
    void test6(){
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(currentTime);
        System.out.println(dateString);
    }

}
