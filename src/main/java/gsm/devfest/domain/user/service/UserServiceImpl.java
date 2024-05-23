package gsm.devfest.domain.user.service;

import gsm.devfest.domain.user.data.CreateUserRequest;
import gsm.devfest.domain.user.data.UserResponse;
import gsm.devfest.domain.user.entity.User;
import gsm.devfest.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    @Override
    public Mono<Void> saveUser(CreateUserRequest request) {
        return userRepository.save(request.toEntityStudent()).then();
    }

    @Override
    public Flux<UserResponse> getAllUsers() {
        Flux<UserResponse> responseFlux = userRepository.findAll()
                .map(this::convertToResponse);
        return responseFlux;
    }

    private UserResponse convertToResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .userRole(user.getUserRole())
                .build();
    }

}
