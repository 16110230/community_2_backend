package com.lawencon.community.config;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import freemarker.cache.ClassTemplateLoader;
import freemarker.cache.TemplateLoader;
import freemarker.template.Configuration;

@org.springframework.context.annotation.Configuration
public class ApiConfig {
	
	@Bean 
	public FreeMarkerConfigurer freemarkerClassLoaderConfig() {
	    Configuration configuration = new Configuration(Configuration.VERSION_2_3_27);
	    TemplateLoader templateLoader = new ClassTemplateLoader(this.getClass(), "/mail-template");
	    configuration.setTemplateLoader(templateLoader);
	    FreeMarkerConfigurer freeMarkerConfigurer = new FreeMarkerConfigurer();
	    freeMarkerConfigurer.setConfiguration(configuration);
	    
	    return freeMarkerConfigurer; 
	}
	
	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**").allowedOrigins("http://localhost:4200").allowedMethods(
						HttpMethod.POST.name(), HttpMethod.GET.name(), HttpMethod.DELETE.name(),
						HttpMethod.PUT.name(), HttpMethod.PATCH.name());
			}
		};
	}
}
