package gsm.devfest.domain.conference.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

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
}
