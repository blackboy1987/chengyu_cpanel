package com.bootx.miniprogram.controller.idom;

import com.bootx.Main;
import com.bootx.common.Result;
import com.bootx.entity.BaseEntity;
import com.bootx.miniprogram.entity.*;
import com.bootx.miniprogram.service.AppService;
import com.bootx.miniprogram.service.Idiom1Service;
import com.bootx.miniprogram.service.MemberService;
import com.bootx.miniprogram.service.WordService;
import com.fasterxml.jackson.annotation.JsonView;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.*;

@RestController("miniprogramIdiomIndexController")
@RequestMapping("/idiom/api")
public class IndexController {

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

    @PostMapping("/index")
    private Result index() throws Exception{

        List<String[]> idoms = Main.main();

        for (int i=0;i<idoms.size();i++){
            Idiom1 idiom1 = new Idiom1();
            char[] chars = idoms.get(i)[0].toCharArray();
            for (char c:chars) {
                if(StringUtils.isNotBlank(c+"")){
                    idiom1.getText().add(c+"");
                }
            }
            idiom1.setLevel(i+1);
            String[] chars1 = idoms.get(i)[1].split(" ");
            for (String c:chars1) {
                if(StringUtils.isNotBlank(c)){
                    idiom1.getPinYin().add(c);
                }
            }
            new Thread(()->{
                idiom1Service.save(idiom1);
            }).start();
            Thread.sleep(20);
        }

        return Result.success("aa");
    }

    @PostMapping("/game")
    @JsonView(BaseEntity.ViewView.class)
    public Result game (String appCode, String appSecret, String userToken) {
        Map<String,Object> data = new HashMap<>();
        App app = appService.findByCodeAndSecret(appCode,appSecret);
        Member member = memberService.findByUserTokenAndApp(userToken,app);
        Integer level = member.getLevel();
        if(level==null){
            level = 0;
        }
        Idiom1 idiom = idiom1Service.findByLeve(level+1);
        String idiomStr = StringUtils.join(idiom.getText(),"");
        char[] chars = idiomStr.toCharArray();
        // 干扰词
        Integer position = new Random().nextInt(4);
        char text = chars[position];
        Word word = wordService.findByText(text+"");
        List<String> ganrao = getGanRao(word);
        ganrao.add(text+"");
        Collections.shuffle(ganrao);
        Collections.shuffle(ganrao);
        data.put("idiom",idiomStr);
        data.put("answers",ganrao);
        data.put("level",level+1);
        data.put("position",position);
        return Result.success(data);
    }

    /**
     * 过关了更新等级信息
     * @param appCode
     * @param appSecret
     * @param userToken
     * @param level
     * @return
     */
    @PostMapping("/success")
    @JsonView(BaseEntity.ViewView.class)
    public Result success (String appCode, String appSecret, String userToken,Integer level,Integer levelCount) {
        App app = appService.findByCodeAndSecret(appCode,appSecret);
        Member member = memberService.findByUserTokenAndApp(userToken,app);
        member.setLevel(level);
        memberService.update(member);
        return Result.success(checkRedPackage(app,levelCount));
    }

    private Boolean checkRedPackage(App app,Integer levelCount){
        SiteInfo siteInfo = app.getSiteInfo();
        Integer everyLevelReward = Integer.valueOf(siteInfo.getExtras().get("everyLevelReward").toString());
        if(levelCount>=everyLevelReward){
            return true;
        }
        return false;
    }

    /**
     * 扣除积分
     * @param appCode
     * @param appSecret
     * @param userToken
     * @param level
     * @return
     */
    @PostMapping("/discount")
    @JsonView(BaseEntity.ViewView.class)
    public Result discount (String appCode, String appSecret, String userToken,Integer level) {
        App app = appService.findByCodeAndSecret(appCode,appSecret);
        SiteInfo siteInfo = app.getSiteInfo();
        Long deductionPoint = Long.valueOf(siteInfo.getExtras().get("deductionPoint").toString());
        Member member = memberService.findByUserTokenAndApp(userToken,app);
        if(member.getPoint().compareTo(deductionPoint)<0){
            return Result.error("积分余额不足");
        }
        memberService.addPoint(member,-deductionPoint, PointLog.Type.adjustment,"游戏扣除积分");
        return Result.success("");
    }


