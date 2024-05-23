package gsm.devfest.domain.user.controller;

import gsm.devfest.domain.user.data.CreateUserRequest;
import gsm.devfest.domain.user.data.UserResponse;
import gsm.devfest.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/{id}")
    public Mono<ResponseEntity<UserResponse>> getUserById(@PathVariable Long id) {
        return userService.getUserById(id)
                .map(ResponseEntity::ok);
    }

    @GetMapping
    public ResponseEntity<Flux<UserResponse>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @PostMapping
    public Mono<ResponseEntity<Void>> saveUser(@RequestBody CreateUserRequest request) {
        return userService.saveUser(request)
                .map(response -> ResponseEntity.created(URI.create("/user")).body(response));
    }
}
