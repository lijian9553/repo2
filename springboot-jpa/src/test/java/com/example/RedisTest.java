package com.example;

import com.example.domain.User;
import com.example.repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringbootJpaApplication.class)
public class RedisTest {

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void redisTest() throws JsonProcessingException {
        String s = redisTemplate.boundValueOps("user.findAll").get();
        if (null == s){
            System.out.println("redis中暂时没有数据，从数据中去查询");
            List<User> list = userRepository.findAll();
            ObjectMapper objectMapper = new ObjectMapper();
            s = objectMapper.writeValueAsString(list);
            redisTemplate.boundValueOps("user.findAll").set(s);
        }else {
            System.out.println("从缓存中获取数据");
        }
        System.out.println(s);

    }
}
