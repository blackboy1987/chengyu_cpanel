package com.bootx.miniprogram.controller;

import com.bootx.common.Result;
import com.bootx.miniprogram.entity.*;
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
import java.util.List;
import java.util.Map;

@RestController("miniprogramIndexController")
@RequestMapping("/api")
public class IndexController {

    @Autowired
    private MemberService memberService;
    @Autowired
    private AppService appService;

    @Autowired
    private JobRankService jobRankService;
    @Autowired
    private CarRankService carRankService;
    @Autowired
    private HouseRankService houseRankService;


    @GetMapping("/login")
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

    @GetMapping("/job_rank")
    public String jobRank(){
        String jobRankStr = "[{\"index\":19,\"level\":1180,\"name\":\"皇上\",\"resource\":{\"head\":\"home-rwt20@2x.png?ver=190704\",\"body\":\"home-rwst20@2x.png?ver=190704\"},\"type\":1},{\"index\":18,\"level\":1060,\"name\":\"亲王\",\"resource\":{\"head\":\"home-rwt19@2x.png?ver=190704\",\"body\":\"home-rwst19@2x.png?ver=190704\"},\"type\":1},{\"index\":17,\"level\":940,\"name\":\"郡王\",\"resource\":{\"head\":\"home-rwt18@2x.png?ver=190704\",\"body\":\"home-rwst18@2x.png?ver=190704\"},\"type\":1},{\"index\":16,\"level\":820,\"name\":\"宰相\",\"resource\":{\"head\":\"home-rwt17@2x.png?ver=190704\",\"body\":\"home-rwst17@2x.png?ver=190704\"},\"type\":1},{\"index\":15,\"level\":700,\"name\":\"丞相\",\"resource\":{\"head\":\"home-rwt16@2x.png?ver=190704\",\"body\":\"home-rwst16@2x.png?ver=190704\"},\"type\":1},{\"index\":14,\"level\":610,\"name\":\"内阁总管\",\"resource\":{\"head\":\"home-rwt15@2x.png?ver=190704\",\"body\":\"home-rwst15@2x.png?ver=190704\"},\"type\":1},{\"index\":13,\"level\":530,\"name\":\"礼部尚书\",\"resource\":{\"head\":\"home-rwt14@2x.png?ver=190704\",\"body\":\"home-rwst14@2x.png?ver=190704\"},\"type\":1},{\"index\":12,\"level\":450,\"name\":\"知府\",\"resource\":{\"head\":\"home-rwt13@2x.png?ver=190704\",\"body\":\"home-rwst13@2x.png?ver=190704\"},\"type\":1},{\"index\":11,\"level\":370,\"name\":\"翰林学士\",\"resource\":{\"head\":\"home-rwt12@2x.png?ver=190704\",\"body\":\"home-rwst12@2x.png?ver=190704\"},\"type\":1},{\"index\":10,\"level\":290,\"name\":\"太学博士\",\"resource\":{\"head\":\"home-rwt11@2x.png?ver=190704\",\"body\":\"home-rwst11@2x.png?ver=190704\"},\"type\":1},{\"index\":9,\"level\":240,\"name\":\"县令\",\"resource\":{\"head\":\"home-rwt10@2x.png?ver=190704\",\"body\":\"home-rwst10@2x.png?ver=190704\"},\"type\":1},{\"index\":8,\"level\":200,\"name\":\"侍郎\",\"resource\":{\"head\":\"home-rwt9@2x.png?ver=190704\",\"body\":\"home-rwst9@2x.png?ver=190704\"},\"type\":1},{\"index\":7,\"level\":160,\"name\":\"芝麻官\",\"resource\":{\"head\":\"home-rwt8@2x.png?ver=190704\",\"body\":\"home-rwst8@2x.png?ver=190704\"},\"type\":1},{\"index\":6,\"level\":120,\"name\":\"状元\",\"resource\":{\"head\":\"home-rwt7@2x.png?ver=190704\",\"body\":\"home-rwst7@2x.png?ver=190704\"},\"type\":1},{\"index\":5,\"level\":80,\"name\":\"榜眼\",\"resource\":{\"head\":\"home-rwt6@2x.png?ver=190704\",\"body\":\"home-rwst6@2x.png?ver=190704\"},\"type\":1},{\"index\":4,\"level\":55,\"name\":\"探花\",\"resource\":{\"head\":\"home-rwt5@2x.png?ver=190704\",\"body\":\"home-rwst5@2x.png?ver=190704\"},\"type\":1},{\"index\":3,\"level\":35,\"name\":\"举人\",\"resource\":{\"head\":\"home-rwt4@2x.png?ver=190704\",\"body\":\"home-rwst4@2x.png?ver=190704\"},\"type\":1},{\"index\":2,\"level\":15,\"name\":\"秀才\",\"resource\":{\"head\":\"home-rwt3@2x.png?ver=190704\",\"body\":\"home-rwst3@2x.png?ver=190704\"},\"type\":1},{\"index\":1,\"level\":2,\"name\":\"书生\",\"resource\":{\"head\":\"home-rwt2@2x.png?ver=190704\",\"body\":\"home-rwst2@2x.png?ver=190704\"},\"type\":1},{\"index\":0,\"level\":0,\"name\":\"乞丐\",\"resource\":{\"head\":\"home-rwt1@2x.png?ver=190704\",\"body\":\"home-rwst1@2x.png?ver=190704\"},\"type\":1}]";

        List<JobRank> jobRankList = JsonUtils.toObject(jobRankStr, new TypeReference<List<JobRank>>() {
        });

        for (JobRank jobRank:jobRankList) {
            jobRankService.save(jobRank);
        }
        return "ok";

    }


