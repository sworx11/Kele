package me.wemeet.kele.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class APIInterceptorConfigurer implements WebMvcConfigurer {
    private HandlerInterceptor apiInterceptor;

    @Autowired
    public void setApiInterceptor(APIInterceptor apiInterceptor) {
        this.apiInterceptor = apiInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        List<String> excludePaths = new ArrayList<>();
        excludePaths.add("/common/**");
        excludePaths.add("/music/api/**");
        excludePaths.add("/source/**");
        excludePaths.add("/user/verify/**");
        excludePaths.add("/user/register");
        excludePaths.add("/user/login");
        excludePaths.add("/user/logout");
        excludePaths.add("/admin/login");
        excludePaths.add("/admin/logout");
        excludePaths.add("/app-version/**");
        excludePaths.add("/app-notice/**");
        excludePaths.add("/file/**");
        excludePaths.add("/config/**");

        registry.addInterceptor(apiInterceptor).excludePathPatterns(excludePaths);
    }
}
