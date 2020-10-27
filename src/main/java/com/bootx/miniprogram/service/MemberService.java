
package com.bootx.miniprogram.service;

import com.bootx.miniprogram.entity.App;
import com.bootx.miniprogram.entity.Member;
import com.bootx.miniprogram.entity.MemberDepositLog;
import com.bootx.miniprogram.entity.PointLog;
import com.bootx.service.BaseService;

import java.math.BigDecimal;
import java.util.Map;

/**
 * Service - 插件
 * 
 * @author blackboy
 * @version 1.0
 */
public interface MemberService extends BaseService<Member,Long> {

    Member find(String openId, App app);

    Member create(Map<String,String > map, App app);

    Member findByUserTokenAndApp(String userToken, App app);


    Map<String,Object> getData(Member member);


    /**
     * 增加余额
     *
     * @param member
     *            会员
     * @param amount
     *            值
     * @param type
     *            类型
     * @param memo
     *            备注
     */
    void addBalance(Member member, BigDecimal amount, MemberDepositLog.Type type, String memo);

    /**
     * 增加积分
     *
     * @param member
     *            会员
     * @param amount
     *            值
     * @param type
     *            类型
     * @param memo
     *            备注
     */
    void addPoint(Member member, long amount, PointLog.Type type, String memo);

    /**
     * 增加消费金额
     *
     * @param member
     *            会员
     * @param amount
     *            值
     */
    void addAmount(Member member, BigDecimal amount);
}