package gsm.devfest.domain.user.data;

import gsm.devfest.domain.user.enums.UserRole;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@RequiredArgsConstructor
public class UserResponse {
    private final Long id;
    private final String name;
    private final String email;
    private final String stuNum;
    private final UserRole userRole;
}
