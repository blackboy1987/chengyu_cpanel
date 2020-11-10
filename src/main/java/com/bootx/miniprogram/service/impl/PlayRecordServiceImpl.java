
package com.bootx.miniprogram.service.impl;

import com.bootx.miniprogram.dao.PlayRecordDao;
import com.bootx.miniprogram.entity.Member;
import com.bootx.miniprogram.entity.PlayRecord;
import com.bootx.miniprogram.service.PlayRecordService;
import com.bootx.service.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * Service - 素材目录
 * 
 * @author blackboy
 * @version 1.0
 */
@Service
public class PlayRecordServiceImpl extends BaseServiceImpl<PlayRecord, Long> implements PlayRecordService {

    @Autowired
    private PlayRecordDao playRecordDao;

    @Override
    public PlayRecord findToday(Member member) {
        return playRecordDao.findToday(member);
    }

    @Override
    public PlayRecord create(Member member) {
        PlayRecord playRecord = new PlayRecord();
        playRecord.setMember(member);
        playRecord.setContinuousLeveCount(0);
        playRecord.setLevelCount(0);
        playRecord.setMoney(BigDecimal.ZERO);
        return super.save(playRecord);
    }
}