package com.bootx.miniprogram.controller.idom;

import com.bootx.common.Result;
import com.bootx.entity.BaseEntity;
import com.bootx.miniprogram.entity.App;
import com.bootx.miniprogram.entity.SiteInfo;
import com.bootx.miniprogram.service.AppService;
import com.bootx.miniprogram.service.SiteInfoService;
import com.bootx.miniprogram.util.EhCacheUtils;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController("miniprogramIdiomSiteController")
@RequestMapping("/idiom/api/site")
public class SiteController {

    @Autowired
    private AppService appService;
    @Autowired
    private SiteInfoService siteInfoService;

    @PostMapping
    @JsonView({BaseEntity.ViewView.class})
    public Result index(String appCode, String appSecret,Long siteInfoId){
        Object result = EhCacheUtils.getCacheSiteInfo(siteInfoId);
        if(result==null){
            EhCacheUtils.setCacheSiteInfo(siteInfoService.find(siteInfoId));
            result = EhCacheUtils.getCacheSiteInfo(siteInfoId);
        }
        return Result.success(result);
    }

    @GetMapping("/update")
    @JsonView({BaseEntity.ViewView.class})
    public Result update(Long id){
        App app = appService.find(id);
        SiteInfo siteInfo = app.getSiteInfo();
        Map<String,Object> extras = new HashMap<>();
        extras.put("deductionPoint",50);
        extras.put("browseVideoRewardPoint",1000);
        extras.put("everyLevelReward",1);
        extras.put("everyLevelRewardMoney",0.2);
        extras.put("firstLoginRewardMoney",1);
        extras.put("firstLoginRewardPoint",1000);
        extras.put("deductionMoney",0.1);
        extras.put("shareRewardPoint",500);
        extras.put("deductionType",0);
        extras.put("deductionMoney",0.01);
        // 0 扣积分。1：扣现金
        extras.put("deductionType",0);
        siteInfo.setExtras(extras);
        siteInfoService.update(siteInfo);
        return Result.success("");
    }
}
