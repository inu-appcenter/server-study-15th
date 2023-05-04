package appCenter.guCoding.todoList.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfig {
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.OAS_30)
                .apiInfo(apiInfo())
                .useDefaultResponseMessages(false) // swagger 제공 기본 응답 코드 사용 x
                .select()
                .apis(RequestHandlerSelectors.basePackage("appCenter.guCoding.todoList"))
                .paths(PathSelectors.any()) //
                .build();

    }

    // swagger-ui/index.html

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("todoList")
                .description("SwaggerConfig")
                .version("3.0.0")
                .build();
    }
}
