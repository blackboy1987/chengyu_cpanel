package com.bootx.miniprogram.controller.idom;

import com.bootx.common.Result;
import com.bootx.miniprogram.entity.Member;
import com.bootx.miniprogram.service.AppService;
import com.bootx.miniprogram.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("miniprogramIdiomUserController")
@RequestMapping("/idiom/api/user")
public class UserController {

    @Autowired
    private MemberService memberService;
    @Autowired
    private AppService appService;

    @GetMapping("/info")
    public Result index(String appCode, String appSecret,String userToken){
        Member member = memberService.findByUserTokenAndApp(userToken,appService.findByCodeAndSecret(appCode,appSecret));
        return Result.success(memberService.getData(member));
    }
    @GetMapping("/update")
    public Result update(Long id){
        Member member = memberService.find(id);
        if(member!=null){
            member.setPoint(10000L);
            memberService.update(member);
        }
        return Result.success(memberService.getData(member));
    }
}
