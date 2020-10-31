package com.bootx.miniprogram.controller.idom;

import com.bootx.common.Result;
import com.bootx.miniprogram.entity.Idiom1;
import com.bootx.miniprogram.service.Idiom1Service;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.atomic.AtomicReference;

@RestController("miniprogramIdiomIdiom111Controller")
@RequestMapping("/idiom/api/idiom")
public class IdiomController {

    @Autowired
    private Idiom1Service idiom1Service;
    @Autowired
    private JdbcTemplate jdbcTemplate;

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


    @GetMapping("/random")
    private Result random(){
        AtomicReference<Integer> level = new AtomicReference<>(28128);
        for (Long i=1L;i<31;i++) {
            System.out.println("---------------------"+i+"---------------------------------------------------------------------------------------------------------------");
            List<Map<String,Object>> result = jdbcTemplate.queryForList("SELECT id FROM idiom1 as t1 WHERE t1.level>28127 and t1.id>=(RAND()*(SELECT MAX(id) FROM idiom1))LIMIT 1000;");
            result.stream().forEach(item->{
                String sql = "UPDATE idiom1 set `level` = "+level+" where id="+item.get("id")+";";
                System.out.println(sql);
                Integer count = jdbcTemplate.update(sql);
                level.set(level.get() + count);
            });



        }
        return Result.success("ok");
    }

    @GetMapping("/random2")
    private Result random2(){
        AtomicReference<Integer> level = new AtomicReference<>(1);
        List<Map<String,Object>> result = jdbcTemplate.queryForList("select id from idiom1 ORDER BY `level` asc;");
        result.stream().forEach(item->{
            String sql = "UPDATE idiom1 set `level` = "+level+" where id="+item.get("id")+" and level !="+level+";";
            System.out.println(sql);
            Integer count = jdbcTemplate.update(sql);
            level.set(level.get() + count);
        });
        return Result.success("ok");
    }

}
