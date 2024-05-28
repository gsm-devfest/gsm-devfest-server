package gsm.devfest.domain.conference.validator;

import gsm.devfest.domain.conference.data.RegisterConferencePresenterRequest;
import gsm.devfest.domain.conference.entity.ConferenceRequest;
import gsm.devfest.domain.conference.repository.ConferenceRepository;
import gsm.devfest.global.error.BasicException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class ConferenceRequestValidator {

    private final ConferenceRepository conferenceRepository;

    public Mono<RegisterConferencePresenterRequest> isExists(Boolean isExists, RegisterConferencePresenterRequest request) {
        if (Boolean.TRUE.equals(isExists)) {
            return Mono.error(new BasicException("Already Exists Conference Request User", HttpStatus.BAD_REQUEST));
        } else {
            return Mono.just(request);
        }
    }

    public Mono<ConferenceRequest> isExistsMember(ConferenceRequest entity) {
        return conferenceRepository.existsByUserId(entity.getUserId())
                .flatMap(isExists -> {
                    if (Boolean.TRUE.equals(isExists)) {
                        return Mono.error(new BasicException("Already Exists Conference User", HttpStatus.BAD_REQUEST));
                    } else {
                        return Mono.just(entity);
                    }
                });
    }
}
