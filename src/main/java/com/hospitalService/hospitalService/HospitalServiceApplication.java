package com.hospitalService.hospitalService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@EnableWebMvc
public class HospitalServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(HospitalServiceApplication.class, args);
	}

	@Bean
	public Docket api(){
		Docket docket = new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.hospitalService.hospitalService"))
				.paths(PathSelectors.any())
				.build().apiInfo(metaData());
		docket.tags(new Tag("Doctor API","For all Doctors related operations"),
				new Tag("Patient API","For all Patients related operations"),
				new Tag("Suggestion API","For getting Doctor suggestions based on symptoms of Patients"));
		return docket;
	}

	private ApiInfo metaData() {
		return new ApiInfoBuilder()
				.title("Hospital Service API")
				.description("This API allows you to" + "\n" +
						"-> Register Doctors on the database " + "\n" +
						"-> Register Patients on the database" + "\n" +
						"-> Suggesting Doctors based on Patient's symptom")
				.version("1")
				.build();
	}

}
