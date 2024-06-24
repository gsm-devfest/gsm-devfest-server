package gsm.devfest.domain.conference.service;

import gsm.devfest.common.lock.annotation.DistributedLock;
import gsm.devfest.domain.conference.data.AcceptConferenceRequest;
import gsm.devfest.domain.conference.data.ConferenceResponse;
import gsm.devfest.domain.conference.data.RegisterConferencePresenterRequest;
import gsm.devfest.domain.conference.entity.Conference;
import gsm.devfest.domain.conference.entity.ConferenceMember;
import gsm.devfest.domain.conference.entity.ConferenceRequest;
import gsm.devfest.domain.conference.repository.ConferenceMemberRepository;
import gsm.devfest.domain.conference.repository.ConferenceRepository;
import gsm.devfest.domain.conference.repository.ConferenceRequestRepository;
import gsm.devfest.domain.conference.validator.ConferenceRequestValidator;
import gsm.devfest.domain.conference.validator.ConferenceValidator;
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
    private final ConferenceRequestRepository conferenceRequestRepository;
    private final ConferenceMemberRepository conferenceMemberRepository;
    private final ConferenceValidator conferenceValidator;
    private final ConferenceRequestValidator conferenceRequestValidator;

    @Override
    public Mono<Long> registerConferencePresenter(RegisterConferencePresenterRequest request) {
        return conferenceRequestRepository.existsByUserId(request.getUserId())
                .flatMap(isExists -> conferenceRequestValidator.isExists(isExists, request))
                .flatMap(conferenceRequest -> conferenceRequestRepository.save(conferenceRequest.toEntity()))
                .map(ConferenceRequest::getId);
    }

    @Override
    public Mono<Long> acceptConference(Long requestId, AcceptConferenceRequest request) {
        return conferenceRequestRepository.findById(requestId)
                .switchIfEmpty(Mono.error(new BasicException("Not Found ConferenceRequest", HttpStatus.NOT_FOUND)))
                .flatMap(conferenceRequestValidator::isExistsMember)
                .flatMap(conferenceRequest -> conferenceRepository.save(conferenceRequest.toConference(
                        request.getLimitCount(),
                        request.getMemberCount(),
                        request.getConferenceDate(),
                        request.getStartRegisterDate(),
                        request.getEndRegisterDate()
                )))
                .map(Conference::getId);
    }

    @Override
    @DistributedLock(key = "#conferenceId")
    public Mono<Long> registerConference(Long conferenceId, Long userId) {
        return conferenceRepository.findById(conferenceId)
                .switchIfEmpty(Mono.error(new BasicException("Not Found Conference", HttpStatus.NOT_FOUND)))
                .flatMap(conferenceValidator::validateDate)
                .flatMap(entity -> conferenceValidator.validateAlreadyRegistered(entity, userId))
                .flatMap(conferenceValidator::validateLimit)
                .flatMap(this::updateMemberCount)
                .flatMap(entity -> saveConferenceMember(entity, userId))
                .map(ConferenceMember::getId);
    }

    @Override
    public Mono<ConferenceResponse> getConferenceById(Long conferenceId) {
        return conferenceRepository.findById(conferenceId)
                .switchIfEmpty(Mono.error(new BasicException("Not Found Conference", HttpStatus.NOT_FOUND)))
                .map(this::convertToConferenceResponse);
    }

    private ConferenceResponse convertToConferenceResponse(Conference conference) {
        return ConferenceResponse.builder()
                .id(conference.getId())
                .title(conference.getTitle())
                .content(conference.getContent())
                .limitCount(conference.getLimitCount())
                .memberCount(conference.getMemberCount())
                .conferenceDate(conference.getConferenceDate())
                .startRegisterDate(conference.getStartRegisterDate())
                .endRegisterDate(conference.getEndRegisterDate())
                .userId(conference.getUserId())
                .build();
    }

    @Override
    public Flux<ConferenceResponse> getConferences() {
        return conferenceRepository.findAll().map(this::convertToConferenceResponse);
    }

    private Mono<ConferenceMember> saveConferenceMember(Conference conference, Long userId) {
        ConferenceMember member = ConferenceMember.builder()
                .memberId(userId)
                .conferenceId(conference.getId())
                .build();
        return conferenceMemberRepository.save(member);
    }

    private Mono<Conference> updateMemberCount(Conference entity) {
        Conference conference = new Conference(
                entity.getId(),
                entity.getTitle(),
                entity.getContent(),
                entity.getLimitCount(),
                entity.getMemberCount() + 1,
                entity.getConferenceDate(),
                entity.getStartRegisterDate(),
                entity.getEndRegisterDate(),
                entity.getUserId()
        );
        return conferenceRepository.save(conference);
    }
}
