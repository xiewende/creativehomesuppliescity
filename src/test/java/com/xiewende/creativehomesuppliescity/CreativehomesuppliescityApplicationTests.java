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
import java.util.Random;


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

    @Autowired
    private DesignerMapper designerMapper;




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
  /*      List<Integer> list = categorySecondMapper.selectIdOfLikeCategoryName("电");
        System.out.println(list.toString());

        String str = "工业";
        List<Integer> list1 = styleMapper.selectIdOfLikeStyleName(str);
        System.out.println(list1.toString());

        List<Integer> list2 = brandMapper.selectIdOfLikeBrandName("具");
        System.out.println(list2.toString());

        List<Integer> list3 = categorySecondMapper.selectIdOfLikeFirstName("客");
        System.out.println(list3.toString());*/
       /* List<Integer> list = userMapper.selectIdOfLikeUserName("王");
        System.out.println(list.toString());

        List<Integer> list1 = goodsMapper.selectIdOfLikeGoodName("餐");
        System.out.println(list1.toString());*/

        List<Integer> list = designerMapper.selectIdOfLikeDesignerName("hh");
        System.out.println(list.size());
        System.out.println(list.toString());

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
//        Date currentTime = new Date();
//        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        String dateString = formatter.format(currentTime);
//        System.out.println(dateString);
//        String logisticsMame;
//        Random random = new Random();
//        int i = random.nextInt(5);
//        if(i == 1) logisticsMame = "顺丰";
//        else if(i==2)  logisticsMame = "圆通";
//        else if(i==3)  logisticsMame = "中通";
//        else if(i==4)  logisticsMame = "韵达";
//        else logisticsMame = "邮政"; //默认物流
//        System.out.println(logisticsMame);

        Random random = new Random();
        SimpleDateFormat allTime = new SimpleDateFormat("YYYYMMddHHmmSSS");
        String subjectno = allTime.format(new Date()) + random.nextInt(10);
        String subjectno1 = allTime.format(new Date());
        System.out.println(subjectno);
        System.out.println(subjectno1);
        System.out.println(random.nextInt(10));
    }

}
