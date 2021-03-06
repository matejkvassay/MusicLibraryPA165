package sk.matejkvassay.musiclibrary.config;


import javax.validation.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

/**
 *
 * @author Marián Macik
 */
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "sk.matejkvassay")
@Import({ SecurityConfig.class })
public class SpringMvcConfig extends WebMvcConfigurerAdapter {
    
    final static Logger LOG = LoggerFactory.getLogger(SpringMvcConfig.class);
    
    /**
     * Maps the main page to a specific view.
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        LOG.debug("mapping URL / to home view");
        registry.addViewController("/").setViewName("index");
    }
    
    /**
     * Adds resources folder for web pages access.
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
    }
    
    /**
     * Enables default Tomcat servlet that serves static files.
     */
    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        LOG.debug("enabling default servlet for static files");
        configurer.enable();
    }
    
    /**
     * Provides mapping from view names to JSP pages in WEB-INF/jsp directory.
     */
    @Bean
    public ViewResolver viewResolver() {
        LOG.debug("registering JSP in /WEB-INF/jsp/ as views");
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/jsp/");
        viewResolver.setSuffix(".jsp");
        return viewResolver;
    }
    
    /**
     * Provides localized messages.
     */
    @Bean
    public MessageSource messageSource() {
        LOG.debug("registering ResourceBundle 'texts' for messages");
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("texts");
        return messageSource;
    }
    
    /**
     * Provides JSR-303 Validator. Not used so far.
     */
    @Bean
    public Validator validator() {
        LOG.debug("validator()");
        return new LocalValidatorFactoryBean();
    }
    
}
