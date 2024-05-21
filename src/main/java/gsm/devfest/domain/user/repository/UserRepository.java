package gsm.devfest.domain.user.repository;

import gsm.devfest.domain.user.entity.User;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

public interface UserRepository extends R2dbcRepository<User, Long> {
}
