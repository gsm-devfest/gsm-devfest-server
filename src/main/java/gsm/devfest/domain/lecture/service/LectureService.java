package gsm.devfest.domain.lecture.service;

import gsm.devfest.domain.lecture.data.CreateLectureRequest;
import gsm.devfest.domain.lecture.data.LectureResponse;
import gsm.devfest.domain.lecture.data.RegisterLectureRequest;
import gsm.devfest.domain.lecture.entity.Lecture;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface LectureService {
    Mono<Void> createLecture(CreateLectureRequest request);

    Mono<Long> registerLecture(RegisterLectureRequest request);

    Mono<LectureResponse> getLectureById(Long lectureId);

    Flux<LectureResponse> getLecture();

    Mono<Void> deleteLecture(Long lectureId);
}
