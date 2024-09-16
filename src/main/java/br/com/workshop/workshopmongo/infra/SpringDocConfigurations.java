package br.com.workshop.workshopmongo.infra;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;


@Configuration
public class SpringDocConfigurations {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API Spring + MongoDB")
                        .version("v1")
                        .description("[GitHub](https://github.com/hada97/workshopmongo)\n\n"
                        ));
    }
}
