package gsm.devfest.domain.lecture.repository;

import gsm.devfest.domain.lecture.entity.Lecture;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

public interface LectureRepository extends R2dbcRepository<Lecture, Long> {
}
