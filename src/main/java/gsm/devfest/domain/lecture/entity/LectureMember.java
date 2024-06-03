package gsm.devfest.domain.lecture.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table("tbl_lecture_member")
public class LectureMember {

    @Id
    private Long id;

    private Long memberId;

    private Long lectureId;
}
