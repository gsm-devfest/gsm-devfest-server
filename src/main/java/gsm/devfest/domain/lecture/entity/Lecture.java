package gsm.devfest.domain.lecture.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tbl_lecture")
public class Lecture {

    @Id
    private Long id;

    private String title;

    private String content;

    private String section;

    private LocalDateTime lectureDate;

    private LocalDateTime startRegisterDate;

    private LocalDateTime endRegisterDate;

    private String presenterName;
}
