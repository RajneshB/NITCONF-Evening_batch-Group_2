package com.nitconf.backend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class CorsConfig implements WebMvcConfigurer {
        @SuppressWarnings("null")
        @Override
        public void addCorsMappings(@SuppressWarnings("null") CorsRegistry registry){
            registry.addMapping("/api/**")
            .allowedOrigins("nitconf.vercel.app")
            .allowedMethods("GET", "POST", "PUT", "DELETE")
            .allowedHeaders("*")
            .allowCredentials(true)
            .maxAge(3600);

        }
}
