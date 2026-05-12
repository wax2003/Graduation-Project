package com.example.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvConfig implements WebMvcConfigurer {

    /**
     * 静态资源映射：文件路径映射为网络路径
     *
     * @param registry
     */

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // addResourceHandler：设置网络路径
        registry.addResourceHandler("/view/**")
                .addResourceLocations("file:C:\\Users\\24420\\Desktop\\webapp\\springboot\\work\\src\\assets\\");
    }
}
