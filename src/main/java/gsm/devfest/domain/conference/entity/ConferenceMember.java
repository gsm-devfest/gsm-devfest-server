package gsm.devfest.domain.conference.entity;

import org.springframework.data.relational.core.mapping.Table;

@Table(name = "tbl_conference_member")
public class ConferenceMember {

    private Long id;

    private Long conferenceId;
}
