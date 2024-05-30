package gsm.devfest.domain.lecture.service;

import gsm.devfest.domain.lecture.data.CreateLectureRequest;
import gsm.devfest.domain.lecture.data.ReserveLectureRequest;
import gsm.devfest.domain.lecture.entity.Lecture;
import gsm.devfest.domain.lecture.entity.LectureMember;
import gsm.devfest.domain.lecture.repository.LectureMemberRepository;
import gsm.devfest.domain.lecture.repository.LectureRepository;
import gsm.devfest.domain.lecture.validator.LectureValidator;
import gsm.devfest.domain.user.repository.UserRepository;
import gsm.devfest.global.error.BasicException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class LectureServiceImpl implements LectureService {

    private final LectureRepository lectureRepository;
    private final LectureMemberRepository lectureMemberRepository;
    private final LectureValidator lectureValidator;

    @Override
    public Mono<Void> createLecture(CreateLectureRequest request) {
        return lectureRepository.save(request.toEntity()).then();
    }

    @Override
    public Mono<Long> reserveLecture(ReserveLectureRequest request) {
        return lectureRepository.findById(request.getLectureId())
                .switchIfEmpty(Mono.error(new BasicException("Lecture Not Found", HttpStatus.NOT_FOUND)))
                .flatMap(entity -> lectureValidator.isExistLectureMember(entity, request.getUserId()))
                .flatMap(lectureValidator::validateDate)
                .flatMap(lectureValidator::validateLimit)
                .flatMap(entity -> lectureValidator.isExistSection(entity, request.getUserId()))
                .flatMap(entity -> saveLectureMember(entity, request.getUserId()))
                .map(LectureMember::getId);
    }

    public Mono<LectureMember> saveLectureMember(Lecture lecture, Long userId) {
        LectureMember lectureMember = LectureMember.builder()
                .memberId(userId)
                .lectureId(lecture.getId())
                .lectureDate(lecture.getLectureDate())
                .build();
        return lectureMemberRepository.save(lectureMember);
    }


}
