package gsm.devfest.common.lock.service;

import gsm.devfest.global.error.BasicException;
import org.redisson.api.RedissonReactiveClient;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.concurrent.TimeUnit;

@Component
public class DistributedLockService {
    private static final long DEFAULT_TIMEOUT = 5000L;
    private static final long DEFAULT_LEASE_TIME = 5000L;

    private final RedissonReactiveClient redissonReactiveClient;

    public DistributedLockService(RedissonReactiveClient redissonReactiveClient) {
        this.redissonReactiveClient = redissonReactiveClient;
    }

    public Mono<Boolean> acquireLock(String key, long threadId) {
        return redissonReactiveClient.getLock(key)
                .tryLock(
                        DEFAULT_TIMEOUT,
                        DEFAULT_LEASE_TIME,
                        TimeUnit.MILLISECONDS,
                        threadId
                )
                .map(lockAcquired -> {
                    if (lockAcquired) {
                        return true;
                    } else {
                        throw new BasicException("Failed to acquire lock for key," + key, HttpStatus.BAD_REQUEST);
                    }
                });
    }

    public Mono<Void> releaseLock(String key, long threadId) {
        return redissonReactiveClient.getLock(key)
                .unlock(threadId)
                .then();
    }
}
