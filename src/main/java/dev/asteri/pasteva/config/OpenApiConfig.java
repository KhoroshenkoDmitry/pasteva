package dev.asteri.pasteva.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;


@OpenAPIDefinition(
        info = @Info(
                title = "Pasteva",
                description = "yet another pastebin copy with anon feature",
                version = "0.5",
                contact = @Contact(
                        name = "Asteri"
                )
        )
)
public class OpenApiConfig {

}