package com.bootx.miniprogram.controller;

import com.bootx.common.Result;
import com.bootx.entity.BaseEntity;
import com.bootx.miniprogram.entity.App;
import com.bootx.miniprogram.entity.SiteInfo;
import com.bootx.miniprogram.service.AppService;
import com.bootx.miniprogram.service.MemberRankService;
import com.bootx.miniprogram.service.SiteInfoService;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController("miniprogramSiteController")
@RequestMapping("/api/site")
public class SiteController {

    @Autowired
    private MemberRankService memberRankService;
    @Autowired
    private AppService appService;
    @Autowired
    private SiteInfoService siteInfoService;

    @PostMapping
    @JsonView({BaseEntity.ViewView.class})
    public Result index(String appCode, String appSecret){
        Map<String,Object> data = new HashMap<>();

        App app = appService.findByCodeAndSecret(appCode,appSecret);
        SiteInfo siteInfo = app.getSiteInfo();
        Map<String,Object> reward = new HashMap<>();
        reward.put("open",1);
        reward.put("unit","元");
        reward.put("max_unit",30);
        reward.put("img","https://bbs.zhuchenkeji.shop/attachment/images/51/2020/09/s1DD3FW1W3tjbBZzDBWbK77fgbTss3.jpg");
        reward.put("msg","满30元开启");
        siteInfo.setReward(reward);
        siteInfoService.update(siteInfo);


        data.put("siteInfo",app.getSiteInfo());
        data.put("rankList",memberRankService.findAll());
        return Result.success(data);
    }

    @PostMapping("/update")
    @JsonView({BaseEntity.ViewView.class})
    public Result update(Long id){

        App app = appService.find(id);
        SiteInfo siteInfo = app.getSiteInfo();
        Map<String,Object> extras = new HashMap<>();
        extras.put("deductionPoint",50);
        extras.put("browseVideoRewardPoint",1000);
        extras.put("everyLevelReward",5);
        extras.put("everyLevelRewardMoney",3);
        extras.put("firstLoginRewardMoney",1);
        extras.put("firstLoginRewardPoint",1000);
        siteInfo.setExtras(extras);

        siteInfoService.update(siteInfo);
        return Result.success("");
    }
}
