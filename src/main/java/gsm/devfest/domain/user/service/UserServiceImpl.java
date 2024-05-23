package gsm.devfest.domain.user.service;

import gsm.devfest.domain.user.data.CreateUserRequest;
import gsm.devfest.domain.user.data.UserResponse;
import gsm.devfest.domain.user.entity.User;
import gsm.devfest.domain.user.repository.UserRepository;
import gsm.devfest.global.error.BasicException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    @Override
    public Mono<Void> saveUser(CreateUserRequest request) {
        return userRepository.save(request.toEntity()).then();
    }

    @Override
    public Mono<UserResponse> getUserById(Long id) {
        return userRepository.findById(id)
                .switchIfEmpty(Mono.error(new BasicException("User Not Found", HttpStatus.NOT_FOUND)))
                .map(this::convertToResponse);
    }

    @Override
    public Flux<UserResponse> getAllUsers() {
        return userRepository.findAll()
                .map(this::convertToResponse);
    }

    private UserResponse convertToResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .stuNum(user.getStuNum())
                .userRole(user.getUserRole())
                .build();
    }

}
