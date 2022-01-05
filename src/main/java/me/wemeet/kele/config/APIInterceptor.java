package me.wemeet.kele.config;

import me.wemeet.kele.service.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class APIInterceptor implements HandlerInterceptor {

    private CommonService commonService;

    @Autowired
    public void setCommonService(CommonService commonService) {
        this.commonService = commonService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String accessToken = request.getHeader("Access-Token");
        if (accessToken == null || accessToken.isBlank()) return false;
        return commonService.testAccessToken(accessToken);
    }

}
