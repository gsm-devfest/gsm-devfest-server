package gsm.devfest.common.aop;

import gsm.devfest.common.annotation.DistributedLock;
import gsm.devfest.global.error.BasicException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.redisson.api.RLockReactive;
import org.redisson.api.RedissonClient;
import org.redisson.api.RedissonReactiveClient;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.lang.reflect.Method;

@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
public class DistributedLockAspect {
    private static final String REDISSON_LOCK_PREFIX = "LOCK:";

    private final RedissonClient redissonClient;
    private final TransactionAspect transactionAspect;

    @Around("@annotation(gsm.devfest.common.annotation.DistributedLock)")
    public Mono<Object> lock(final ProceedingJoinPoint joinPoint) {
        RedissonReactiveClient redisson = redissonClient.reactive();
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        DistributedLock distributedLock = method.getAnnotation(DistributedLock.class);

        String key = REDISSON_LOCK_PREFIX + getDynamicValue(signature.getParameterNames(), joinPoint.getArgs(), distributedLock.key());
        RLockReactive rLock = redisson.getLock(key);

        return rLock.tryLock(distributedLock.waitTime(), distributedLock.leaseTime(), distributedLock.timeUnit())
                .flatMap(available -> {
                    if (!available) {
                        return Mono.error(new BasicException("Not Avaliable Lock", HttpStatus.BAD_REQUEST));
                    }
                    return transactionAspect.proceed(joinPoint);
                })
//                .doFinally(signalType -> rLock.unlock().subscribe(
//                        null, e -> {
//                            if (e instanceof IllegalMonitorStateException) {
//                                log.info("Distributed Lock Already Unlocked {} {}", method.getName(), key);
//                            } else {
//                                log.error("Failed to unlock {} {}", method.getName(), key, e);
//                            }
//                        }))
                .doFinally(status -> rLock.unlock())
                .onErrorResume(e -> {
                    if (e instanceof InterruptedException) {
                        return Mono.error(new BasicException("Interrupted Error Lock", HttpStatus.BAD_REQUEST));
                    }
                    return Mono.error(e);
                });
    }

    public static Object getDynamicValue(String[] parameterNames, Object[] args, String key) {
        ExpressionParser parser = new SpelExpressionParser();
        StandardEvaluationContext context = new StandardEvaluationContext();

        for (int i = 0; i < parameterNames.length; i++) {
            context.setVariable(parameterNames[i], args[i]);
        }
        return parser.parseExpression(key).getValue(context, Object.class);
    }
}
