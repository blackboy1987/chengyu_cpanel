
package com.bootx.miniprogram.dao;

import com.bootx.common.Page;
import com.bootx.common.Pageable;
import com.bootx.dao.BaseDao;
import com.bootx.miniprogram.entity.Member;
import com.bootx.miniprogram.entity.MemberDepositLog;

/**
 * Dao - 会员预存款记录
 * 
 * @author IGOMALL  Team
 * @version 1.0
 */
public interface MemberDepositLogDao extends BaseDao<MemberDepositLog, Long> {

	/**
	 * 查找会员预存款记录分页
	 * 
	 * @param member
	 *            会员
	 * @param pageable
	 *            分页信息
	 * @return 会员预存款记录分页
	 */
	Page<MemberDepositLog> findPage(Member member, Pageable pageable);

}