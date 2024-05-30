package gsm.devfest.domain.lecture.repository;

import gsm.devfest.domain.lecture.entity.LectureMember;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

public interface LectureMemberRepository extends R2dbcRepository<LectureMember, Long> {

    Mono<Boolean> existsByLectureIdAndMemberId(Long lectureId, Long memberId);
    Mono<Boolean> existsByMemberIdAndLectureDate(Long memberId, LocalDate lectureDate);
}
