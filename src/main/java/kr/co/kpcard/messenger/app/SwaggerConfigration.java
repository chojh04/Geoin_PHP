package kr.co.kpcard.messenger.app;

import static springfox.documentation.builders.PathSelectors.regex;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfigration {

	@Bean
	public Docket newsApi() 
	{
		return new Docket(DocumentationType.SWAGGER_2).groupName("messenger")
				.apiInfo(apiInfo()).select().paths(regex("/messenger*.*"))
				// .paths(PathSelectors.any())
				.build();
	}

	@SuppressWarnings("deprecation")
	private ApiInfo apiInfo() 
	{
		return new ApiInfoBuilder().title("KPC Messenger Service REST APIs")
				.description("Kpcaed Messenger APIs ")
				// .termsOfServiceUrl("http://www-03.ibm.com/software/sla/sladb.nsf/sla/bm?Open")
				.contact("한국선불카드 기업부설연구소 기술개발팀 조준희")
				// .license("Apache License Version 2.0")
				// .licenseUrl("https://github.com/IBM-Bluemix/news-aggregator/blob/master/LICENSE")
				.version("2.0").build();
	}

}
