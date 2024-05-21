package gsm.devfest.domain.conference.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "tbl_conference_member")
public class ConferenceMember {

    @Id
    private Long id;

    private Long conferenceId;
}
