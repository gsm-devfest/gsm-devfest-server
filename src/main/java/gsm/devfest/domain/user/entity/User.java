package gsm.devfest.domain.user.entity;

import gsm.devfest.domain.user.enums.UserRole;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "tbl_user")
public class User {

    @Id
    private Long id;

    private String name;

    private String email;

    private String stuNumber;

    private UserRole userRole;

    private Long conferenceMemberId;

    private Long lectureMemberId;
}
