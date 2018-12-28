package www.ezrpro.com.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Protocol;

/**
* 
* @auth: nanChen
* @date: 2018-12-24  16:44:52
* 
*/

public class JedisClientFactory {

    static {
        //new JedisConfig();
    }

    public static Jedis getJedisClient() {
        return JedisPoolHolder.getJedisPool().getResource();
    }

    private static class JedisPoolHolder {
        private static JedisPool jedisPool;
        private static void setJedisPool(String redisHost, int redisPort, String redisPassword, int redisDatabase) {
            JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
            jedisPoolConfig.setMaxIdle(10);
            jedisPoolConfig.setMaxTotal(50);
            jedisPoolConfig.setMaxWaitMillis(2000);
            jedisPoolConfig.setTestOnBorrow(false);
            jedisPoolConfig.setTestOnCreate(false);
            jedisPoolConfig.setTestOnReturn(false);
            jedisPoolConfig.setTimeBetweenEvictionRunsMillis(2000);
            jedisPool = new JedisPool(jedisPoolConfig,redisHost,redisPort, Protocol.DEFAULT_TIMEOUT,redisPassword,redisDatabase);
        }

        static {
            //setRedisHost(JedisConfig.getRedisHost());
//            setRedisPort(JedisConfig.getRedisPort());
//            if (null != JedisConfig.getRedisPassword() && !JedisConfig.getRedisPassword().isEmpty()) {
//                setRedisPassword(JedisConfig.getRedisPassword());
//            }
//            setRedisDatabase(JedisConfig.getRedisDatabase());
            setJedisPool("192.168.12.42",6379, null,0);
        }

        public static JedisPool getJedisPool() {
            return jedisPool;
        }
    }
}