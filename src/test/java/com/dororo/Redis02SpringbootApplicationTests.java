package com.dororo;

import com.dororo.pojo.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;


@SpringBootTest
class Redis02SpringbootApplicationTests {
    @Autowired
    @Qualifier("redisTemplate")
    private RedisTemplate redisTemplate;

    @Test
    void contextLoads() {
        redisTemplate.opsForValue().set("k33", "啊实打实大撒打算大所多");
        System.out.println(redisTemplate.opsForValue().get("k33"));
    }

    @Test
    public void test() throws JsonProcessingException {
        User user = new User("程锋", 3);
//        String jsonUser = new ObjectMapper().writeValueAsString(user);
//        redisTemplate.opsForValue().set("user", jsonUser);
        redisTemplate.opsForValue().set("user", user);
        Object result = redisTemplate.opsForValue().get("user");
        System.out.println(result);
    }

}
