package gsm.devfest.domain.lecture.service;

import gsm.devfest.domain.lecture.data.CreateLectureRequest;
import gsm.devfest.domain.lecture.repository.LectureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class LectureServiceImpl implements LectureService {

    private final LectureRepository lectureRepository;

    @Override
    public Mono<Void> createLecture(CreateLectureRequest request) {
        return lectureRepository.save(request.toEntity()).then();
    }
}
