package br.com.devdojo.awesome.docs;

import java.util.Collections;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import static springfox.documentation.builders.PathSelectors.regex;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2

public class SwaggerConfig {

    @Bean
    public Docket apiDock() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("br.com.devdojo.awesome.endpoint"))
                .paths(regex("/v1.*"))
                .build()
                .globalOperationParameters(Collections.singletonList(new ParameterBuilder()
                                .name("Authorization")
                                .description("Bearer token")
                                .modelRef(new ModelRef("string"))
                                .parameterType("header")
                                .required(true)
                                .build()))
                .apiInfo(metadata());
    }

    private ApiInfo metadata() { //cabeçalho

        return new ApiInfoBuilder()
                .title("SpringBoot by Devdojo")
                .description("desc")
                .contact(new Contact("name", "http://35.237.88.55/", "henrique@gmail.com"))
                .license("Apache license")
                .licenseUrl("http://apache.org")
                .build();
    }

}
