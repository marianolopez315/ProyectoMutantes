package org.example.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Mutant Detector API")
                        .version("1.0.0")
                        //Agregamos el link en español aca usando HTML
                        .description("API para detectar mutantes mediante análisis de secuencias de ADN. " +
                                     "<br><br>" +
                                     "📧 <a href='mailto:tu.email@ejemplo.com'>Enviar correo a Mariano Lopez</a>")
                        .contact(new Contact()
                                .name("Mariano Lopez")
                                .url("https://github.com/marianolopez315/ProyectoMutantes.git"))
                );
    }
}