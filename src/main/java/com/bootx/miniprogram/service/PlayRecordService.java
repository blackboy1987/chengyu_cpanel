
package com.bootx.miniprogram.service;

import com.bootx.miniprogram.entity.Member;
import com.bootx.miniprogram.entity.PlayRecord;
import com.bootx.service.BaseService;

/**
 * Service - 插件
 * 
 * @author blackboy
 * @version 1.0
 */
public interface PlayRecordService extends BaseService<PlayRecord,Long> {

    PlayRecord findToday(Member member);

    PlayRecord create(Member member);
}