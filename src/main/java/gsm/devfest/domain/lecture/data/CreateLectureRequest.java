package gsm.devfest.domain.lecture.data;

import com.fasterxml.jackson.annotation.JsonFormat;
import gsm.devfest.domain.lecture.entity.Lecture;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@Getter
@RequiredArgsConstructor
public class CreateLectureRequest {
    private final String title;
    private final String content;
    private final String section;
    private final Integer limitCount;
    private final Integer memberCount;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private final LocalDate lectureDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private final LocalDate startResisterDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private final LocalDate endResisterDate;
    private final String presenterName;

    public Lecture toEntity() {
        return Lecture.builder()
                .title(title)
                .content(content)
                .section(section)
                .limitCount(limitCount)
                .memberCount(memberCount)
                .lectureDate(lectureDate)
                .startRegisterDate(startResisterDate)
                .endRegisterDate(endResisterDate)
                .presenterName(presenterName)
                .build();
    }
}
