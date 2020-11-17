package com.bootx.miniprogram.controller.idom;

import com.bootx.common.Result;
import com.bootx.entity.BaseEntity;
import com.bootx.miniprogram.entity.*;
import com.bootx.miniprogram.service.*;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.*;

@RestController("miniprogramIdiomIndex1Controller")
@RequestMapping("/idiom/api1")
public class Index1Controller {

    @Autowired
    private WordService wordService;

    @Autowired
    private MemberService memberService;
    @Autowired
    private AppService appService;
    @Autowired
    private Idiom1Service idiom1Service;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private PlayRecordService playRecordService;

    @PostMapping("/game")
    @JsonView(BaseEntity.ViewView.class)
    public Result game (String appCode, String appSecret, String userToken) {
        Map<String,Object> data = new HashMap<>();
        App app = appService.findByCodeAndSecret(appCode,appSecret);
        Member member = memberService.findByUserTokenAndApp(userToken,app);
        if(member==null){
            return Result.error("请先登录");
        }
        Integer level = member.getLevel();
        if(level==null){
            level = 0;
        }
        Idiom1 idiom = idiom1Service.findByLeve(level+1);
        String text = idiom.getText().get(idiom.getPosition());
        System.out.println("text:"+text+":"+idiom.getPosition()+":"+idiom.getText());
        Word word = wordService.findByText(text);
        List<String> ganrao = getGanRao(word);
        ganrao.add(text);
        Collections.shuffle(ganrao);
        Collections.shuffle(ganrao);
        data.put("idiom",idiom.getText());
        data.put("answers",ganrao);
        data.put("level",level+1);
        data.put("position",idiom.getPosition());
        return Result.success(data);
    }

    private List<String> getGanRao(Word word) {
        List<String> ganrao = new ArrayList<>();
        String sql = "SELECT DISTINCT(text) FROM word WHERE  pin_yin='" + word.getPinYin() + "' and text !='"+word.getText()+"' AND id >= ((SELECT MAX(id) FROM word)-(SELECT MIN(id) FROM word)) * RAND() + (SELECT MIN(id) FROM word)  LIMIT 9";
        List<Map<String, Object>> maps = jdbcTemplate.queryForList(sql);
        maps.stream().forEach(map->{
            ganrao.add(map.get("text")+"");
        });
        return ganrao;
    }

    @PostMapping("/discount")
    @JsonView(BaseEntity.ViewView.class)
    public Result discount (String appCode, String appSecret, String userToken,Integer level) {
        App app = appService.findByCodeAndSecret(appCode,appSecret);
        SiteInfo siteInfo = app.getSiteInfo();
        Long deductionPoint = Long.valueOf(siteInfo.getExtras().get("deductionPoint").toString());
        Integer deductionType = Integer.valueOf(siteInfo.getExtras().get("deductionType").toString());
        BigDecimal deductionMoney = new BigDecimal(siteInfo.getExtras().get("deductionMoney").toString());
        Member member = memberService.findByUserTokenAndApp(userToken,app);
        if(member.getPoint().compareTo(deductionPoint)<0){
            return Result.error("积分余额不足");
        }
        memberService.addPoint(member,-deductionPoint, PointLog.Type.adjustment,"游戏扣除积分");
        return Result.success("");
    }

    @PostMapping("/check")
    @JsonView(BaseEntity.ViewView.class)
    public Result check (String appCode, String appSecret, String userToken,Integer level) {
        App app = appService.findByCodeAndSecret(appCode,appSecret);
        SiteInfo siteInfo = app.getSiteInfo();
        Long deductionPoint = Long.valueOf(siteInfo.getExtras().get("deductionPoint").toString());
        Member member = memberService.findByUserTokenAndApp(userToken,app);
        // 检测积分账户
        if (member.getPoint().compareTo(deductionPoint)<0){
            return Result.error("");
        }
        return Result.success("");
    }

    @PostMapping("/addPoint")
    @JsonView(BaseEntity.ViewView.class)
    public Result addPoint (String appCode, String appSecret, String userToken,String memo) {
        Map<String,Object> data = new HashMap<>();
        App app = appService.findByCodeAndSecret(appCode,appSecret);
        Member member = memberService.findByUserTokenAndApp(userToken,app);
        data.put("code",0);
        if(app == null){
            data.put("code",-1);
            return Result.success(data);
        }
        SiteInfo siteInfo = app.getSiteInfo();
        if(siteInfo==null || siteInfo.getExtras()==null || siteInfo.getExtras().size()==0 || siteInfo.getExtras().get("browseVideoRewardPoint")==null){
            data.put("code",-1);
            return Result.success(data);
        }
        Long browseVideoRewardPoint = Long.valueOf(siteInfo.getExtras().get("browseVideoRewardPoint")+"");
        if(member==null){
            data.put("code",-1);
            return Result.success(data);
        }
        // 积分调整
        data.put("code",0);
        memberService.addPoint(member,browseVideoRewardPoint, PointLog.Type.adjustment,memo);
        data.put("userInfo",memberService.getData(member));
        return Result.success(data);
    }

    @PostMapping("/userInfo")
    @JsonView(BaseEntity.ViewView.class)
    public Result userInfo (String appCode, String appSecret, String userToken) {
        App app = appService.findByCodeAndSecret(appCode,appSecret);
        Member member = memberService.findByUserTokenAndApp(userToken,app);
        return Result.success(memberService.getData(member));
    }
}
