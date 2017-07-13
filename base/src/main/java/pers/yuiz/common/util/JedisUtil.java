package pers.yuiz.common.util;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import pers.yuiz.customer.vo.LoginInfo;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;

public class JedisUtil {
    private final static Logger logger = LoggerFactory.getLogger(JedisUtil.class);
    /**
     * ip地址
     */
    private static String ADDR;

    /**
     * 端口号
     */
    private static int PORT;

    /**
     * 密码
     */
    private static String AUTH;

    /**
     * 最大连接数
     */
    private static int MAX_ACTIVE;

    /**
     * 最大空闲连接数, 默认8个
     * 控制一个pool最多有多少个状态为idle(空闲的)的jedis实例。
     */
    private static int MAX_IDLE;

    /**
     * 获取连接时的最大等待毫秒数(如果设置为阻塞时BlockWhenExhausted),
     * 如果超时就抛异常, 小于零:阻塞不确定的时间, 默认-1
     */
    private static int MAX_WAIT;
    /**
     * 最大连接数, 默认8个
     */
    private static int MAX_TOTAL;

    /**
     * 超时时间
     */
    private static int TIMEOUT;
    /**
     * 表示当borrow(引入)一个jedis实例时，最大的等待时间，
     * 如果超过等待时间，则直接抛出JedisConnectionException；
     */
    private static Long MAX_WAIT_MILLIS;

    /**
     * 在获取连接的时候检查有效性, 默认false
     */
    private static boolean TEST_ON_BORROW;

    /**
     * jedis连接池
     */
    private static JedisPool jedisPool = null;

    private JedisUtil() {
    }

    static {
        logger.info("初始化jedis连接池");
        InputStream inputStream = null;
        Properties properties = new Properties();
        try {
            inputStream = new ClassPathResource("redis.properties").getInputStream();
            properties.load(inputStream);
            ADDR = properties.getProperty("addr");
            PORT = Integer.parseInt(properties.getProperty("port"));
            AUTH = properties.getProperty("auth");
            MAX_ACTIVE = Integer.parseInt(properties.getProperty("maxActive"));
            MAX_IDLE = Integer.parseInt(properties.getProperty("maxIdle"));
            MAX_WAIT = Integer.parseInt(properties.getProperty("maxWait"));
            TIMEOUT = Integer.parseInt(properties.getProperty("timeOut"));
            TEST_ON_BORROW = Boolean.parseBoolean(properties.getProperty("testOnBorrow"));
            MAX_TOTAL = Integer.parseInt(properties.getProperty("maxTotal"));
            MAX_WAIT_MILLIS = Long.parseLong(properties.getProperty("maxWaitMillis"));
            JedisPoolConfig config = new JedisPoolConfig();
            //连接耗尽时是否阻塞, false报异常,ture阻塞直到超时, 默认true
            config.setBlockWhenExhausted(true);
            //设置的逐出策略类名, 默认DefaultEvictionPolicy(当连接超过最大空闲时间,或连接数超过最大空闲连接数)
            config.setEvictionPolicyClassName("org.apache.commons.pool2.impl.DefaultEvictionPolicy");
            //是否启用pool的jmx管理功能, 默认true
            config.setJmxEnabled(true);
            config.setMaxIdle(MAX_IDLE);
            config.setMaxTotal(MAX_TOTAL);
            config.setMaxWaitMillis(MAX_WAIT_MILLIS);
            config.setTestOnBorrow(TEST_ON_BORROW);
            //在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；
            config.setTestOnBorrow(true);
            jedisPool = new JedisPool(config, ADDR, PORT, TIMEOUT, AUTH);
            logger.info("初始化jedis连接池成功");
        } catch (IOException e) {
            logger.info("初始化jedis连接池失败");
            e.printStackTrace();
            logger.error(e.getLocalizedMessage());
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    logger.error(e.getLocalizedMessage());
                }
            }
        }
    }

    /**
     * 获取Jedis实例
     *
     * @return
     */
    public static Jedis createJedis() {
        Jedis jedis = null;
        try {
            if (jedisPool != null) {
                jedis = jedisPool.getResource();
            }
            return jedis;
        } catch (Exception e) {
            e.printStackTrace();
            return jedis;
        }
    }

    /**
     * 释放jedis
     *
     * @param jedis
     */
    public static void returnResource(Jedis jedis) {
        if (jedis != null) {
            jedis.close();
        }
    }

    public static void setex(String key, int seconds, Object object) {
        Jedis jedis = null;
        try {
            String value = "";
            jedis = createJedis();
            if (object instanceof String) {
                value = (String) object;
            } else {
                value = JSON.toJSONString(object);
            }
            jedis.setex(key, seconds, value);
        } finally {
            returnResource(jedis);
        }
    }

    public static void set(String key, Object object) {
        Jedis jedis = null;
        try {
            String value = "";
            jedis = createJedis();
            if (object instanceof String) {
                value = (String) object;
            } else {
                value = JSON.toJSONString(object);
            }
            jedis.set(key, value);
        } finally {
            returnResource(jedis);
        }
    }

    public static String get(String key) {
        Jedis jedis = null;
        try {
            jedis = createJedis();
            String value = jedis.get(key);
            return value;
        } finally {
            returnResource(jedis);
        }
    }

    public static void del(String key) {
        Jedis jedis = null;
        try {
            jedis = createJedis();
            jedis.del(key);
        } finally {
            returnResource(jedis);
        }
    }

    public static String set(Object object) {
        Jedis jedis = null;
        try {
            String value = "";
            jedis = createJedis();
            if (object instanceof String) {
                value = (String) object;
            } else {
                value = JSON.toJSONString(object);
            }
            String key = UUID.randomUUID().toString();
            while (jedis.exists(key)) {
                key = UUID.randomUUID().toString();
            }
            jedis.set(key, value);
            return key;
        } finally {
            returnResource(jedis);
        }
    }

    public static String setex(int seconds, Object object) {
        Jedis jedis = null;
        try {
            String value = "";
            jedis = createJedis();
            if (object instanceof String) {
                value = (String) object;
            } else {
                value = JSON.toJSONString(object);
            }
            String key = UUID.randomUUID().toString();
            while (jedis.exists(key)) {
                key = UUID.randomUUID().toString();
            }
            jedis.setex(key, seconds, value);
            return key;
        } finally {
            returnResource(jedis);
        }
    }
}
