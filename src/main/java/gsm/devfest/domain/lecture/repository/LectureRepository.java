package gsm.devfest.domain.lecture.repository;

import gsm.devfest.domain.lecture.entity.Lecture;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;

import java.time.LocalDate;

public interface LectureRepository extends R2dbcRepository<Lecture, Long> {

    Flux<Lecture> findAllByLectureDate(LocalDate lectureDate);
}
