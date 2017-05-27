package pers.yuiz.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import pers.yuiz.common.interceptor.KeepLoginInterceptor;
import pers.yuiz.customer.interceptor.CustomerInterceptor;

@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new KeepLoginInterceptor())
                .addPathPatterns("/**");
        registry.addInterceptor(new CustomerInterceptor())
                .addPathPatterns("/country/**")
                .addPathPatterns("/logout")
                .addPathPatterns("/info");
        super.addInterceptors(registry);
    }
}
