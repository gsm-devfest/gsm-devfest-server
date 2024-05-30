package gsm.devfest.domain.lecture.validator;

import gsm.devfest.domain.conference.entity.Conference;
import gsm.devfest.domain.lecture.data.ReserveLectureRequest;
import gsm.devfest.domain.lecture.entity.Lecture;
import gsm.devfest.domain.lecture.repository.LectureMemberRepository;
import gsm.devfest.global.error.BasicException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class LectureValidator {

    private final LectureMemberRepository lectureMemberRepository;

    public Mono<Lecture> isExistLectureMember(Lecture entity, Long userId) {
        return lectureMemberRepository.existsByLectureIdAndMemberId(entity.getId(), userId)
                .flatMap(isExist -> {
                    if (Boolean.TRUE.equals(isExist)) {
                        return Mono.error(new BasicException("Already Exist Lecture Member", HttpStatus.BAD_REQUEST));
                    } else {
                        return Mono.just(entity);
                    }
                });
    }

    public Mono<Lecture> isExistSection(Lecture entity, Long userId) {
        return lectureMemberRepository.existsByMemberIdAndLectureDate(userId, entity.getLectureDate())
                .flatMap(isExist -> {
                    if (Boolean.TRUE.equals(isExist)) {
                        return Mono.error(new BasicException("Already Exist Section", HttpStatus.BAD_REQUEST));
                    } else {
                        return Mono.just(entity);
                    }
                });
    }

    public Mono<Lecture> validateDate(Lecture entity) {
        if (isValidDate(entity)) {
            return Mono.just(entity);
        } else {
            return Mono.error(new BasicException("Invalid Date Lecture", HttpStatus.BAD_REQUEST));
        }
    }

    public Mono<Lecture> validateLimit(Lecture entity) {
        if (isValidLimit(entity)) {
            return Mono.just(entity);
        } else {
            return Mono.error(new BasicException("Invalid Limit Lecture", HttpStatus.BAD_REQUEST));
        }
    }

    private boolean isValidDate(Lecture lecture) {
        LocalDate current = LocalDate.now();
        return current.isBefore(lecture.getEndRegisterDate()) && current.isAfter(lecture.getStartRegisterDate());
    }

    private boolean isValidLimit(Lecture lecture) {
        return lecture.getLimitCount() > lecture.getMemberCount() + 1;
    }

}
