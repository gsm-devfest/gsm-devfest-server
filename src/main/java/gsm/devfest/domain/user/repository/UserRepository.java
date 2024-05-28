package gsm.devfest.domain.user.repository;

import gsm.devfest.domain.user.entity.User;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface UserRepository extends ReactiveCrudRepository<User, Long> {
    Mono<Boolean> findByEmail(String email);
}
