package com.reylibutan.tabularasa;

import java.util.Arrays;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import com.reylibutan.tabularasa.interceptor.SampleInterceptor;

@SpringBootApplication
public class TabularasaApplication extends SpringBootServletInitializer {

    private final String PARAM_LANGUAGE = "lang";
	
	public static void main(String[] args) {
    	SpringApplication.run(TabularasaApplication.class, args);
    }
    
    /**
     * Prints the beans generated by Spring Boot based on the information it gathered in the classpath.
     * 
     * @param ctx current application context
     */
    public static void printGeneratedBeans(ApplicationContext ctx) {
    	System.out.println("Generated Beans:");
    	
    	String[] beanNames = ctx.getBeanDefinitionNames();
        Arrays.sort(beanNames);
        for (String beanName : beanNames) {
            System.out.println(beanName);
        }
    }
    
    /* 
     * Enables the application to be packaged as a WAR file.
     */
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(TabularasaApplication.class);
    }
    
    /**
     * Sets the locale default value to US.
     * 
     * @return localeResolver
     */
    @Bean
    public LocaleResolver localeResolver() {
        SessionLocaleResolver slr = new SessionLocaleResolver();
        slr.setDefaultLocale(Locale.US);
        return slr;
    }
    
    /**
     * Allows the application to switch languages using the URL <br>
     * Example: http://example.com?lang=jp
     * 
     * @return localeChangeInterceptor
     */
    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
        lci.setParamName(this.PARAM_LANGUAGE);
        return lci;
    }
    
    /**
     * Allows the validators to use the existing message source instead of its default ValidationMessages.properties
     * 
     * @param messageSource the configured message source (see application.properties #Internationalization)     * 
     * @return localValidatorFactoryBean
     */
    @Bean
    @Autowired
    public LocalValidatorFactoryBean validator(MessageSource messageSource) {
    	LocalValidatorFactoryBean lvfb = new LocalValidatorFactoryBean();
    	lvfb.setValidationMessageSource(messageSource);
    	return lvfb;
    }
    
    /**
     * Enables the registration of interceptors
     * 
     * @return webMvcConfigurerAdapter
     */
    @Bean
    public WebMvcConfigurerAdapter adapter() {
        return new WebMvcConfigurerAdapter() {
            @Override
            public void addInterceptors(InterceptorRegistry registry) {
            	registry.addInterceptor(new SampleInterceptor()).addPathPatterns("/sample/*");
            	registry.addInterceptor(localeChangeInterceptor());
            }
        };
    }
}