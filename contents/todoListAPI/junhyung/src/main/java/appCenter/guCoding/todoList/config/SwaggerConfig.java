package appCenter.guCoding.todoList.config;


import appCenter.guCoding.todoList.config.auth.LoginUser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import java.util.Arrays;
import java.util.List;

@Configuration
public class SwaggerConfig {
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.OAS_30)
                .apiInfo(apiInfo())
                .useDefaultResponseMessages(false)
                .securitySchemes(List.of(this.apiKey()))
                .ignoredParameterTypes(LoginUser.class) // ㅠㅠ
                .select()
                .apis(RequestHandlerSelectors.basePackage("appCenter.guCoding.todoList"))
                .paths(PathSelectors.any())
                .build()
                .securityContexts(Arrays.asList(securityContext()));
    }

    // JWT SecurityContext 구성
    private SecurityContext securityContext() {
        return SecurityContext.builder()
                .securityReferences(defaultAuth())
                .build();
    }

    private List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return List.of(new SecurityReference("Authorization", authorizationScopes));
    }


    // swagger-ui/index.html

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("todoList")
                .description("SwaggerConfig")
                .version("3.0.0")
                .build();
    }

    // 요청헤더, 매개변수이름, 위치
    private ApiKey apiKey() {
        return new ApiKey("Authorization", "Authorization", "header");
    }
}
