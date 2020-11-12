package com.bootx.miniprogram.controller.idom;

import com.bootx.common.Result;
import com.bootx.entity.BaseEntity;
import com.bootx.miniprogram.entity.*;
import com.bootx.miniprogram.service.*;
import com.fasterxml.jackson.annotation.JsonView;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

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
        PlayRecord playRecord = playRecordService.findToday(member);
        if(playRecord==null){
            playRecord = playRecordService.create(member);
        }
        playRecord.setLevelCount(playRecord.getLevelCount()+1);
        playRecord.setContinuousLeveCount(playRecord.getContinuousLeveCount()+1);
        playRecordService.update(playRecord);
        return Result.success(checkRedPackage(app,levelCount));
    }


    @PostMapping("/error")
    @JsonView(BaseEntity.ViewView.class)
    public Result error (String appCode, String appSecret, String userToken,Integer level,Integer levelCount) {
        App app = appService.findByCodeAndSecret(appCode,appSecret);
        Member member = memberService.findByUserTokenAndApp(userToken,app);
        PlayRecord playRecord = playRecordService.findToday(member);
        if(playRecord==null){
            playRecord = playRecordService.create(member);
        }
        playRecord.setContinuousLeveCount(0);
        playRecordService.update(playRecord);
        return Result.success("");
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
    public Result redpackage (String appCode, String appSecret, String userToken,Integer level,Integer level1,Long parentId,Integer openRedPackageType) {
        if(openRedPackageType==null){
            openRedPackageType = 0;
        }
        Map<String,Object> map = new HashMap<>();
        if(level1==null){
            level1 = 0;
        }
        App app = appService.findByCodeAndSecret(appCode,appSecret);
        SiteInfo siteInfo = app.getSiteInfo();
        Integer everyLevelReward = Integer.valueOf(siteInfo.getExtras().get("everyLevelReward").toString());
        Double everyLevelRewardMoney = Double.valueOf(siteInfo.getExtras().get("everyLevelRewardMoney").toString());
        Double maxLevelRewardMoney = Double.valueOf(siteInfo.getExtras().get("maxLevelRewardMoney").toString());
        if(level1>=everyLevelReward){
            BigDecimal money = new BigDecimal(Math.random()*everyLevelRewardMoney);
            String memo="过关奖励："+money;
            if(openRedPackageType==1){
                money = money.add(money);
                memo=memo+",翻倍奖励："+money;
            }
            if(money.compareTo(new BigDecimal(0.01))<0){
                money = new BigDecimal(0.01);
            }else if(money.compareTo(new BigDecimal(maxLevelRewardMoney))>0){
                money = new BigDecimal(maxLevelRewardMoney);
            }
            Member member = memberService.findByUserTokenAndApp(userToken,app);
            // 写入红包记录
            memberService.addBalance(member,money, MemberDepositLog.Type.reward,memo);
            PlayRecord playRecord = playRecordService.findToday(member);
            if(playRecord==null){
                playRecord = playRecordService.create(member);
            }
            playRecord.setMoney(playRecord.getMoney().add(money));
            playRecordService.update(playRecord);



            map.put("money",setScale(money));
            map.put("userInfo",memberService.getData(member));

            // 写入推荐人和其他相关人员的奖励
            distributionReward(money,member,parentId);


            return Result.success(map);
        }
        return Result.error("");
    }

    private void distributionReward(BigDecimal money, Member member,Long parentId) {
        Member parent = memberService.find(member.getParentId());
        if(parent==null){
            parent = memberService.find(parentId);
            if(parent!=null){
                // 设置推荐人
                member.setParentId(parentId);
                memberService.update(member);
                memberService.addBalance(parent,money.multiply(new BigDecimal(0.05)), MemberDepositLog.Type.reward,member.getNickName()+"过关,奖励");
            }
        }else{
            memberService.addBalance(parent,money.multiply(new BigDecimal(0.05)), MemberDepositLog.Type.reward,member.getNickName()+"过关,奖励");
        }
        List<Long> ids = member.getParentIds();
        for (Long id:ids) {
            Member parent1 = memberService.find(id);
            if(parent1!=null){
                memberService.addBalance(parent1,money.multiply(new BigDecimal(0.03)).divide(new BigDecimal(ids.size()),1), MemberDepositLog.Type.reward,member.getNickName()+"过关，通用奖励");
            }
        }
    }

    @PostMapping("/share")
    @JsonView(BaseEntity.ViewView.class)
    public Result redpackage (String appCode, String appSecret, String userToken,Long parentId) {
        App app = appService.findByCodeAndSecret(appCode,appSecret);
        Member member = memberService.findByUserTokenAndApp(userToken,app);
        Member parent = memberService.find(parentId);
        // 设置推荐人和积分奖励
        if(member!=null&&parent==null&&member.getParentId()==null && member.getId().compareTo(parentId)!=0){
            member.setParentId(parentId);
            memberService.update(member);
            // 积分奖励
            SiteInfo siteInfo = app.getSiteInfo();
            Integer shareRewardPoint = Integer.valueOf(siteInfo.getExtras().get("shareRewardPoint").toString());
            if(shareRewardPoint>0){
                memberService.update(parent);
                memberService.addPoint(parent,shareRewardPoint, PointLog.Type.reward,"分享奖励积分");
            }
        }
        // 写入到parentIds中
        List<Long> parentIds = member.getParentIds();
        if(member!=null&&parentIds!=null&&member.getParentId()!=null&&parentId!=null&&!parentIds.contains(parentId)&&parentId.compareTo(member.getParentId())!=0){
            parentIds.add(parentId);
            member.setParentIds(parentIds);
            memberService.update(member);
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
        List<Map<String,Object>> list = jdbcTemplate.queryForList("select X.credit,X.member_id from (select FORMAT(credit, 2) credit,member_id, count(distinct member_id) from member_deposit_log WHERE app_id=? and credit>0 group by member_id LIMIT 5) as X where credit>0;",app.getId());
        List<Map<String, Object>> result = list.stream().map(item -> {
            item.put("nickName", memberService.find(Long.valueOf(item.get("member_id") + "")).getNickName());

            return item;
        }).collect(Collectors.toList());


        return Result.success(result);
    }


    private BigDecimal setScale(BigDecimal amount) {
        return amount.setScale(2, BigDecimal.ROUND_UP);
    }

    private List<String> getGanRao(Word word) {
        List<String> ganrao = new ArrayList<>();
        String sql = "SELECT DISTINCT(text) FROM word WHERE  pin_yin='" + word.getPinYin() + "' and text !='"+word.getText()+"' AND id >= ((SELECT MAX(id) FROM word)-(SELECT MIN(id) FROM word)) * RAND() + (SELECT MIN(id) FROM word)  LIMIT 3";
        List<Map<String, Object>> maps = jdbcTemplate.queryForList(sql);
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


    @PostMapping("/more_game")
    public Result moreGame(String appCode, String appSecret){
        App app = appService.findByCodeAndSecret(appCode,appSecret);
        List<Map<String, Object>> list = jdbcTemplate.queryForList("select logo,memo,`name`,path,app_id1 appId from more_game WHERE is_enabled=true and app_id=? ORDER BY orders ASC;", app.getId());
        return Result.success(list);
    }




    @GetMapping("/demo1234")
    public Result demo1234(){
        List<Idiom1> all = idiom1Service.findAll();
        for (Idiom1 idiom1:all) {
            idiom1.setFullText(StringUtils.join(idiom1.getText(),""));
            jdbcTemplate.update("update idiom1 set full_text=? where id=?",idiom1.getFullText(),idiom1.getId());

        }

        return Result.success("");
    }


    @GetMapping("/demo1")
    public Result demo1() throws IOException {
        int level = 30804;
        for (int i = 1; i <= 1000; i++) {
            Idiom1 idiom1 = new Idiom1();
            Document document = Jsoup.parse(new URL("http://cy.5156edu.com/html4/" + i + ".html"), 20000);
            Element table3 = document.getElementById("table3");
            if(table3==null){
                continue;
            }
            Element tbody = table3.getElementsByTag("tbody").first();
            if(tbody!=null){
                tbody = tbody.getElementsByTag("table").first().getElementsByTag("tbody").first();
            }


            try {
                Elements trs = tbody.getElementsByTag("tr");
                Element tr0 = trs.get(0);
                if(tr0!=null){
                    String text = tr0.text();
                    idiom1.setFullText(text);
                }else{
                    continue;
                }

                Element tr1 = trs.get(1);
                if(tr1!=null){
                    Elements tds = tr1.getElementsByTag("td");
                    // 拼音
                    Element td1 = tds.get(1);
                    idiom1.setPinYin(Arrays.asList(td1.text().split(" ")));
                    // 简拼
                    Element td3 = tds.get(3);
                    idiom1.setJianPin(td3.text());
                }else{
                    continue;
                }


                Element tr2 = trs.get(2);
                if(tr2!=null){
                    Elements tds = tr2.getElementsByTag("td");
                    // 近义词
                    Element td1 = tds.get(1);
                    idiom1.setJinYiCi(td1.text());
                    // 反义词
                    Element td3 = tds.get(3);
                    idiom1.setFanYiCi(td3.text());
                }else{
                    continue;
                }

                // 用法
                Element tr3 = trs.get(3);
                if(tr3!=null){
                    Elements tds = tr3.getElementsByTag("td");
                    // 用法
                    Element td1 = tds.get(1);
                    idiom1.setYongFa(td1.text());
                }else{
                    continue;
                }

                // 解释
                Element tr4 = trs.get(4);
                if(tr4!=null){
                    Elements tds = tr4.getElementsByTag("td");
                    // 解释
                    Element td1 = tds.get(1);
                    idiom1.setJieSi(td1.text());
                }else{
                    continue;
                }
                // 出处
                Element tr5 = trs.get(5);
                if(tr5!=null){
                    Elements tds = tr5.getElementsByTag("td");
                    // 出处
                    Element td1 = tds.get(1);
                    idiom1.setChuChu(td1.text());
                }else{
                    continue;
                }
                // 例子
                Element tr6 = trs.get(6);
                if(tr6!=null){
                    Elements tds = tr6.getElementsByTag("td");
                    // 例子
                    Element td1 = tds.get(1);
                    idiom1.setLiZi(td1.text());
                }else{
                    continue;
                }
                // 歇后语
                Element tr7 = trs.get(7);
                if(tr7!=null){
                    Elements tds = tr7.getElementsByTag("td");
                    // 歇后语
                    Element td1 = tds.get(1);
                    idiom1.setXieHouYu(td1.text());
                }else{
                    continue;
                }

                // 谜语
                Element tr8 = trs.get(8);
                if(tr8!=null){
                    Elements tds = tr8.getElementsByTag("td");
                    // 谜语
                    Element td1 = tds.get(1);
                    idiom1.setMiYu(td1.text());
                }else{
                    continue;
                }
                // 成语故事
                Element tr9 = trs.get(9);
                if(tr9!=null){
                    Elements tds = tr9.getElementsByTag("td");
                    // 成语故事
                    Element td1 = tds.get(1);
                    idiom1.setChengYuGuShi(td1.text());
                }else{
                    continue;
                }
                Idiom1 idiom11 = idiom1Service.findByFullText(idiom1.getFullText());
                if(idiom11!=null){
                    idiom11.setChengYuGuShi(idiom1.getChengYuGuShi());
                    idiom11.setMiYu(idiom1.getMiYu());
                    idiom11.setXieHouYu(idiom1.getXieHouYu());
                    idiom11.setLiZi(idiom1.getLiZi());
                    idiom11.setJieSi(idiom1.getJieSi());
                    idiom11.setYongFa(idiom1.getYongFa());
                    idiom11.setFanYiCi(idiom1.getFanYiCi());
                    idiom11.setJinYiCi(idiom1.getJinYiCi());
                    idiom11.setJianPin(idiom1.getJianPin());
                    idiom1Service.update(idiom11);
                }else{
                    idiom1.setLevel(++level);
                    idiom1.setPosition(0);
                    System.out.println(idiom1.getFullText()+"===================================================================================================================================================================================="+i);
                    idiom1Service.save(idiom1);
                }
            }catch (Exception e){
                System.out.println("error===================================================================================================================================================================================="+i);
            }
        }



        return Result.error("");
    }


    @GetMapping("/demo2")
    public Result demo2() throws IOException {
        int level = 30804;
        for (int i = 1; i <= 1000; i++) {

            String index = "";
            if(i<10){
                index="000"+i;
            }else if(i<100){
                index="00"+i;
            }else if(i<1000){
                index="0"+i;
            }else if(i<10000){
                index=""+i;
            }


            Idiom1 idiom1 = new Idiom1();
            Document document = Jsoup.parse(new URL("http://www.hydcd.com/cy/chengyu/cy0" + index + ".htm"), 20000);
            Element table1 = document.getElementById("table1");
            if(table1==null){
                continue;
            }
            Elements tds = table1.getElementsByTag("td");
            if(tds==null){
                continue;
            }
            for (Element td:tds) {
                Elements a = td.getElementsByTag("a");
                String url = a.attr("href").replace("..","http://www.hydcd.com/cy");
                String text = a.text();

            }
            try {
                Idiom1 idiom11 = idiom1Service.findByFullText(idiom1.getFullText());
                if(idiom11!=null){
                    idiom11.setChengYuGuShi(idiom1.getChengYuGuShi());
                    idiom11.setMiYu(idiom1.getMiYu());
                    idiom11.setXieHouYu(idiom1.getXieHouYu());
                    idiom11.setLiZi(idiom1.getLiZi());
                    idiom11.setJieSi(idiom1.getJieSi());
                    idiom11.setYongFa(idiom1.getYongFa());
                    idiom11.setFanYiCi(idiom1.getFanYiCi());
                    idiom11.setJinYiCi(idiom1.getJinYiCi());
                    idiom11.setJianPin(idiom1.getJianPin());
                    idiom1Service.update(idiom11);
                }else{
                    idiom1.setLevel(++level);
                    idiom1.setPosition(0);
                    System.out.println(idiom1.getFullText()+"===================================================================================================================================================================================="+i);
                    idiom1Service.save(idiom1);
                }
            }catch (Exception e){
                System.out.println("error===================================================================================================================================================================================="+i);
            }
        }



        return Result.error("");
    }
}
