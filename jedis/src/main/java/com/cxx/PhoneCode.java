package com.cxx;

import org.junit.Test;
import redis.clients.jedis.Jedis;

import java.util.Random;

/**
 * @author 陈喜喜
 * @date 2022-08-11 9:37
 */
public class PhoneCode {
    public static void main(String[] args) {
        verify("18507970088");


    }

    //验证码校验
    public static void getRedisCode(String PhoneNumber, String PhoneCode){
        Jedis jedis = new Jedis("192.168.223.128", 6379);

        String codeKey = PhoneNumber + ":code";
        if (jedis.get(codeKey).equals(PhoneCode)) {
            System.out.println("超过");
        }else {
            System.out.println("失败");
        }
        jedis.close();
    }


    //每个手机号码每天只能发送3次，验证码有效实践2分钟
    public static void verify(String PhoneNumber){
        Jedis jedis = new Jedis("192.168.223.128", 6379);

        String countKey = PhoneNumber + ":count";
        String codeKey = PhoneNumber + ":code";

        String count = jedis.get(countKey);
        if (count == null) {
            jedis.setex(countKey,24*60*60,"1");
        }else if(Integer.parseInt(count) <= 2){
            jedis.incr(codeKey);
        }else {
            System.out.println("次数今日超过三次");
            jedis.close();
            return;
        }

        jedis.setex(codeKey,120,getPhoneCode());
        jedis.close();
    }

    //生成6位数字验证码
    public static String getPhoneCode() {
        Random random = new Random();
        StringBuffer phoneCode = new StringBuffer();
        for (int i = 0; i < 6; i++) {
            int rand = random.nextInt(10);
            phoneCode.append(rand);
        }
        return phoneCode.toString();
    }

    @Test
    public void testPhoneCode(){

    }
}