    /**
     * 处理红包
     * @param appCode
     * @param appSecret
     * @param userToken
     * @param level
     * @return
     */
    @PostMapping("/redpackage")
    @JsonView(BaseEntity.ViewView.class)
    public Result redpackage (String appCode, String appSecret, String userToken,Integer level,Integer level1) {
        if(level1==null){
            level1 = 0;
        }
        App app = appService.findByCodeAndSecret(appCode,appSecret);
        SiteInfo siteInfo = app.getSiteInfo();
        Integer everyLevelReward = Integer.valueOf(siteInfo.getExtras().get("everyLevelReward").toString());
        Double everyLevelRewardMoney = Double.valueOf(siteInfo.getExtras().get("everyLevelRewardMoney").toString());
        if(level1>=everyLevelReward){
            BigDecimal money = new BigDecimal(Math.random()*everyLevelRewardMoney);
            Member member = memberService.findByUserTokenAndApp(userToken,app);
            member.setMoney(member.getMoney().add(money));
            memberService.update(member);
            // 写入红包记录
            memberService.addBalance(member,money, MemberDepositLog.Type.reward,"过关奖励");

            return Result.success(setScale(money));
        }
        return Result.error("");
    }

    @PostMapping("/share")
    @JsonView(BaseEntity.ViewView.class)
    public Result redpackage (String appCode, String appSecret, String userToken,Long parentId) {
        App app = appService.findByCodeAndSecret(appCode,appSecret);
        Member member = memberService.findByUserTokenAndApp(userToken,app);
        Member parent = memberService.find(parentId);
        if(member!=null&&parent==null&&member.getParentId()==null && member.getId().compareTo(parentId)!=0){
            member.setParentId(parentId);
            memberService.update(member);
            // 积分奖励
            SiteInfo siteInfo = app.getSiteInfo();
            Integer shareRewardPoint = Integer.valueOf(siteInfo.getExtras().get("shareRewardPoint").toString());
            if(shareRewardPoint>0){
                parent.setPoint(parent.getPoint()+shareRewardPoint);
                memberService.update(parent);
                memberService.addPoint(parent,shareRewardPoint, PointLog.Type.reward,"分享奖励积分");
            }
        }
        return Result.success("");
    }
    @PostMapping("/rank")
    @JsonView(BaseEntity.ViewView.class)
    public Result rank (String appCode, String appSecret, Integer page) {
        if(page==null||page<1){
            page = 1;
        }
        Map<String,Object> result = new HashMap<>();
        App app = appService.findByCodeAndSecret(appCode,appSecret);
        List<Map<String,Object>> list = jdbcTemplate.queryForList("select avatar_url,nick_name,level from member where app_id=? and level>0 order by level desc limit ?, 10",app.getId(),10*(page-1));
        if(list.size()==0){
            result.put("hasMore",false);
        }else{
            result.put("hasMore",true);
        }
        result.put("list",list);
        result.put("current",page);
        return Result.success(result);
    }

    @PostMapping("/reward_notice")
    @JsonView(BaseEntity.ViewView.class)
    public Result rewardNotice (String appCode, String appSecret) {
        App app = appService.findByCodeAndSecret(appCode,appSecret);
        List<Map<String,Object>> list = jdbcTemplate.queryForList("select avatar_url,nick_name,level from member where app_id=? ",1);
        return Result.success(list);
    }


    private BigDecimal setScale(BigDecimal amount) {
        return amount.setScale(2, BigDecimal.ROUND_UP);
    }

    private List<String> getGanRao(Word word) {
        List<String> ganrao = new ArrayList<>();
        List<Map<String, Object>> maps = jdbcTemplate.queryForList("SELECT DISTINCT(text) FROM word WHERE  pin_yin='" + word.getPinYin() + "' AND id >= ((SELECT MAX(id) FROM word)-(SELECT MIN(id) FROM word)) * RAND() + (SELECT MIN(id) FROM word)  LIMIT 3");
        maps.stream().forEach(map->{
            ganrao.add(map.get("text")+"");
        });
        return ganrao;
    }


    @PostMapping("/adjust")
    public Result adjust(String appCode, String appSecret, String userToken, Long point, String memo){
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
        if(member.getPoint()+point>0){
            // 积分调整
            data.put("code",0);
            memberService.addPoint(member,browseVideoRewardPoint, PointLog.Type.adjustment,memo);
        }
        data.put("userInfo",memberService.getData(member));
        return Result.success(data);
    }
}
