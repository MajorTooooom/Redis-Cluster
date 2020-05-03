package com.dororo.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;


@Configuration
public class RedisConfig {
    /**
     * 此bean对应的是类RedisAutoConfiguration里面的方法bean
     *
     * @param factory
     * @return
     */
    @Bean
    @SuppressWarnings("all")
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(factory);//连接工厂，
        //序列化配置：代码意思是用jackson去解析任意的对象（参数object.class）
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<Object>(Object.class);
        ObjectMapper om = new ObjectMapper();//工具类，可以将对象转义成json字符串
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);//设置参数
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);
        //此处使用String的序列化
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        //下面对来来回回的数据进行设置
        template.setKeySerializer(stringRedisSerializer);//传递的key采用String的序列化方式
        template.setHashKeySerializer(stringRedisSerializer);//传递的hash的也key采用String的序列化方式
        template.setValueSerializer(jackson2JsonRedisSerializer);//传递的value序列化方式采用jackson
        template.setHashValueSerializer(jackson2JsonRedisSerializer);//传递的hash的value序列化方式也采用jackson
        template.afterPropertiesSet();

        return template;
    }


}
