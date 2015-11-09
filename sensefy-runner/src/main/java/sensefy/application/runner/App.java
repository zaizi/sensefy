package sensefy.application.runner;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Configuration;

import com.zaizi.sensefy.api.SensefyResourceApplication;
import com.zaizi.sensefy.ui.SensefySearchUiApplication;

/**
 * Hello world!
 *
 */
@SpringBootApplication
@Configuration
public class App {
	public static void main(String[] args) {

		start(SensefyResourceApplication.class).properties("server.port=9080").run(args);
		//start(AuthServerApplication.class).properties("server.port=9090").run(args);
		 start(SensefySearchUiApplication.class).properties("server.port=9070").run(args);
		// start(Another.class).properties("server.port=${other.port:9000}").run(args);
	}

	private static SpringApplicationBuilder start(Class<?>... sources) {
		return new SpringApplicationBuilder(App.class).showBanner(false).child(sources).web(true);
	}
}
