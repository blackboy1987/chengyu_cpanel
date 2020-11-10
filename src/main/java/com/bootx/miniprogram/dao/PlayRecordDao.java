
package com.bootx.miniprogram.dao;

import com.bootx.dao.BaseDao;
import com.bootx.miniprogram.entity.Member;
import com.bootx.miniprogram.entity.PlayRecord;

/**
 * Dao - 素材目录
 * 
 * @author blackboy
 * @version 1.0
 */
public interface PlayRecordDao extends BaseDao<PlayRecord, Long> {

    PlayRecord findToday(Member member);
}