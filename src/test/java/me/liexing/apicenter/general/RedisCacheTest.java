package me.liexing.apicenter.general;

import me.liexing.apicenter.general.service.SkService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisCacheTest {

    @Autowired
    private SkService skService;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @Test
    public void testRedisCache(){
        boolean result = skService.isValid("1234567890", "http://127.0.0.1");
        System.out.println(result);
    }
}
