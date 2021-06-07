package com.xiewende.creativehomesuppliescity.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * @create 2021-04-17  15:55
 */
public class RandomGetString {

    public static String getOrderNum() {
        //年月日时分秒+随机数+随机数
        //减低重复得概率
        Random random = new Random();
        SimpleDateFormat allTime = new SimpleDateFormat("YYYYMMddHHmmSSS");
        String subjectno = allTime.format(new Date()) + random.nextInt(10);
        return subjectno + random.nextInt(10);
    }
}
