package com.chainbell.placesearch.common.config;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import redis.embedded.RedisServer;

import java.io.IOException;
import java.util.Optional;

/*
// embedded redis 실행 설정
@Configuration
public class RedisLocalConfig implements InitializingBean, DisposableBean {

    @Value("${spring.redis.embedded.port}")
    private int redisPort;

    private RedisServer redisServer;

    @Override
    public void destroy() throws Exception {
        Optional.ofNullable(redisServer).ifPresent(RedisServer::stop);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        redisServer = RedisServer.builder().port(redisPort).setting("maxmemory 128M").build();
        try{
            redisServer.start();
        }
        catch (Exception e){
            // local redis 실행 시 오류 발생 하면 start할 필요 없음
        }
    }

}
*/