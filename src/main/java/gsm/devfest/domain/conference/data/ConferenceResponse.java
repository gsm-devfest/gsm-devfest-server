package gsm.devfest.domain.conference.data;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class ConferenceResponse {

    private final Long id;

    private final String title;

    private final String content;

    private LocalDateTime conferenceDate;

    private LocalDateTime startRegisterDate;

    private LocalDateTime endRegisterDate;

    private Long userId;
}
