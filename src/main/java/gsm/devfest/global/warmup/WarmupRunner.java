package gsm.devfest.global.warmup;

import gsm.devfest.domain.conference.controller.ConferenceController;
import gsm.devfest.domain.lecture.controller.LectureController;
import gsm.devfest.global.error.BasicException;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class WarmupRunner implements ApplicationRunner {

    private final ConferenceController conferenceController;
    private final LectureController lectureController;

    public WarmupRunner(ConferenceController conferenceController, LectureController lectureController) {
        this.conferenceController = conferenceController;
        this.lectureController = lectureController;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        try {
            conferenceController.getConferences();
            lectureController.getLecture();
        } catch (Exception e) {
            Mono.error(new BasicException("Failed JVM WarmUp", HttpStatus.BAD_REQUEST));
        }
    }
}
