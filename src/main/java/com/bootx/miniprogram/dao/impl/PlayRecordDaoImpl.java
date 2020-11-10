
package com.bootx.miniprogram.dao.impl;

import com.bootx.dao.impl.BaseDaoImpl;
import com.bootx.miniprogram.dao.PlayRecordDao;
import com.bootx.miniprogram.entity.Member;
import com.bootx.miniprogram.entity.PlayRecord;
import com.bootx.util.DateUtils;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * Dao - 插件配置
 * 
 * @author blackboy
 * @version 1.0
 */
@Repository
public class PlayRecordDaoImpl extends BaseDaoImpl<PlayRecord, Long> implements PlayRecordDao {

    @Override
    public PlayRecord findToday(Member member) {
        if(member==null){
            return null;
        }
        synchronized (member){
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<PlayRecord> criteriaQuery = criteriaBuilder.createQuery(PlayRecord.class);
            Root<PlayRecord> root = criteriaQuery.from(PlayRecord.class);
            criteriaQuery.select(root);
            Predicate restrictions = criteriaBuilder.conjunction();
            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("member"), member));
            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.greaterThanOrEqualTo(root.get("createdDate"), DateUtils.getBeginToday()));
            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.lessThanOrEqualTo(root.get("createdDate"), DateUtils.getEndToday()));
            criteriaQuery.where(restrictions);
            try {
                return findList(criteriaQuery,null,null,null,null).get(0);
            }catch (Exception e){
                return null;
            }
        }
    }
}