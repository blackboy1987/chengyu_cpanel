package com.bootx.miniprogram.controller.idom;

import com.bootx.common.Result;
import com.bootx.miniprogram.entity.Idiom1;
import com.bootx.miniprogram.service.Idiom1Service;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

@RestController("miniprogramIdiomIdiom111Controller")
@RequestMapping("/idiom/api/idiom")
public class IdiomController {

    @Autowired
    private Idiom1Service idiom1Service;

    @GetMapping("/index")
    private Result index(){
        for (Long i=1L;i<30804L;i++) {
            Idiom1 idiom = idiom1Service.find(i);
            if(idiom!=null){
                Integer position = new Random().nextInt(idiom.getText().size());
                String current = idiom.getText().get(position);
                while (StringUtils.equalsIgnoreCase(",",current)){
                    position = new Random().nextInt(idiom.getText().size());
                }
                idiom.setPosition(position);

                idiom1Service.update(idiom);
            }
        }
        return Result.success("ok");
    }
}