    @GetMapping("/car_rank")
    public String carRank(){
        String carRankStr = "[{\"index\":19,\"level\":1270,\"name\":\"金身龙辇\",\"resHeight\":289,\"resWidth\":527,\"resource\":{\"tail\":\"home-zjwb19@2x.png?ver=190704\",\"body\":\"home-zj19@2x.png?ver=190704\"},\"tailHeight\":74,\"tailWidth\":70,\"type\":3},{\"index\":18,\"level\":1150,\"name\":\"银顶马车\",\"resHeight\":330,\"resWidth\":518,\"resource\":{\"tail\":\"home-zjwb18@2x.png?ver=190704\",\"body\":\"home-zj18@2x.png?ver=190704\"},\"tailHeight\":73,\"tailWidth\":70,\"type\":3},{\"index\":17,\"level\":1030,\"name\":\"四驾马车\",\"resHeight\":304,\"resWidth\":500,\"resource\":{\"tail\":\"home-zjwb17@2x.png?ver=190704\",\"body\":\"home-zj17@2x.png?ver=190704\"},\"tailHeight\":74,\"tailWidth\":70,\"type\":3},{\"index\":16,\"level\":910,\"name\":\"豪华马车\",\"resHeight\":287,\"resWidth\":470,\"resource\":{\"tail\":\"home-zjwb16@2x.png?ver=190704\",\"body\":\"home-zj16@2x.png?ver=190704\"},\"tailHeight\":73,\"tailWidth\":53,\"type\":3},{\"index\":15,\"level\":790,\"name\":\"三驾马车\",\"resHeight\":264,\"resWidth\":471,\"resource\":{\"tail\":\"home-zjwb15@2x.png?ver=190704\",\"body\":\"home-zj15@2x.png?ver=190704\"},\"tailHeight\":73,\"tailWidth\":53,\"type\":3},{\"index\":14,\"level\":670,\"name\":\"雕花马车\",\"resHeight\":221,\"resWidth\":472,\"resource\":{\"tail\":\"home-zjwb14@2x.png?ver=190704\",\"body\":\"home-zj14@2x.png?ver=190704\"},\"tailHeight\":73,\"tailWidth\":53,\"type\":3},{\"index\":13,\"level\":590,\"name\":\"双驾马车\",\"resHeight\":221,\"resWidth\":471,\"resource\":{\"tail\":\"home-zjwb13@2x.png?ver=190704\",\"body\":\"home-zj13@2x.png?ver=190704\"},\"tailHeight\":73,\"tailWidth\":53,\"type\":3},{\"index\":12,\"level\":510,\"name\":\"木屋马车\",\"resHeight\":222,\"resWidth\":470,\"resource\":{\"tail\":\"home-zjwb12@2x.png?ver=190704\",\"body\":\"home-zj12@2x.png?ver=190704\"},\"tailHeight\":73,\"tailWidth\":53,\"type\":3},{\"index\":11,\"level\":430,\"name\":\"千里马车\",\"resHeight\":200,\"resWidth\":440,\"resource\":{\"tail\":\"home-zjwb11@2x.png?ver=190704\",\"body\":\"home-zj11@2x.png?ver=190704\"},\"tailHeight\":73,\"tailWidth\":53,\"type\":3},{\"index\":10,\"level\":350,\"name\":\"竹棚马车\",\"resHeight\":228,\"resWidth\":452,\"resource\":{\"tail\":\"home-zjwb10@2x.png?ver=190704\",\"body\":\"home-zj10@2x.png?ver=190704\"},\"tailHeight\":72,\"tailWidth\":59,\"type\":3},{\"index\":9,\"level\":270,\"name\":\"赤兔马车\",\"resHeight\":228,\"resWidth\":450,\"resource\":{\"tail\":\"home-zjwb9@2x.png?ver=190704\",\"body\":\"home-zj9@2x.png?ver=190704\"},\"tailHeight\":72,\"tailWidth\":59,\"type\":3},{\"index\":8,\"level\":230,\"name\":\"草棚马车\",\"resHeight\":230,\"resWidth\":450,\"resource\":{\"tail\":\"home-zjwb8@2x.png?ver=190704\",\"body\":\"home-zj8@2x.png?ver=190704\"},\"tailHeight\":70,\"tailWidth\":54,\"type\":3},{\"index\":7,\"level\":190,\"name\":\"枣红马\",\"resHeight\":168,\"resWidth\":406,\"resource\":{\"tail\":\"home-zjwb7@2x.png?ver=190704\",\"body\":\"home-zj7@2x.png?ver=190704\"},\"tailHeight\":70,\"tailWidth\":53,\"type\":3},{\"index\":6,\"level\":150,\"name\":\"单驾马车\",\"resHeight\":168,\"resWidth\":400,\"resource\":{\"tail\":\"home-zjwb6@2x.png?ver=190704\",\"body\":\"home-zj6@2x.png?ver=190704\"},\"tailHeight\":78,\"tailWidth\":54,\"type\":3},{\"index\":5,\"level\":110,\"name\":\"小马驹\",\"resHeight\":166,\"resWidth\":241,\"resource\":{\"tail\":\"home-zjwb5@2x.png?ver=190704\",\"body\":\"home-zj5@2x.png?ver=190704\"},\"tailHeight\":68,\"tailWidth\":54,\"type\":3},{\"index\":4,\"level\":70,\"name\":\"牛车\",\"resHeight\":150,\"resWidth\":406,\"resource\":{\"tail\":\"home-zjwb4@2x.png?ver=190704\",\"body\":\"home-zj4@2x.png?ver=190704\"},\"tailHeight\":54,\"tailWidth\":42,\"type\":3},{\"index\":3,\"level\":50,\"name\":\"水牛\",\"resHeight\":127,\"resWidth\":228,\"resource\":{\"tail\":\"home-zjwb3@2x.png?ver=190704\",\"body\":\"home-zj3@2x.png?ver=190704\"},\"tailHeight\":51,\"tailWidth\":40,\"type\":3},{\"index\":2,\"level\":30,\"name\":\"驴车\",\"resHeight\":157,\"resWidth\":322,\"resource\":{\"tail\":\"home-zjwb2@2x.png?ver=190704\",\"body\":\"home-zj2@2x.png?ver=190704\"},\"tailHeight\":60,\"tailWidth\":40,\"type\":3},{\"index\":1,\"level\":10,\"name\":\"毛驴\",\"resHeight\":156,\"resWidth\":232,\"resource\":{\"tail\":\"home-zjwb1@2x.png?ver=190704\",\"body\":\"home-zj1@2x.png?ver=190704\"},\"tailHeight\":60,\"tailWidth\":40,\"type\":3}]";

        List<CarRank> jobRankList = JsonUtils.toObject(carRankStr, new TypeReference<List<CarRank>>() {
        });

        for (CarRank carRank:jobRankList) {
            carRankService.save(carRank);
        }
        return "ok";
    }

