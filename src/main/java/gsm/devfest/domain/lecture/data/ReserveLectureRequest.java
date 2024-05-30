package gsm.devfest.domain.lecture.data;

import lombok.Getter;
import lombok.RequiredArgsConstructor;


@Getter
@RequiredArgsConstructor
public class ReserveLectureRequest {

    private final Long lectureId;
    private final Long userId;

}
