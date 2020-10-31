/*
 * Copyright 2005-2017 shopxx.net. All rights reserved.
 * Support: http://www.shopxx.net
 * License: http://www.shopxx.net/license
 */
package com.bootx.miniprogram.entity;

import com.bootx.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonView;

import javax.persistence.*;

/**
 * Entity - 积分记录
 * 
 * @author IGOMALL  Team
 * @version 1.0
 */
@Entity
public class PointLog extends BaseEntity<Long> {

	private static final long serialVersionUID = -1758056800285585097L;

	/**
	 * 类型
	 */
	public enum Type {

		/**
		 * 积分赠送
		 */
		reward,

		/**
		 * 积分兑换
		 */
		exchange,

		/**
		 * 积分兑换撤销
		 */
		undoExchange,

		/**
		 * 积分调整
		 */
		adjustment
	}

	/**
	 * 类型
	 */
	@JsonView(BaseView.class)
	@Column(nullable = false, updatable = false)
	private Type type;

	/**
	 * 获取积分
	 */
	@JsonView(BaseView.class)
	@Column(nullable = false, updatable = false)
	private Long credit;

	/**
	 * 扣除积分
	 */
	@JsonView(BaseView.class)
	@Column(nullable = false, updatable = false)
	private Long debit;

	/**
	 * 当前积分
	 */
	@JsonView(BaseView.class)
	@Column(nullable = false, updatable = false)
	private Long balance;

	/**
	 * 备注
	 */
	@JsonView(BaseView.class)
	@Column(updatable = false)
	private String memo;

	/**
	 * 会员
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false, updatable = false)
	private Member member;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false, updatable = false)
	private App app;

	/**
	 * 获取类型
	 * 
	 * @return 类型
	 */
	public Type getType() {
		return type;
	}

	/**
	 * 设置类型
	 * 
	 * @param type
	 *            类型
	 */
	public void setType(Type type) {
		this.type = type;
	}

	/**
	 * 获取获取积分
	 * 
	 * @return 获取积分
	 */
	public Long getCredit() {
		return credit;
	}

	/**
	 * 设置获取积分
	 * 
	 * @param credit
	 *            获取积分
	 */
	public void setCredit(Long credit) {
		this.credit = credit;
	}

	/**
	 * 获取扣除积分
	 * 
	 * @return 扣除积分
	 */
	public Long getDebit() {
		return debit;
	}

	/**
	 * 设置扣除积分
	 * 
	 * @param debit
	 *            扣除积分
	 */
	public void setDebit(Long debit) {
		this.debit = debit;
	}

	/**
	 * 获取当前积分
	 * 
	 * @return 当前积分
	 */
	public Long getBalance() {
		return balance;
	}

	/**
	 * 设置当前积分
	 * 
	 * @param balance
	 *            当前积分
	 */
	public void setBalance(Long balance) {
		this.balance = balance;
	}

	/**
	 * 获取备注
	 * 
	 * @return 备注
	 */
	public String getMemo() {
		return memo;
	}

	/**
	 * 设置备注
	 * 
	 * @param memo
	 *            备注
	 */
	public void setMemo(String memo) {
		this.memo = memo;
	}

	/**
	 * 获取会员
	 * 
	 * @return 会员
	 */
	public Member getMember() {
		return member;
	}

	/**
	 * 设置会员
	 * 
	 * @param member
	 *            会员
	 */
	public void setMember(Member member) {
		this.member = member;
	}

	public App getApp() {
		return app;
	}

	public void setApp(App app) {
		this.app = app;
	}
}