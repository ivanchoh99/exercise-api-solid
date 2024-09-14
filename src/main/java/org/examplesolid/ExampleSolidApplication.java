package org.examplesolid;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource(value = {"classpath:config.properties"})
public class ExampleSolidApplication {
    public static void main(String[] args) {
        SpringApplication.run(ExampleSolidApplication.class, args);
    }
}
