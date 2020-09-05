package org.sharon.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author sharon
 * @create 2020-08-30-16:22
 */
@Configuration
public class MyMvcConfig implements WebMvcConfigurer {
    @Bean
    public WebMvcConfigurer webMvcConfigurerImpl(){
        return new WebMvcConfigurer(){
            @Override
            public void addViewControllers(ViewControllerRegistry registry) {
                registry.addViewController("/admin/login").setViewName("admin/login");
                registry.addViewController("/admin").setViewName("admin/index");
                registry.addViewController("/about").setViewName("about");
            }
        };
    }
}
