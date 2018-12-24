package www.ezrpro.com.redis;

import redis.clients.jedis.Jedis;

import java.util.concurrent.TimeUnit;
/**
* 
* @auth: nanChen
* @date: 2018-12-24  17:25:23
* 
*/

public class JedisLock{
     /**
     * 默认过期时间
     */
    public static final int DEFAULT_EXPIRY_TIME_MILLIS = Integer.getInteger("com.yit.common.jedis.lock.expiry.millis",
            60 * 1000);
    /**
     * 默认重新申请锁的重试等待时间
     */
    public static final int DEFAULT_ACQUIRY_RESOLUTION_MILLIS = Integer.getInteger(
            "com.yit.common.jedis.lock.acquiry.resolution.millis", 100);
    /**
     * 分布式锁使用的redis锁命名空间
     */
    private static final String LOCK_NAMESAPACE = "_LOCK";
    private final String lockKeyPath;
    private final int lockExpiryInMillis;

    public int getLockExpiryInMillis() {
        return lockExpiryInMillis;
    }

    /**
     * Detailed constructor with default lock expiration of 60000 msecs.
     *
     * @param namespace 命名空间
     * @param lockKey   lock key (ex. account:1, ...)
     */
    public JedisLock(String namespace, String lockKey) {
        this(namespace, lockKey, DEFAULT_EXPIRY_TIME_MILLIS);
    }

    /**
     * Detailed constructor.
     *
     * @param namespace        命名空间
     * @param lockKey          lock key (ex. account:1, ...)
     * @param expiryTimeMillis lock expiration in miliseconds (default: 60000 msecs)
     */
    public JedisLock(String namespace, String lockKey, int expiryTimeMillis) {
        this.lockKeyPath = namespace + "/" + LOCK_NAMESAPACE + "/" + lockKey;
        this.lockExpiryInMillis = expiryTimeMillis;
    }

    /**
     * @return lock key path
     */
    public String getLockKeyPath() {
        return lockKeyPath;
    }

    /**
     * 0等，上锁失败立即返回false，不可重入
     * 分布式:依赖redis setnx原子操作
     * Acquire lock.
     * 注意: 如果争用到锁却不调用release函数（分布式锁自动失效），需要注意调用closeJedis，释放jedis实例
     *
     * @return true if lock is acquired, false acquire timeouted
     * @throws InterruptedException in case of thread interruption
     */
    public synchronized boolean tryLock() throws InterruptedException {
        return tryLock(0, TimeUnit.MILLISECONDS);
    }

    /**
     * 超时后返回false，不可重入
     * 分布式:依赖redis setnx原子操作
     * Acquire lock.
     * 注意: 如果争用到锁却不调用release函数（分布式锁自动失效），需要注意调用closeJedis，释放jedis实例
     *
     * @param timeout 锁超时时间
     * @param unit    时间单位
     * @return true if lock is acquired, false acquire timeouted
     * @throws InterruptedException in case of thread interruption
     */
    public synchronized boolean tryLock(int timeout, TimeUnit unit)
            throws InterruptedException {
        boolean locked = false;
        Jedis jedis = null;
        try {
            jedis = JedisClientFactory.getJedisClient();
            long timeoutMillis = unit.toMillis(timeout);
            while (timeoutMillis >= 0) {
                //NX -- Only set the key if it does not already exist.
                //XX -- Only set the key if it already exist.
                //EX seconds -- Set the specified expire time, in seconds.
                //PX milliseconds -- Set the specified expire time, in milliseconds.
                String result = jedis.set(lockKeyPath, "1", "NX", "PX",
                        lockExpiryInMillis);
                if ("OK".equalsIgnoreCase(result)) {
                    locked = true;
                    break;
                }
                //超时时间递减
                timeoutMillis -= DEFAULT_ACQUIRY_RESOLUTION_MILLIS;
                if (timeoutMillis > 0) {
                    Thread.sleep(DEFAULT_ACQUIRY_RESOLUTION_MILLIS);
                }
            }
        } catch (Exception e) {
            //
        } finally {
            //及时释放jedis链接
            forceClose(jedis);
        }
        return locked;
    }

    /**
     * 释放锁
     * 注意：release已经隐含了forceClose动作，请勿在调用release之前进行jedis实例释放
     * Acquired lock release.
     */
    public synchronized void release() {
        Jedis jedis = null;
        try {
            jedis = JedisClientFactory.getJedisClient();
            jedis.del(lockKeyPath);
        } finally {
            forceClose(jedis);
        }
    }

    /**
     * 释放jedis链接,内部创建的jedis实例需要触发释放
     * 注意：release已经隐含了forceClose动作，请勿在调用release之前进行jedis实例释放
     */
    public synchronized void forceClose(Jedis jedis) {
        if (jedis != null) {
            try {
                jedis.close();
            } catch (Exception e) {
                //
            }
        } else {
            //
        }
    }

    /**
     * 是否已被锁
     * Check if owns the lock
     *
     * @return true if lock owned
     */
    public synchronized boolean isLocked() {
        Jedis jedis = null;
        boolean isExists = false;
        try {
            jedis = JedisClientFactory.getJedisClient();
            isExists = jedis.exists(lockKeyPath);
        } finally {
            forceClose(jedis);
        }
        return isExists;
    }
}