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
@Table(name = "tbl_conference")
public class Conference {

    @Id
    private Long id;

    private String title;

    private String content;

    private Integer limitCount;

    private Integer memberCount;

    private LocalDate conferenceDate;

    private LocalDate startRegisterDate;

    private LocalDate endRegisterDate;

    private Long userId;
}
