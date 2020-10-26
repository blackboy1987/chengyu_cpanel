package com.bootx.miniprogram.controller.idom;

import com.bootx.common.Result;
import com.bootx.miniprogram.entity.App;
import com.bootx.miniprogram.entity.Member;
import com.bootx.miniprogram.service.*;
import com.bootx.util.JWTUtils;
import com.bootx.util.JsonUtils;
import com.bootx.util.WebUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController("miniprogramIdiomLoginController")
@RequestMapping("/idiom/api/login")
public class LoginController {

    @Autowired
    private MemberService memberService;
    @Autowired
    private AppService appService;

    @GetMapping
    public Result index(String code, String appCode, String appSecret){
        Map<String,Object> data = new HashMap<>();
        String url = "https://api.weixin.qq.com/sns/jscode2session";
        App app = appService.findByCodeAndSecret(appCode,appSecret);
        Map<String,Object> params = new HashMap<>();
        params.put("appid",app.getAppId());
        params.put("secret",app.getAppSecret());
        params.put("js_code",code);
        params.put("grant_type","authorization_code");
        Map<String,String> result = JsonUtils.toObject(WebUtils.get(url, params), new TypeReference<Map<String, String>>() {});
        Member member = memberService.create(result,app);
        if(member!=null){
            Map<String,Object> data1 = new HashMap<>(result);
            data1.put("id",member.getId());
            data.put("id",member.getId());
            data.putAll(memberService.getData(member));
            data.put("token", JWTUtils.create(member.getId()+"",data1));
        }
        return Result.success(data);
    }
}
