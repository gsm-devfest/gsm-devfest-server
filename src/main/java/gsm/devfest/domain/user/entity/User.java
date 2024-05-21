package gsm.devfest.domain.user.entity;

import gsm.devfest.domain.user.enums.UserRole;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tbl_user")
public class User {

    @Id
    private Long id;

    private String name;

    private String email;

    private String stuNum;

    private UserRole userRole;

}
