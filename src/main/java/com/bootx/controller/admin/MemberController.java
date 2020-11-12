package com.bootx.controller.admin;

import com.bootx.common.Pageable;
import com.bootx.common.Result;
import com.bootx.controller.BaseController;
import com.bootx.entity.BaseEntity;
import com.bootx.miniprogram.service.MemberService;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/idiom/api/admin/member")
public class MemberController extends BaseController {

    @Autowired
    private MemberService memberService;

    @PostMapping("/list")
    @JsonView(BaseEntity.ListView.class)
    public Result list(Pageable pageable){
        return Result.success(memberService.findPage(pageable));
    }
}
