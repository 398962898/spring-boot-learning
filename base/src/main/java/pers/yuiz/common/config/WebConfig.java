package pers.yuiz.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import pers.yuiz.common.interceptor.KeepLoginInterceptor;

@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new KeepLoginInterceptor()).addPathPatterns("/**");
        super.addInterceptors(registry);
    }
}
