package org.mxwj.ems_vue;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;

import javax.annotation.Resource;

@SpringBootTest
class EmsVueApplicationTests {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Resource


    @Test
    void contextLoads() {
        stringRedisTemplate.opsForValue().set("hello", "world");
    }

}
