
package com.bootx.miniprogram.service.impl;

import com.bootx.miniprogram.dao.MemberDao;
import com.bootx.miniprogram.entity.App;
import com.bootx.miniprogram.entity.Member;
import com.bootx.miniprogram.service.MemberRankService;
import com.bootx.miniprogram.service.MemberService;
import com.bootx.service.impl.BaseServiceImpl;
import com.bootx.util.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * Service - 素材目录
 * 
 * @author blackboy
 * @version 1.0
 */
@Service
public class MemberServiceImpl extends BaseServiceImpl<Member, Long> implements MemberService {

	@Autowired
	private MemberDao memberDao;
	@Autowired
	private MemberRankService memberRankService;

	@Override
	public Member find(String openId, App app) {
		return memberDao.find(openId,app);
	}

	@Override
	public Member create(Map<String,String> map, App app) {
		String openId = map.get("openid");
		String unionid = map.get("unionid");
		String sessionKey = map.get("session_key");

		Map<String,Object> result = new HashMap<>();
		result.put("openId",openId);
		result.put("unionid",unionid);
		result.put("sessionKey",sessionKey);

		Member member = memberDao.find("openId",openId);
		if(member==null){
			member = new Member();
			member.setOpenId(openId);
			member.setUnionid(unionid);
			member.setSessionKey(sessionKey);
			member.setApp(app);
			member.setMoney(BigDecimal.ZERO);
			member.setGameLevel(0);
			member.setLevel(0);
			member.setCartIndex(0);
			member.setHouseIndex(0);
			member.setJobIndex(0);
			member.setMemberRank(memberRankService.findDefault());
			// return super.save(member);
			return member;
		}
		return member;
	}

	@Override
	public Member findByUserTokenAndApp(String userToken, App app) {
		return find(JWTUtils.getKey(userToken,"openid"),app);
	}

	@Override
	public Map<String, Object> getData(Member member) {
		Map<String,Object> data = new HashMap<>();
		data.put("nickName",member.getNickName());
		data.put("money",member.getMoney());
		data.put("ticket",3);
		data.put("level",member.getLevel());
		data.put("carIndex",member.getCartIndex());
		data.put("houseIndex",member.getHouseIndex());
		data.put("jobIndex",member.getJobIndex());
		return data;
	}
}