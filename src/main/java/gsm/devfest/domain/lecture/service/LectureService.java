package gsm.devfest.domain.lecture.service;

import gsm.devfest.domain.lecture.data.CreateLectureRequest;
import reactor.core.publisher.Mono;

public interface LectureService {
    Mono<Void> createLecture(CreateLectureRequest request);
}
