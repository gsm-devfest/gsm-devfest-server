package gsm.devfest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

import java.time.ZoneId;
import java.util.TimeZone;

@SpringBootApplication
@ConfigurationPropertiesScan
public class DevfestApplication {

	public static void main(String[] args) {
		TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));
		SpringApplication.run(DevfestApplication.class, args);
	}

}
