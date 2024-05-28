package gsm.devfest.domain.lecture.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;

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

    private LocalDate lectureDate;

    private LocalDate startRegisterDate;

    private LocalDate endRegisterDate;

    private String presenterName;
}
