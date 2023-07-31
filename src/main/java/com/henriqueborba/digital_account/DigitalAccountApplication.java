package com.henriqueborba.digital_account;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
        info = @Info(
                title = "Digital Account API",
                version = "1.0",
                description = "Digital Account API",
                contact = @Contact(
                        name = "Henrique Borba",
                        email = "henrique.borba@ecomp.ufsm.br"
                )
        )
)
@SecurityScheme(name = "bearerAuth", type = SecuritySchemeType.HTTP, scheme = "bearer")
public class DigitalAccountApplication {

    public static void main(String[] args) {
        SpringApplication.run(DigitalAccountApplication.class, args);
    }

}
