package com.bootx.miniprogram.interceptor;

import cn.hutool.crypto.digest.DigestUtil;
import cn.hutool.crypto.digest.Digester;
import com.bootx.common.Result;
import com.bootx.miniprogram.entity.App;
import com.bootx.miniprogram.service.AppService;
import com.bootx.util.JsonUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;


public class AppValidateInterceptor  extends HandlerInterceptorAdapter {

    @Autowired
    private AppService appService;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String appCode = request.getParameter("appCode");
        String appSecret = request.getParameter("appSecret");
        App app = appService.findByCodeAndSecret(appCode,appSecret);
        System.out.println("---------------------------------------------------------------aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        if(app==null){
            Map<String, Object> data = new HashMap<>();
            data.put("data", Result.error("非法调用"));
            response.setStatus(999);
            response.setContentType("text/html; charset=UTF-8");
            JsonUtils.writeValue(response.getWriter(), data);
            return false;
        }
        // 校验
        Map<String,Object> params = new HashMap<>();
        params.put("appid",app.getAppId());
        params.put("secret",app.getAppSecret());
        params.put("code",appCode);
        Digester digester = DigestUtil.digester("sm3");
        String secret = digester.digestHex(JsonUtils.toJson(params));
        if(!StringUtils.equals(secret,app.getSecret())){
            Map<String, Object> data = new HashMap<>();
            data.put("data", Result.error("非法调用"));
            response.setStatus(999);
            response.setContentType("text/html; charset=UTF-8");
            JsonUtils.writeValue(response.getWriter(), data);
            return false;
        }
        return true;
    }
}
