package gsm.devfest.domain.lecture.data;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@Getter
@Builder
@RequiredArgsConstructor
public class LectureResponse {
    private final Long id;

    private final String title;

    private final String content;

    private final String section;

    private final Integer limitCount;

    private final Integer memberCount;

    private final LocalDate lectureDate;

    private final LocalDate startRegisterDate;

    private final LocalDate endRegisterDate;

    private final String presenterName;
}
