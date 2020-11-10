package com.bootx.miniprogram.controller.idom;

import com.bootx.common.Result;
import com.bootx.miniprogram.entity.*;
import com.bootx.miniprogram.service.AppService;
import com.bootx.miniprogram.service.MemberService;
import com.bootx.miniprogram.service.OrderService;
import com.bootx.miniprogram.service.ProductService;
import com.bootx.service.SnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController("miniprogramIdiomOrderController")
@RequestMapping("/idiom/api/order")
public class OrderController {

    @Autowired
    private AppService appService;
    @Autowired
    private MemberService memberService;
    @Autowired
    private ProductService productService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private SnService snService;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @PostMapping("/create")
    public Result index(String code, String appCode, String appSecret,String userToken,Long productId){
        Map<String,Object> data = new HashMap<>();
        App app = appService.findByCodeAndSecret(appCode,appSecret);
        Member member = memberService.findByUserTokenAndApp(userToken,app);
        Product product = productService.find(productId);
        if(member==null){
            return Result.error(-1,"用户不存在");
        }
        if(product==null){
            return Result.error(-1,"商品不存在");
        }
        if(product.getStock()-product.getSales()<=0){
            return Result.error(-1,"商品库存不足");
        }
        if(member.getMoney().compareTo(product.getPrice())<0){
            return Result.error(-1,"账户余额不足");
        }

        product.setStock(product.getStock()-1);
        productService.update(product);


        Order order = new Order();
        order.setApp(product.getApp());
        order.setProductImage(product.getImage());
        order.setSn(snService.generate(Sn.Type.ORDER));
        order.setQuantity(1);
        order.setAmount(product.getPrice().multiply(new BigDecimal(order.getQuantity())));
        order.setMember(member);
        order.setProduct(product);
        order.setProductName(product.getName());
        order.setProductPrice(product.getPrice());
        order.setStatus(0);
        if(member.getMoney().compareTo(order.getAmount())>=0){
            orderService.save(order);
        }
        memberService.addBalance(member,order.getAmount().multiply(new BigDecimal(-1)), MemberDepositLog.Type.orderPayment,"商品兑换");
        data.put("userInfo",memberService.getData(member));
        data.put("code",0);
        return Result.success(data);


    }

    @PostMapping("/list")
    public Result list(String code, String appCode, String appSecret,String userToken){
        App app = appService.findByCodeAndSecret(appCode,appSecret);
        Member member = memberService.findByUserTokenAndApp(userToken,app);
        if(member!=null){
            List<Map<String, Object>> products = jdbcTemplate.queryForList("select status, product_name,product_price,amount,quantity,app_id,sn,product_image from orders where member_id=? and app_id=? ORDER BY created_date DESC;",member.getId(), app.getId());
            return Result.success(products);
        }
        return Result.success(Collections.emptyList());

    }
}
