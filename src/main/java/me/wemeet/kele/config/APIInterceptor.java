package me.wemeet.kele.config;

import me.wemeet.kele.common.response.KeleResponseStatus;
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
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String accessToken = request.getHeader("Access-Token");
        String path = request.getServletPath();
        boolean flag;
        if (accessToken == null || accessToken.isBlank()) {
            flag = false;
        } else {
            if (path.startsWith("/admin")) {
                flag = commonService.testAdminAccessToken(accessToken);
            } else {
                flag = commonService.testAccessToken(accessToken);
            }
        }
        if (!flag) {
            response.sendError(KeleResponseStatus.INSUFFICIENT_PERMISSION.code, KeleResponseStatus.INSUFFICIENT_PERMISSION.message);
        }

        return flag;
    }

}
