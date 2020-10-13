package com.bootx.miniprogram.controller;

import com.bootx.common.Result;
import com.bootx.miniprogram.entity.Member;
import com.bootx.miniprogram.entity.MemberRank;
import com.bootx.miniprogram.service.AppService;
import com.bootx.miniprogram.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController("miniprogramUserController")
@RequestMapping("/api/user")
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
    public Result index(String appCode, String appSecret,String userToken,Member memberInfo){
        Member member = memberService.findByUserTokenAndApp(userToken,appService.findByCodeAndSecret(appCode,appSecret));
        if(member!=null){
            member.setNickName(memberInfo.getNickName());
            member.setGender(memberInfo.getGender());
            member.setCity(memberInfo.getCity());
            member.setProvince(memberInfo.getProvince());
            member.setCountry(memberInfo.getCountry());
            member.setAvatarUrl(memberInfo.getAvatarUrl());
            memberService.update(member);
        }
        return Result.success(memberService.getData(member));
    }

    /**
     * ta:
     * status: 1
     * residue_time: 0
     * ticket: "7"
     * money: "1"
     * gold: "0"
     * service_flag: 0
     * rank_img: "https://bbs.zhuchenkeji.shop/attachment/yf_zhaocha_resource/images/rank/rank6.png"
     * rank_name_img: "https://bbs.zhuchenkeji.shop/attachment/yf_zhaocha_resource/images/rank/rank6_1.png"
     * value_name: "机敏青铜"
     * rank: "6"
     * will_title: "还差42关晋级优秀黄铜"
     * share_flag: 1
     * help_flag: 1
     * @param appCode
     * @param appSecret
     * @param userToken
     * @return
     */
    @GetMapping("/level")
    public Result level(String appCode, String appSecret,String userToken){
        Map<String,Object> data = new HashMap<>();
        Member member = memberService.findByUserTokenAndApp(userToken,appService.findByCodeAndSecret(appCode,appSecret));
        if(member!=null){
            MemberRank memberRank = member.getMemberRank();
            data.put("money",member.getMoney());
            data.put("rankImg",memberRank.getRankImg());
            data.put("rankNameImg",memberRank.getRankNameImg());
            data.put("rankName",memberRank.getName());
            data.put("rank",memberRank.getId());
            data.put("residueTime",550);
            data.put("willTitle","还差"+(memberRank.getNext().getLevel()-member.getGameLevel())+"关晋级"+memberRank.getNext().getName());
        }
        return Result.success(data);
    }
}
