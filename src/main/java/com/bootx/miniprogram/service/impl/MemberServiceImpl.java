
package com.bootx.miniprogram.service.impl;

import com.bootx.miniprogram.dao.MemberDao;
import com.bootx.miniprogram.dao.MemberDepositLogDao;
import com.bootx.miniprogram.dao.PointLogDao;
import com.bootx.miniprogram.entity.*;
import com.bootx.miniprogram.service.MemberRankService;
import com.bootx.miniprogram.service.MemberService;
import com.bootx.service.impl.BaseServiceImpl;
import com.bootx.util.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.persistence.LockModeType;
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

	@Autowired
	private MemberDepositLogDao memberDepositLogDao;
	@Autowired
	private PointLogDao pointLogDao;

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
			member.setIsAuth(false);
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
			member.setAmount(BigDecimal.ZERO);
			member.setPoint(1000L);
			return super.save(member);
			// return member;
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
		if(member==null){
			return data;
		}
		data.put("nickName",member.getNickName());
		data.put("money",setScale(member.getMoney()));
		data.put("point",member.getPoint());
		data.put("ticket",3);
		data.put("level",member.getLevel());
		data.put("carIndex",member.getCartIndex());
		data.put("houseIndex",member.getHouseIndex());
		data.put("jobIndex",member.getJobIndex());
		data.put("isAuth",member.getIsAuth());
		data.put("wechat",member.getWechat());
		data.put("mobile",member.getMobile());
		data.put("name",member.getName());
		return data;
	}

	private BigDecimal setScale(BigDecimal amount) {
		return amount.setScale(2, BigDecimal.ROUND_UP);
	}

	@Override
	public void addBalance(Member member, BigDecimal amount, MemberDepositLog.Type type, String memo) {
		Assert.notNull(member,"");
		Assert.notNull(amount,"");
		Assert.notNull(type,"");

		if (amount.compareTo(BigDecimal.ZERO) == 0) {
			return;
		}

		if (!LockModeType.PESSIMISTIC_WRITE.equals(memberDao.getLockMode(member))) {
			memberDao.flush();
			memberDao.refresh(member, LockModeType.PESSIMISTIC_WRITE);
		}

		Assert.notNull(member.getMoney(),"");
		Assert.state(member.getMoney().add(amount).compareTo(BigDecimal.ZERO) >= 0,"");

		member.setMoney(member.getMoney().add(amount));
		memberDao.flush();

		MemberDepositLog memberDepositLog = new MemberDepositLog();
		memberDepositLog.setType(type);
		memberDepositLog.setCredit(amount.compareTo(BigDecimal.ZERO) > 0 ? amount : BigDecimal.ZERO);
		memberDepositLog.setDebit(amount.compareTo(BigDecimal.ZERO) < 0 ? amount.abs() : BigDecimal.ZERO);
		memberDepositLog.setBalance(member.getMoney());
		memberDepositLog.setMemo(memo);
		memberDepositLog.setMember(member);
		memberDepositLogDao.persist(memberDepositLog);
	}

	@Override
	public void addPoint(Member member, long amount, PointLog.Type type, String memo) {
		Assert.notNull(member,"");
		Assert.notNull(type,"");

		if (amount == 0) {
			return;
		}

		if (!LockModeType.PESSIMISTIC_WRITE.equals(memberDao.getLockMode(member))) {
			memberDao.flush();
			memberDao.refresh(member, LockModeType.PESSIMISTIC_WRITE);
		}

		Assert.notNull(member.getPoint(),"");
		Assert.state(member.getPoint() + amount >= 0,"");

		member.setPoint(member.getPoint() + amount);
		memberDao.flush();

		PointLog pointLog = new PointLog();
		pointLog.setType(type);
		pointLog.setCredit(amount > 0 ? amount : 0L);
		pointLog.setDebit(amount < 0 ? Math.abs(amount) : 0L);
		pointLog.setBalance(member.getPoint());
		pointLog.setMemo(memo);
		pointLog.setMember(member);
		pointLogDao.persist(pointLog);
	}

	@Override
	public void addAmount(Member member, BigDecimal amount) {
		Assert.notNull(member,"");
		Assert.notNull(amount,"");

		if (amount.compareTo(BigDecimal.ZERO) == 0) {
			return;
		}

		if (!LockModeType.PESSIMISTIC_WRITE.equals(memberDao.getLockMode(member))) {
			memberDao.flush();
			memberDao.refresh(member, LockModeType.PESSIMISTIC_WRITE);
		}

		Assert.notNull(member.getAmount(),"");
		Assert.state(member.getAmount().add(amount).compareTo(BigDecimal.ZERO) >= 0,"");

		member.setAmount(member.getAmount().add(amount));
		MemberRank memberRank = member.getMemberRank();
		memberDao.flush();
	}

}