package gsm.devfest.domain.conference.validator;

import gsm.devfest.domain.conference.entity.Conference;
import gsm.devfest.domain.conference.repository.ConferenceMemberRepository;
import gsm.devfest.global.error.BasicException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class ConferenceValidator {

    private final ConferenceMemberRepository conferenceMemberRepository;

    private static final String NOT_FOUND_CONFERENCE = "Not Found Conference";
    private static final String INVALID_DATE_CONFERENCE = "Invalid Date Conference";
    private static final String ALREADY_REGISTER_USER = "Already Register User";
    private static final String INVALID_LIMIT_CONFERENCE = "Invalid Limit Conference";

    public Mono<Conference> validateDate(Conference entity) {
        if (isValidDate(entity)) {
            return Mono.just(entity);
        } else {
            return Mono.error(new BasicException(INVALID_DATE_CONFERENCE, HttpStatus.BAD_REQUEST));
        }
    }

    public Mono<Conference> validateAlreadyRegistered(Conference entity, Long userId) {
        return conferenceMemberRepository.existsByMemberIdAndConferenceId(userId, entity.getId())
                .flatMap(isExist -> {
                    if (Boolean.TRUE.equals(isExist)) {
                        return Mono.error(new BasicException(ALREADY_REGISTER_USER, HttpStatus.BAD_REQUEST));
                    } else {
                        return Mono.just(entity);
                    }
                });
    }

    public Mono<Conference> validateLimit(Conference entity) {
        if (isValidLimit(entity)) {
            return Mono.just(entity);
        } else {
            return Mono.error(new BasicException(INVALID_LIMIT_CONFERENCE, HttpStatus.BAD_REQUEST));
        }
    }

    private boolean isValidLimit(Conference conference) {
        return conference.getLimitCount() > conference.getMemberCount() + 1;
    }

    private boolean isValidDate(Conference conference) {
        LocalDate current = LocalDate.now();
        return current.isBefore(conference.getEndRegisterDate()) && current.isAfter(conference.getStartRegisterDate());
    }
}
