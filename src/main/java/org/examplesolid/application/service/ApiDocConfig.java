package org.examplesolid.application.service;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class ApiDocConfig {

    @Value("$api.doc.dev-url")
    private String devUrl;

    @Bean
    public OpenAPI myOpenAPI() {
        Server devServer = new Server();
        devServer.setUrl(devUrl);
        Contact contact = new Contact();
        contact.setEmail("ivanchoh99@gmail.com");
        contact.setName("Ivan H Carvajal Acevedo");
        License mitLicense = new License().name("MIT License").url("https://choosealicense.com/licenses/mit/");
        Info info = new Info().title("Traninig example API").version("0.5").contact(contact).description("This API is a exercise mentoring by Juan Manuel to improve and get better tech concepts").license(mitLicense);
        return new OpenAPI().info(info).servers(List.of(devServer));
    }
}
