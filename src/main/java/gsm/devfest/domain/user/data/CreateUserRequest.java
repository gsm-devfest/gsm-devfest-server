package gsm.devfest.domain.user.data;

import gsm.devfest.domain.user.entity.User;
import gsm.devfest.domain.user.enums.UserRole;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CreateUserRequest {
    private final String name;
    private final String email;
    private final String stuNum;
    private final UserRole role;

    public User toEntityStudent() {
        return User.builder()
                .name(name)
                .email(email)
                .stuNum(stuNum)
                .userRole(role)
                .build();
    }
}
