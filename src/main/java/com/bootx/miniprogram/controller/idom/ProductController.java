package com.bootx.miniprogram.controller.idom;

import com.bootx.common.Result;
import com.bootx.miniprogram.entity.App;
import com.bootx.miniprogram.entity.Member;
import com.bootx.miniprogram.service.AppService;
import com.bootx.miniprogram.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController("miniprogramIdiomProductController")
@RequestMapping("/idiom/api/product")
public class ProductController {

    @Autowired
    private AppService appService;
    @Autowired
    private MemberService memberService;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @PostMapping
    public Result index(String code, String appCode, String appSecret,String userToken){
        App app = appService.findByCodeAndSecret(appCode,appSecret);
        Member member = memberService.findByUserTokenAndApp(userToken,app);
        if(member!=null){
            List<Map<String, Object>> products = jdbcTemplate.queryForList("select id,name,price,sales,stock,image,market_price from product where is_enabled=true and app_id=? and price <="+member.getMoney()+" order by orders asc ", app.getId());

            return Result.success(products);
        }
        return Result.success(Collections.emptyList());


    }
}
