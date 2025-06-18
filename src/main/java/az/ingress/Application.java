package az.ingress;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableAsync;

import static org.springframework.boot.SpringApplication.run;

@SpringBootApplication
@EnableFeignClients
@EnableAsync
public class Application {

    public static void main(String[] args) {
        run(Application.class, args);
    }
}