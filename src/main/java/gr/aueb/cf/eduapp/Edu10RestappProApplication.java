package gr.aueb.cf.eduapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.resilience.annotation.EnableResilientMethods;

@SpringBootApplication
@EnableResilientMethods
@EnableJpaAuditing
public class Edu10RestappProApplication {

    public static void main(String[] args) {
        SpringApplication.run(Edu10RestappProApplication.class, args);
    }

}
