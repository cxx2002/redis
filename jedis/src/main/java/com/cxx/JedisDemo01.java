package com.cxx;

import org.junit.Test;
import redis.clients.jedis.Jedis;

import java.util.List;
import java.util.Set;

/**
 * @author 陈喜喜
 * @date 2022-08-10 20:52
 */
public class JedisDemo01 {

    public static void main(String[] args) {
        Jedis jedis = new Jedis("192.168.223.128", 6379);
        String s = jedis.ping();
        System.out.println("s=" + s);
    }

    @Test
    public void test() {
        Jedis jedis = new Jedis("192.168.223.128", 6379);

        jedis.set("name", "lucy");


        jedis.mset("name2", "jack", "name3", "Tom", "k3", "v3");
        List<String> mget = jedis.mget("name","name2","name3","k1","k2");
        mget.forEach(value -> System.out.println("value=" + value));

        Set<String> keys = jedis.keys("*");
        keys.forEach(key -> System.out.println("key: " + key));
    }

}
