package gsm.devfest.common.aop;

import gsm.devfest.global.error.BasicException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Component
@Slf4j
public class TransactionAspect {

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Mono<Object> proceed(final ProceedingJoinPoint joinPoint) {
        return Mono.fromCallable(() -> {
            try {
                return joinPoint.proceed();
            } catch (Throwable throwable) {
                throw new BasicException(throwable.getMessage(), HttpStatus.BAD_REQUEST);
            }
        }).subscribeOn(Schedulers.boundedElastic());
    }
}
