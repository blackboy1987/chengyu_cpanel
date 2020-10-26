package com.bootx.miniprogram.controller.idom;

import com.bootx.common.Result;
import com.bootx.entity.BaseEntity;
import com.bootx.miniprogram.entity.*;
import com.bootx.miniprogram.service.AppService;
import com.bootx.miniprogram.service.IdiomService;
import com.bootx.miniprogram.service.MemberService;
import com.bootx.miniprogram.service.WordService;
import com.bootx.util.HanyuPinyinUtils;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
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
    private IdiomService idiomService;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping("/index")
    public String index () {
        String start="4e00";//定义一个字符串变量为4e00
        String end="9fa5";//定义一个字符串变量为9fa5
        int s=Integer.parseInt(start, 16);//将16进制字符start转换为10进制整数
        int e=Integer.parseInt(end, 16);//将16进制字符end转换为10进制整数
        for (int i=s;i<=e;i++){//for循环实现汉字的输出
            String str=(char)i+ "";//类型转换
            Word word = wordService.findByText(str);
            if(word==null){
                word = new Word();
                System.out.println("text:"+str);
                word.setText(str);
                try {
                    word.setPinYin(HanyuPinyinUtils.ToPinyin(word.getText()));
                }catch (Exception exception){
                    exception.printStackTrace();
                }
                wordService.save(word);
            }
        }

        return "ok";
    }


    @GetMapping("/game")
    @JsonView(BaseEntity.ViewView.class)
    public Result game (String appCode, String appSecret, String userToken) {
        Map<String,Object> data = new HashMap<>();
        App app = appService.findByCodeAndSecret(appCode,appSecret);
        Member member = memberService.findByUserTokenAndApp(userToken,app);
        Integer level = member.getLevel();
        if(level==null){
            level = 0;
        }
        Idiom idiom = idiomService.findByLeve(level+1);
        String idiomStr = idiom.getIdioms().get(0);
        char[] chars = idiomStr.toCharArray();
        // 干扰词
        Integer position = new Random().nextInt(4);
        char text = chars[position];
        Word word = wordService.findByText(text+"");
        List<String> ganrao = getGanRao(word);
        ganrao.add(text+"");
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
    @GetMapping("/success")
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
    @GetMapping("/discount")
    @JsonView(BaseEntity.ViewView.class)
    public Result discount (String appCode, String appSecret, String userToken,Integer level) {
        App app = appService.findByCodeAndSecret(appCode,appSecret);
        SiteInfo siteInfo = app.getSiteInfo();
        Long deductionPoint = Long.valueOf(siteInfo.getExtras().get("deductionPoint").toString());
        Member member = memberService.findByUserTokenAndApp(userToken,app);
        if(member.getPoint().compareTo(deductionPoint)<0){
            return Result.error("积分余额不足");
        }
        member.setPoint(member.getPoint()-deductionPoint);
        memberService.update(member);
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
    @GetMapping("/redpackage")
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
            return Result.success(setScale(money));
        }
        return Result.error("");
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
        Collections.shuffle(ganrao);
        Collections.shuffle(ganrao);
        return ganrao;
    }


}
