package gsm.devfest.domain.conference.data;

import gsm.devfest.domain.conference.entity.ConferenceRequest;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class RegisterConferencePresenterRequest {
    private final Long userId;
    private final String title;
    private final String content;

    public ConferenceRequest toEntity() {
        return ConferenceRequest.builder()
                .userId(userId)
                .title(title)
                .content(content)
                .build();
    }
}
