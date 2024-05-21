package gsm.devfest.domain.conference.repository;

import gsm.devfest.domain.conference.entity.Conference;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

public interface ConferenceRepository extends R2dbcRepository<Conference, Long> {
}
