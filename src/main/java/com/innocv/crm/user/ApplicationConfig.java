package com.innocv.crm.user;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
@ConfigurationProperties
public class ApplicationConfig {

    @Value("${elasticsearch.crm.cluster}")
    private String crmCluster;

    @Bean(name = "crm-cluster-client")
    public RestHighLevelClient restHighLevelClient() {
        return new RestHighLevelClient(
                RestClient.builder(new HttpHost(crmCluster)));
    }

    @Bean
    public Docket userApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("crm-user-api")
                .apiInfo(userApiInfo())
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build()
                .pathMapping("/");
    }

    private ApiInfo userApiInfo() {
        return new ApiInfoBuilder()
                .title("Users REST Api")
                .description("InnoCV Challenge User REST Api")
                .termsOfServiceUrl("http://en.wikipedia.org/wiki/Terms_of_service")
                .contact(new Contact("Guillem Serra Calahorra", "https://github.com/gserracalahorra/innocv-test", "guillem.serra.calahorra@gmail.com"))
                .license("Apache License Version 2.0")
                .licenseUrl("http://www.apache.org/licenses/LICENSE-2.0.html")
                .version("2.0")
                .build();
    }


}