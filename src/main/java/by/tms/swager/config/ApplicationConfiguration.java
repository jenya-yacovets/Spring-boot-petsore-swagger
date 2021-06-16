package by.tms.swager.config;

import by.tms.swager.interceptor.AuthorizationUserInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class ApplicationConfiguration extends WebMvcConfigurerAdapter {

    private final AuthorizationUserInterceptor authorizationUserInterceptor;

    public ApplicationConfiguration(AuthorizationUserInterceptor authorizationUserInterceptor) {
        this.authorizationUserInterceptor = authorizationUserInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authorizationUserInterceptor).addPathPatterns("/store", "/store/*", "/store/*/**", "/pet","/pet/*", "/pet/*/**");
    }
}
