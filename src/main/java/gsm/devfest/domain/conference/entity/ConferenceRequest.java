package gsm.devfest.domain.conference.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tbl_conference_request")
public class ConferenceRequest {

    @Id
    private Long id;

    private Long userId;

    private String title;

    private String  content;

    public Conference toConference(Integer limitCount, Integer memberCount, LocalDate conferenceDate, LocalDate startRegisterDate, LocalDate endRegisterDate) {
        return Conference.builder()
                .title(title)
                .content(content)
                .limitCount(limitCount)
                .memberCount(memberCount)
                .conferenceDate(conferenceDate)
                .startRegisterDate(startRegisterDate)
                .endRegisterDate(endRegisterDate)
                .userId(userId)
                .build();
    }
}
