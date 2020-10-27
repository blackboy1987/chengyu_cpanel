
package com.bootx.miniprogram.service.impl;

import com.bootx.common.Page;
import com.bootx.common.Pageable;
import com.bootx.miniprogram.dao.MemberDepositLogDao;
import com.bootx.miniprogram.entity.Member;
import com.bootx.miniprogram.entity.MemberDepositLog;
import com.bootx.miniprogram.service.MemberDepositLogService;
import com.bootx.service.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service - 会员预存款记录
 * 
 * @author IGOMALL  Team
 * @version 1.0
 */
@Service
public class MemberDepositLogServiceImpl extends BaseServiceImpl<MemberDepositLog, Long> implements MemberDepositLogService {

	@Autowired
	private MemberDepositLogDao memberDepositLogDao;

	@Transactional(readOnly = true)
	public Page<MemberDepositLog> findPage(Member member, Pageable pageable) {
		return memberDepositLogDao.findPage(member, pageable);
	}

}