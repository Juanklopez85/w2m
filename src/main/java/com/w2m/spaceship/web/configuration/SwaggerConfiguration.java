package com.w2m.spaceship.web.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class SwaggerConfiguration {
  @Bean
  public OpenAPI customOpenAPI() {
    return new OpenAPI()
        .addServersItem(new Server().url("/"))
        .info(new Info()
            .title("w2m-spaceship-service")
            .description("Technical test to W2M")
            .contact(buildContact())
            .license(new License().name("Apache 2.0").url("http://springdoc.org")));
  }

  private Contact buildContact() {
    Contact contact = new Contact();
    contact.setName("Juan Carlos Lopez");
    contact.setEmail("juanklopez@gmail.com");
    return contact;
  }
}
