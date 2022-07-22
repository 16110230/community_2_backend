package com.lawencon.community.config;

import org.springframework.context.annotation.Bean;
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
}
