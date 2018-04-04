package kr.co.kpcard.messenger.app;

import kr.co.kpcard.messenger.app.components.WebMvcInterceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;


@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter{

	private static final Logger logger = LoggerFactory.getLogger(WebMvcConfigurerAdapter.class);
	
	@Autowired
	private WebMvcInterceptor webMvcInterceptor;
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		// TODO Auto-generated method stub
		logger.debug("=================> WebMvcConfig : addInterceptors");
		//super.addInterceptors(registry);
		registry.addInterceptor(webMvcInterceptor);
	}
	
	
}
