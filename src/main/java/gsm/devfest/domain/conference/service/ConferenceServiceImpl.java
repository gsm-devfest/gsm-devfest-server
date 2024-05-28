package gsm.devfest.domain.conference.service;

import gsm.devfest.domain.conference.data.ConferenceResponse;
import gsm.devfest.domain.conference.data.RegisterConferencePresenterRequest;
import gsm.devfest.domain.conference.repository.ConferenceMemberRepository;
import gsm.devfest.domain.conference.repository.ConferenceRepository;
import gsm.devfest.domain.conference.repository.ConferenceRequestRepository;
import gsm.devfest.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ConferenceServiceImpl implements ConferenceService {

    private final ConferenceRepository conferenceRepository;
    private final UserRepository userRepository;
    private final ConferenceRequestRepository conferenceRequestRepository;
    private final ConferenceMemberRepository conferenceMemberRepository;

    @Override
    public Mono<Long> registerConferencePresenter(RegisterConferencePresenterRequest request) {



        return null;
    }

    @Override
    public Mono<Long> acceptConference(Long requestId) {
        return null;
    }

    @Override
    public Mono<Long> registerConference(Long conferenceId) {
        return null;
    }

    @Override
    public Mono<ConferenceResponse> getConferenceById(Long conferenceId) {
        return null;
    }

    @Override
    public Flux<ConferenceResponse> getConferences() {
        return null;
    }
}
