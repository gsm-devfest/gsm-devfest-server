package gsm.devfest.domain.conference.data;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@Getter
@Builder
@RequiredArgsConstructor
public class ConferenceResponse {

    private final Long id;

    private final String title;

    private final String content;

    private final Integer limitCount;

    private final Integer memberCount;

    private final LocalDate conferenceDate;

    private final LocalDate startRegisterDate;

    private final LocalDate endRegisterDate;

    private final Long userId;
}
