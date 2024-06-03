package gsm.devfest.domain.lecture.service;

import gsm.devfest.domain.lecture.data.CreateLectureRequest;
import gsm.devfest.domain.lecture.data.RegisterLectureRequest;
import reactor.core.publisher.Mono;

public interface LectureService {
    Mono<Void> createLecture(CreateLectureRequest request);

    Mono<Long> registerLecture(RegisterLectureRequest request);
}
