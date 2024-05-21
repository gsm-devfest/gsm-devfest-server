package gsm.devfest.domain.conference.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

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

    private LocalDateTime conferenceDate;

    private LocalDateTime startRegisterDate;

    private LocalDateTime endRegisterDate;

    private Long userId;
}
