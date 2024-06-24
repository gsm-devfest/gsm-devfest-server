package gsm.devfest.domain.lecture.service;

import gsm.devfest.common.lock.annotation.DistributedLock;
import gsm.devfest.domain.lecture.data.CreateLectureRequest;
import gsm.devfest.domain.lecture.data.LectureResponse;
import gsm.devfest.domain.lecture.data.RegisterLectureRequest;
import gsm.devfest.domain.lecture.entity.Lecture;
import gsm.devfest.domain.lecture.entity.LectureMember;
import gsm.devfest.domain.lecture.repository.LectureMemberRepository;
import gsm.devfest.domain.lecture.repository.LectureRepository;
import gsm.devfest.domain.lecture.validator.LectureValidator;
import gsm.devfest.global.error.BasicException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
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
    @DistributedLock(key = "#request.lectureId")
    public Mono<Long> registerLecture(RegisterLectureRequest request) {
        return lectureRepository.findById(request.getLectureId())
                .switchIfEmpty(Mono.error(new BasicException("Lecture Not Found", HttpStatus.NOT_FOUND)))
                .flatMap(entity -> lectureValidator.isExistLectureMember(entity, request.getUserId()))
                .flatMap(lectureValidator::validateDate)
                .flatMap(lectureValidator::validateLimit)
                .flatMap(entity -> lectureValidator.isExistSection(entity, request.getUserId())
                .flatMap(this::updateMemberCount)
                .flatMap(lecture -> saveLectureMember(lecture, request.getUserId()))
                .map(LectureMember::getId));
    }

    @Override
    public Mono<LectureResponse> getLectureById(Long lectureId) {
        return lectureRepository.findById(lectureId)
                .switchIfEmpty(Mono.error(new BasicException("Lecture Not Found", HttpStatus.NOT_FOUND)))
                .map(this::convertToLectureResponse);
    }

    @Override
    public Flux<LectureResponse> getLecture() {
        return lectureRepository.findAll().map(this::convertToLectureResponse);
    }

    @Override
    public Mono<Void> deleteLecture(Long lectureId) {
        return lectureRepository.deleteById(lectureId);
    }

    public LectureResponse convertToLectureResponse(Lecture lecture) {
        return LectureResponse.builder()
                .id(lecture.getId())
                .title(lecture.getTitle())
                .content(lecture.getContent())
                .section(lecture.getSection())
                .limitCount(lecture.getLimitCount())
                .memberCount(lecture.getMemberCount())
                .lectureDate(lecture.getLectureDate())
                .startRegisterDate(lecture.getStartRegisterDate())
                .endRegisterDate(lecture.getEndRegisterDate())
                .presenterName(lecture.getPresenterName())
                .build();
    }

    public Mono<LectureMember> saveLectureMember(Lecture lecture, Long userId) {
        LectureMember lectureMember = LectureMember.builder()
                .memberId(userId)
                .lectureId(lecture.getId())
                .build();
        return lectureMemberRepository.save(lectureMember);
    }

    public Mono<Lecture> updateMemberCount(Lecture entity) {
        Lecture lecture = new Lecture(
                entity.getId(),
                entity.getTitle(),
                entity.getContent(),
                entity.getSection(),
                entity.getLimitCount(),
                entity.getMemberCount() + 1,
                entity.getLectureDate(),
                entity.getStartRegisterDate(),
                entity.getEndRegisterDate(),
                entity.getPresenterName()
        );
        return lectureRepository.save(lecture);
    }


}
