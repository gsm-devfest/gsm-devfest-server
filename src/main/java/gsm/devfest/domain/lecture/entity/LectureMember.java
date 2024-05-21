package gsm.devfest.domain.lecture.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("tbl_lecture_member")
public class LectureMember {

    @Id
    private Long id;

    private Long lectureMember;

}
