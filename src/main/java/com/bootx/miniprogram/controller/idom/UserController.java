package com.bootx.miniprogram.controller.idom;

import com.bootx.common.Result;
import com.bootx.miniprogram.entity.Member;
import com.bootx.miniprogram.service.AppService;
import com.bootx.miniprogram.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("miniprogramIdiomUserController")
@RequestMapping("/idiom/api/user")
public class UserController {

    @Autowired
    private MemberService memberService;
    @Autowired
    private AppService appService;

    @PostMapping("/info")
    public Result index(String appCode, String appSecret,String userToken){
        Member member = memberService.findByUserTokenAndApp(userToken,appService.findByCodeAndSecret(appCode,appSecret));
        return Result.success(memberService.getData(member));
    }
    @PostMapping("/update")
    public Result update(Long id){
        Member member = memberService.find(id);
        if(member!=null){
            member.setPoint(10000L);
            memberService.update(member);
            return Result.success(memberService.getData(member));
        }
        return Result.error("");

    }

    @PostMapping("/update1")
    public Result update1(Long id,String name,String mobile,String wechat){
        Member member = memberService.find(id);
        if(member!=null){
            member.setName(name);
            member.setMobile(mobile);
            member.setWechat(wechat);
            memberService.update(member);
            return Result.success(memberService.getData(member));
        }
        return Result.error("");

    }

}
