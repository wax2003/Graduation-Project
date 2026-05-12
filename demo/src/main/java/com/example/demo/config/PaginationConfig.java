package com.example.demo.config;


import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**分页拦截器
 * offset = （pageNumber - 1） * pageSize
 * 作用：拦截page（）方法，获取pageNumber和pageSize，计算sql中limit的offset偏移量，进行分页
 */
@Configuration
public class PaginationConfig {

    @Bean
    public PaginationInterceptor paginationInterceptor(){
        return new PaginationInterceptor();
    }
}
