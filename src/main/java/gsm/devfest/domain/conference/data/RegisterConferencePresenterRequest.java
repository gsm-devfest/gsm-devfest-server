package gsm.devfest.domain.conference.data;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class RegisterConferencePresenterRequest {
    private final Long userId;
    private final String title;
    private final String content;
}
