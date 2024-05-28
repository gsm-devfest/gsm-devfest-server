package gsm.devfest.domain.conference.service;

import gsm.devfest.domain.conference.data.ConferenceDateRequest;
import gsm.devfest.domain.conference.data.ConferenceResponse;
import gsm.devfest.domain.conference.data.RegisterConferencePresenterRequest;
import gsm.devfest.domain.conference.entity.Conference;
import gsm.devfest.domain.conference.entity.ConferenceMember;
import gsm.devfest.domain.conference.entity.ConferenceRequest;
import gsm.devfest.domain.conference.repository.ConferenceMemberRepository;
import gsm.devfest.domain.conference.repository.ConferenceRepository;
import gsm.devfest.domain.conference.repository.ConferenceRequestRepository;
import gsm.devfest.domain.conference.validator.ConferenceValidator;
import gsm.devfest.domain.user.repository.UserRepository;
import gsm.devfest.global.error.BasicException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
    private final ConferenceValidator conferenceValidator;

    @Override
    public Mono<Long> registerConferencePresenter(RegisterConferencePresenterRequest request) {
        return conferenceRequestRepository.existsByUserId(request.getUserId())
                .flatMap(isExists -> {
                    if(isExists) return Mono.error(new BasicException("Already Exists Conference Request User", HttpStatus.BAD_REQUEST));
                    else return Mono.just(request);
                })
                .flatMap(conferenceRequest -> conferenceRequestRepository.save(conferenceRequest.toEntity()))
                .map(ConferenceRequest::getId);
    }

    @Override
    public Mono<Long> acceptConference(Long requestId, ConferenceDateRequest request) {
        return conferenceRequestRepository.findById(requestId)
                .switchIfEmpty(Mono.error(new BasicException("Not Found ConferenceRequest", HttpStatus.NOT_FOUND)))
                .flatMap(entity -> {
                    Mono<Boolean> isExistsMono = conferenceRepository.existsByUserId(entity.getUserId());
                    return isExistsMono.flatMap(isExists -> {
                        if(Boolean.TRUE.equals(isExists)) return Mono.error(new BasicException("Already Exists Conference User", HttpStatus.BAD_REQUEST));
                        else return Mono.just(entity);
                    });
                }).flatMap(conferenceRequest -> conferenceRepository.save(conferenceRequest.toConference(request.getConferenceDate(), request.getStartRegisterDate(), request.getEndRegisterDate())))
                .map(Conference::getId);
    }

    @Override
    public Mono<Long> registerConference(Long conferenceId, Long userId) {
        return conferenceRepository.findById(conferenceId)
                .switchIfEmpty(Mono.error(new BasicException("Not Found Conference", HttpStatus.NOT_FOUND)))
                .flatMap(conferenceValidator::validateDate)
                .flatMap(entity -> conferenceValidator.validateAlreadyRegistered(entity, userId))
                .flatMap(conferenceValidator::validateLimit)
                .flatMap(entity -> saveConferenceMember(entity, userId))
                .map(ConferenceMember::getId);
    }


    private Mono<ConferenceMember> saveConferenceMember(Conference conference, Long userId) {
        ConferenceMember member = ConferenceMember.builder()
                .memberId(userId)
                .conferenceId(conference.getId())
                .build();
        return conferenceMemberRepository.save(member);
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
