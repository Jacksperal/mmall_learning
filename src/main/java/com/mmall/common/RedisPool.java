package com.mmall.common;

import com.mmall.util.MyPropertiesUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisPool {
    //jedis连接池
    private static JedisPool pool;
    //最大连接数
    private static Integer maxTotal = Integer.parseInt(MyPropertiesUtil.getProperty("redis.max.total", "20"));
    //在jedispool中最大idel空闲状态的jedis实例的个数
    private static Integer maxIdle = Integer.parseInt(MyPropertiesUtil.getProperty("redis.max.idle", "10"));
    //在jedispool中最小idel空闲状态的jedis实例的个数
    private static Integer minIdle = Integer.parseInt(MyPropertiesUtil.getProperty("redis.min.idle", "2"));
    //在borrow一个jedis实例的时候，是否要进行验证操作，如果赋值为true，则得到的jedis实例肯定是可以用的
    private static Boolean testOnBorrow = Boolean.parseBoolean(MyPropertiesUtil.getProperty("redis.test.borrow", "true"));
    //在return一个jedis实例的时候，是否要进行验证操作，如果赋值为true，则得到的jedis实例肯定是可以用的
    private static Boolean testOnReturn = Boolean.parseBoolean(MyPropertiesUtil.getProperty("redis.test.return", "true"));

    private static String redisIp = MyPropertiesUtil.getProperty("redis.ip");

    private static Integer redisPort = Integer.parseInt(MyPropertiesUtil.getProperty("redis.port"));

    static {
        initPool();
    }

    private static void initPool() {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(maxTotal);
        config.setMaxIdle(maxIdle);
        config.setMinIdle(minIdle);
        config.setTestOnBorrow(testOnBorrow);
        config.setTestOnReturn(testOnReturn);

        //设置连接耗尽的时候，是否阻塞，false会抛出异常，true阻塞知道超时,默认为true
        config.setBlockWhenExhausted(true);

        pool = new JedisPool(config, redisIp, redisPort, 1000 * 2);
    }

    public static Jedis getJedis() {
        Jedis jedis = pool.getResource();
        return jedis;
    }

    public static void returnResource(Jedis jedis) {
        pool.returnResource(jedis);
    }

    public static void returnBrokenResource(Jedis jedis) {
        pool.returnBrokenResource(jedis);
    }

    public static void main(String[] args) {
        Jedis jedis = RedisPool.getJedis();
        jedis.set("lizhe","yangjing");
        RedisPool.returnResource(jedis);
    }
}
