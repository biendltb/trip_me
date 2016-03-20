/* 
 * Copyright 2016 biendltb.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.biendltb.config;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

/**
 *
 * @author biendltb
 * @email biendltb@gmail.com
 * @version 1.0
 */
@Configuration
@EnableWebMvc
@Import({DefaultConfig.class})
@ComponentScan(basePackages = {"com.biendltb.controller"})
public class WebConfig extends WebMvcConfigurerAdapter{
    private static final Charset UTF8 = Charset.forName("UTF-8");
    
    @Autowired
    public Environment env;
    
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(stringConverter());
    }
    
    private StringHttpMessageConverter stringConverter() {
        StringHttpMessageConverter stringConverter = new StringHttpMessageConverter();
        stringConverter.setSupportedMediaTypes(Arrays.asList(new MediaType("text", "plain", UTF8)));
        return stringConverter;
    }
    
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/assets/**").addResourceLocations("classpath:/src/main/resources/").setCachePeriod(Integer.MAX_VALUE);
        registry.addResourceHandler("/js/**").addResourceLocations("/js/").setCachePeriod(Integer.MAX_VALUE);
        registry.addResourceHandler("/css/**").addResourceLocations("/css/").setCachePeriod(Integer.MAX_VALUE);
        registry.addResourceHandler("/img/**").addResourceLocations("/img/").setCachePeriod(Integer.MAX_VALUE);
    }
    
    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }
    
    @Bean
    public InternalResourceViewResolver getInternalResourceViewResolver() {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setPrefix("/src/main/resources/pages/");
        resolver.setSuffix(".jsp");
        return resolver;
    }
}
