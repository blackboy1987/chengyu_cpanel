/*
 * Copyright 2008-2018 shopxx.net. All rights reserved.
 * Support: localhost
 * License: localhost/license
 * FileId: 0v4Jk93rxDGjCB+4WsjxS2R4YUSfVL2C
 */
package com.bootx.service.impl;

import com.bootx.dao.SnDao;
import com.bootx.miniprogram.entity.Sn;
import com.bootx.service.SnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service - 序列号
 * 
 * @author 好源++ Team
 * @version 6.1
 */
@Service
public class SnServiceImpl implements SnService {

	@Autowired
	private SnDao snDao;

	@Override
	@Transactional
	public String generate(Sn.Type type) {
		return snDao.generate(type);
	}

}