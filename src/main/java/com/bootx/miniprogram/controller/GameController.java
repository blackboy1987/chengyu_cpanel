package com.bootx.miniprogram.controller;

import com.bootx.common.Result;
import com.bootx.entity.BaseEntity;
import com.bootx.miniprogram.entity.Idiom;
import com.bootx.miniprogram.service.IdiomService;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("miniprogramGameController")
@RequestMapping("/api/game")
public class GameController {

    @Autowired
    private IdiomService idiomService;

    @PostMapping("/level")
    @JsonView(BaseEntity.ViewView.class)
    public Result level(String code, String appCode, String appSecret,Long id){
        return Result.success(idiomService.find(id));
    }

    @PostMapping("/level2")
    public Result level(String code, String appCode, String appSecret){
        for (Long i = 1993L; i < 3009L; i++) {
            level3(code,appCode,appSecret,i);
        }
        return Result.success("ok");
    }


    @PostMapping("/level3")
    public Result level3(String code, String appCode, String appSecret,Long id){
        Idiom idiom = idiomService.find(id);
        if(idiom!=null){
            idiom.setGameBoxes1(idiom.getGameBoxes().get(0));
            new Thread(()->{
                    idiomService.update(idiom);
            }).start();
        }
        return Result.success("ok");
    }
}
