package org.mxwj.cache;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.cache.Cache;
import org.mxwj.utils.ApplicationContextUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Slf4j
public class RedisCache implements Cache {

    private String id;

    public RedisCache(String id) {
        this.id = id;
    }

    @Override
    public String getId() {
        log.info("当前缓存的id:[{}}]", id);
        return this.id;
    }

    @Override//放入Redis缓存
    public void putObject(Object key, Object value) {
        log.info("放入缓存的key：[{}] 放入缓存的value:[{}]", key, value);
        getRedisTemplate().opsForHash().put(id, key.toString(), value);

    }

    @Override//从Redis缓存获取
    public Object getObject(Object key) {
        log.info("获取缓存的key：[{}] 放入缓存的value:[{}]", key);
        return getRedisTemplate().opsForHash().get(id,key.toString());
    }

    @Override//删除指定缓存信息
    public Object removeObject(Object o) {
        return null;
    }

    @Override//清除缓存
    public void clear() {
        log.info("清除所有缓存...");
        getRedisTemplate().delete(id);
    }

    @Override
    public int getSize() {
        return getRedisTemplate().opsForHash().size(id).intValue();
    }

    //封装获取redisTemplate的方法
    public RedisTemplate getRedisTemplate() {
        RedisTemplate redisTemplate = (RedisTemplate) ApplicationContextUtils.getBean("redisTemplate"); //TODO Two
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());

        return redisTemplate;
    }
}
