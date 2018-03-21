package me.liexing.apicenter.general.interceptor;

import me.liexing.apicenter.general.service.SkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

@Component
public class ApiSkInterceptor implements HandlerInterceptor{
    @Autowired
    private SkService skService;

    public static String SESSION_SK_KEY = "sk_info";
    static String API_SK_KEY = "sk";
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (request.getSession().getAttribute(SESSION_SK_KEY) != null) {
            return true;
        }
        Map parameterMap = request.getParameterMap();
        if (parameterMap.containsKey(API_SK_KEY)){
            if(skService.isValid(((String[])parameterMap.get(API_SK_KEY))[0], request.getHeader(HttpHeaders.ORIGIN))){
                try {
                    String origin_url = new URL(request.getHeader(HttpHeaders.ORIGIN)).getHost();
                    request.getSession().setAttribute(SESSION_SK_KEY, origin_url);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                return true;
            }
        }
        try {
            response.getOutputStream().print("sk error, please check!");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
