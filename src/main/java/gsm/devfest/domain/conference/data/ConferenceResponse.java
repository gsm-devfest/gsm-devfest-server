package gsm.devfest.domain.conference.data;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@RequiredArgsConstructor
public class ConferenceResponse {

    private final Long id;

    private final String title;

    private final String content;

    private final Integer limitCount;

    private final Integer memberCount;

    private final LocalDateTime conferenceDate;

    private final LocalDateTime startRegisterDate;

    private final LocalDateTime endRegisterDate;

    private final Long userId;
}
