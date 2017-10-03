package ninja.sakib.restfulassessment.caches;

import com.lambdaworks.redis.RedisClient;
import com.lambdaworks.redis.api.StatefulRedisConnection;
import com.lambdaworks.redis.api.sync.RedisStringCommands;

public class CacheManager {
    private static CacheManager cacheManager = new CacheManager();

    public static CacheManager get() {
        return cacheManager;
    }

    private String redisUri = "redis://128.199.95.1";
    private RedisStringCommands commands;

    private CacheManager() {
        RedisClient redisClient = RedisClient.create(redisUri);
        StatefulRedisConnection redisConnection = redisClient.connect();
        commands = redisConnection.sync();
    }

    public void set(String key, String value, int timeInSeconds) {
        commands.setex(key, timeInSeconds, value);
    }

    public String get(String key) {
        return (String) commands.get(key);
    }
}
