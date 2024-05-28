package gsm.devfest.domain.conference.repository;

import gsm.devfest.domain.conference.entity.ConferenceMember;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Mono;

public interface ConferenceMemberRepository extends R2dbcRepository<ConferenceMember, Long> {
    Mono<Boolean> existsByMemberIdAndConferenceId(Long memberId, Long conferenceId);
}
