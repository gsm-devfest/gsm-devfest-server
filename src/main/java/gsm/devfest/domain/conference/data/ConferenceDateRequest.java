package gsm.devfest.domain.conference.data;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class ConferenceDateRequest {
    private final LocalDateTime conferenceDate;
    private final LocalDateTime startRegisterDate;
    private final LocalDateTime endRegisterDate;
}
