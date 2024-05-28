package gsm.devfest.domain.conference.repository;

import gsm.devfest.domain.conference.entity.ConferenceRequest;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Mono;

public interface ConferenceRequestRepository extends R2dbcRepository<ConferenceRequest, Long> {
    Mono<ConferenceRequest> findByUserId(Long userId);
    Mono<Boolean> existsByUserId(Long userId);
}
