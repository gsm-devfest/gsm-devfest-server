package gsm.devfest.domain.user.service;

import gsm.devfest.domain.user.data.CreateUserRequest;
import gsm.devfest.domain.user.data.UserResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserService {
    Mono<Void> saveUser(CreateUserRequest request);
    Mono<UserResponse> getUserById(Long id);
    Flux<UserResponse> getAllUsers();
}
