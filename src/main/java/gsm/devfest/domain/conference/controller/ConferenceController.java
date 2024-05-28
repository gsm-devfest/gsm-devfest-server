package gsm.devfest.domain.conference.controller;

import gsm.devfest.domain.conference.data.ConferenceDateRequest;
import gsm.devfest.domain.conference.data.ConferenceResponse;
import gsm.devfest.domain.conference.data.RegisterConferencePresenterRequest;
import gsm.devfest.domain.conference.service.ConferenceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/conference")
public class ConferenceController {

    private final ConferenceService conferenceService;

    @PostMapping
    public Mono<ResponseEntity<Long>> registerConferencePresenter(@RequestBody RegisterConferencePresenterRequest request) {
        return conferenceService.registerConferencePresenter(request)
                .map(response -> ResponseEntity.created(URI.create("/conference")).body(response));
    }

    @PostMapping("/{request_id}")
    public Mono<ResponseEntity<Long>> acceptConference(@PathVariable("request_id") Long requestId, @RequestBody ConferenceDateRequest request) {
        return conferenceService.acceptConference(requestId, request)
                .map(ResponseEntity::ok);
    }

    @PostMapping("/{id}/register/{user_id}")
    public Mono<ResponseEntity<Long>> registerConference(@PathVariable Long id, @PathVariable("user_id") Long userId) {
        return conferenceService.registerConference(id, userId)
                .map(ResponseEntity::ok);
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<ConferenceResponse>> getConferenceById(@PathVariable Long id) {
        return conferenceService.getConferenceById(id)
                .map(ResponseEntity::ok);
    }

    @GetMapping
    public ResponseEntity<Flux<ConferenceResponse>> getConferences() {
        return ResponseEntity.ok(conferenceService.getConferences());
    }

}
