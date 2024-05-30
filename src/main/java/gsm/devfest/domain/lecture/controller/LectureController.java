package gsm.devfest.domain.lecture.controller;

import gsm.devfest.domain.lecture.data.CreateLectureRequest;
import gsm.devfest.domain.lecture.data.RegisterLectureRequest;
import gsm.devfest.domain.lecture.service.LectureService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.net.URI;

@RestController
@RequestMapping("/lecture")
@RequiredArgsConstructor
public class LectureController {

    private final LectureService lectureService;

    @PostMapping
    public Mono<ResponseEntity<Void>> createLecture(@RequestBody CreateLectureRequest request) {
        return lectureService.createLecture(request)
                .map(response -> ResponseEntity.created(URI.create("/lecture")).body(response));
    }

    @PostMapping("/register")
    public Mono<ResponseEntity<Long>> registerLecture(@RequestBody RegisterLectureRequest request) {
        return lectureService.registerLecture(request)
                .map(ResponseEntity::ok);
    }
}
