package com.example.ezkeeper.cache;

import com.example.ezkeeper.Util.SpringUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.cache.Cache;
import org.apache.shiro.util.CollectionUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;

import java.applet.AppletContext;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Mybatis二级缓存
 */
@Slf4j
public class MybatisRedisCache implements Cache {

    private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    private final String id;
    private RedisTemplate redisTemplate;
    private static final long EXPIRE_TIME_IN_MINUTES = 30; // redis过期时间


    public MybatisRedisCache(String id){
        if(id == null) throw new IllegalArgumentException("Cache instances require an ID");
        this.id = id;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void putObject(Object key, Object value) {
        try {
            redisTemplate = getRedisTemplate();
            if (value != null){
                redisTemplate.opsForValue().set(key.toString(), value, EXPIRE_TIME_IN_MINUTES, TimeUnit.MINUTES);
            }
        }catch (Throwable t){
            log.error("Redis put failed",t);
        }
    }

    @Override
    public Object getObject(Object key) {
        try{
            redisTemplate = getRedisTemplate();
            return redisTemplate.opsForValue().get(key.toString());
        }catch (Throwable t){
            log.error("Redis get failed",t);
            return null;
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public Object removeObject(Object key) {
        try{
            redisTemplate = getRedisTemplate();
            redisTemplate.delete(key.toString());
        }catch (Throwable t){
            log.error("Redis remove failed",t);
        }
        return null;
    }

    @Override
    public void clear() {
        redisTemplate = getRedisTemplate();
        Set<String> keys = redisTemplate.keys("*:"+this.id+"*");
        if(!CollectionUtils.isEmpty(keys)){
            redisTemplate.delete(keys);
        }
        log.debug("Clear all the cached query result from redis");
    }

    @Override
    public int getSize() {
        return 0;
    }

    @Override
    public ReadWriteLock getReadWriteLock() {
        return readWriteLock;
    }

    private RedisTemplate getRedisTemplate(){
        if(redisTemplate == null) redisTemplate = SpringUtil.getBean("redisTemplate");
        return redisTemplate;
    }
}
