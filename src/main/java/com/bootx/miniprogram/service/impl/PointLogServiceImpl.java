
package com.bootx.miniprogram.service.impl;

import com.bootx.miniprogram.dao.PointLogDao;
import com.bootx.miniprogram.entity.PointLog;
import com.bootx.miniprogram.service.PointLogService;
import com.bootx.service.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bootx.common.Page;
import com.bootx.common.Pageable;
import com.bootx.miniprogram.entity.Member;


/**
 * Service - 积分记录
 * 
 * @author IGOMALL  Team
 * @version 1.0
 */
@Service
public class PointLogServiceImpl extends BaseServiceImpl<PointLog, Long> implements PointLogService {

	@Autowired
	private PointLogDao pointLogDao;

	@Transactional(readOnly = true)
	public Page<PointLog> findPage(Member member, Pageable pageable) {
		return pointLogDao.findPage(member, pageable);
	}

}