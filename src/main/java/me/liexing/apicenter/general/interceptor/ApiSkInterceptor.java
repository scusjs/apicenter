package me.liexing.apicenter.general.interceptor;

import me.liexing.apicenter.general.service.SkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@Component
public class ApiSkInterceptor implements HandlerInterceptor{
    @Autowired
    private SkService skService;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        Map parameterMap = request.getParameterMap();
        try {
            response.getOutputStream().print("sk error, please check!");
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (parameterMap.containsKey("sk")){
            return skService.isValid(((String[])parameterMap.get("sk"))[0], request.getHeader("origin"));
        }

        return false;
    }
}
