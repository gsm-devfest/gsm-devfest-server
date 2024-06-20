package gsm.devfest.common.lock.aop;

import gsm.devfest.common.lock.service.DistributedLockService;
import gsm.devfest.domain.lecture.data.RegisterLectureRequest;
import gsm.devfest.global.error.BasicException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.concurrent.ThreadLocalRandom;

@Aspect
@Component
@Slf4j
public class DistributedLockAspect {

    private final DistributedLockService distributedLockService;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public DistributedLockAspect(DistributedLockService distributedLockService) {
        this.distributedLockService = distributedLockService;
    }

    @Around("@annotation(gsm.devfest.common.lock.annotation.DistributedLock)")
    public Mono<Object> lock(ProceedingJoinPoint joinPoint) {
        String lockKey = getLockKey(joinPoint.getArgs());
        long threadId = ThreadLocalRandom.current().nextLong();
        return distributedLockService.acquireLock(lockKey, threadId)
                .flatMap(lockAcquired -> {
                    if (lockAcquired) {
                        log.info("Lock acquired: {}, threadId: {}", lockKey, threadId);
                        try {
                            return (Mono<Object>) joinPoint.proceed();
                        } catch (Throwable throwable) {
                            return Mono.error(throwable);
                        } finally {
                            distributedLockService.releaseLock(lockKey, threadId)
                                    .doOnSuccess(unused -> logger.info("Lock released: {}, threadId: {}", lockKey, threadId))
                                    .subscribe();
                        }
                    } else {
                        log.debug("Failed to acquire lock: {}", lockKey);
                        return Mono.empty();
                    }
                })
                .switchIfEmpty(Mono.error(new BasicException("Failed get Lock", HttpStatus.BAD_REQUEST)));
    }

    private String getLockKey(Object[] args) {
        if (args.length == 0) {
            throw new BasicException("Arguments must not be empty", HttpStatus.BAD_REQUEST);
        }
        Object arg = args[0];
        if (arg instanceof String) {
            return (String) arg;
        } else if (arg instanceof RegisterLectureRequest) {
            RegisterLectureRequest request = (RegisterLectureRequest) arg;
            Long lectureId = request.getLectureId();
            if (lectureId == null) {
                throw new BasicException("LectureId must not be null", HttpStatus.BAD_REQUEST);
            }
            return "lecture" + lectureId;
        } else {
            throw new BasicException("Unsupported argument type: " + arg.getClass().getName(), HttpStatus.BAD_REQUEST);
        }
    }

}
