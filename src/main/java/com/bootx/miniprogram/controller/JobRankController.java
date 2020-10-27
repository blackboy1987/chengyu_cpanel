package com.bootx.miniprogram.controller;

import com.bootx.common.Result;
import com.bootx.miniprogram.service.JobRankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("miniprogramJobRankController")
@RequestMapping("/api/job_rank")
public class JobRankController {

    @Autowired
    private JobRankService jobRankService;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @PostMapping("/list")
    public Result list(String code, String appCode, String appSecret){




        return Result.success(jdbcTemplate.queryForList("select level,name from job_rank order by orders desc "));
    }
}
