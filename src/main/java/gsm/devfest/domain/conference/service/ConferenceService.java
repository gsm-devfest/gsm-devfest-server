package gsm.devfest.domain.conference.service;

import gsm.devfest.domain.conference.data.ConferenceResponse;
import gsm.devfest.domain.conference.data.RegisterConferencePresenterRequest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ConferenceService {
    Mono<Long> registerConferencePresenter(RegisterConferencePresenterRequest request);
    Mono<Long> acceptConference(Long requestId);
    Mono<Long> registerConference(Long conferenceId);
    Mono<ConferenceResponse> getConferenceById(Long conferenceId);
    Flux<ConferenceResponse> getConferences();
}