    @GetMapping("/house_rank")
    public String houseRank(){
        String houseRankStr = "[{\"index\":19,\"level\":1240,\"name\":\"金銮殿\",\"resource\":{\"body\":\"home-fz19@2x.png?ver=190704\"},\"type\":2},{\"index\":18,\"level\":1120,\"name\":\"恭王府\",\"resource\":{\"body\":\"home-fz18@2x.png?ver=190704\"},\"type\":2},{\"index\":17,\"level\":1000,\"name\":\"郡王府\",\"resource\":{\"body\":\"home-fz17@2x.png?ver=190704\"},\"type\":2},{\"index\":16,\"level\":880,\"name\":\"相府\",\"resource\":{\"body\":\"home-fz16@2x.png?ver=190704\"},\"type\":2},{\"index\":15,\"level\":760,\"name\":\"太傅府\",\"resource\":{\"body\":\"home-fz15@2x.png?ver=190704\"},\"type\":2},{\"index\":14,\"level\":650,\"name\":\"内务府\",\"resource\":{\"body\":\"home-fz14@2x.png?ver=190704\"},\"type\":2},{\"index\":13,\"level\":570,\"name\":\"顺天府\",\"resource\":{\"body\":\"home-fz13@2x.png?ver=190704\"},\"type\":2},{\"index\":12,\"level\":490,\"name\":\"开封府\",\"resource\":{\"body\":\"home-fz12@2x.png?ver=190704\"},\"type\":2},{\"index\":11,\"level\":410,\"name\":\"国子监\",\"resource\":{\"body\":\"home-fz11@2x.png?ver=190704\"},\"type\":2},{\"index\":10,\"level\":330,\"name\":\"府衙\",\"resource\":{\"body\":\"home-fz10@2x.png?ver=190704\"},\"type\":2},{\"index\":9,\"level\":260,\"name\":\"县衙\",\"resource\":{\"body\":\"home-fz9@2x.png?ver=190704\"},\"type\":2},{\"index\":8,\"level\":220,\"name\":\"小四合院\",\"resource\":{\"body\":\"home-fz8@2x.png?ver=190704\"},\"type\":2},{\"index\":7,\"level\":180,\"name\":\"庭院小屋\",\"resource\":{\"body\":\"home-fz7@2x.png?ver=190704\"},\"type\":2},{\"index\":6,\"level\":140,\"name\":\"琉璃瓦房\",\"resource\":{\"body\":\"home-fz6@2x.png?ver=190704\"},\"type\":2},{\"index\":5,\"level\":100,\"name\":\"青砖瓦房\",\"resource\":{\"body\":\"home-fz5@2x.png?ver=190704\"},\"type\":2},{\"index\":4,\"level\":65,\"name\":\"两室木屋\",\"resource\":{\"body\":\"home-fz4@2x.png?ver=190704\"},\"type\":2},{\"index\":3,\"level\":45,\"name\":\"独室木屋\",\"resource\":{\"body\":\"home-fz3@2x.png?ver=190704\"},\"type\":2},{\"index\":2,\"level\":25,\"name\":\"土坯房\",\"resource\":{\"body\":\"home-fz2@2x.png?ver=190704\"},\"type\":2},{\"index\":1,\"level\":5,\"name\":\"茅草屋\",\"resource\":{\"body\":\"home-fz1@2x.png?ver=190704\"},\"type\":2}]";
        List<HouseRank> jobRankList = JsonUtils.toObject(houseRankStr, new TypeReference<List<HouseRank>>() {
        });

        for (HouseRank houseRank:jobRankList) {
            houseRankService.save(houseRank);
        }
        return "ok";

    }
}
