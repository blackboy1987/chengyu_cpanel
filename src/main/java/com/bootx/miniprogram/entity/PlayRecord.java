package com.bootx.miniprogram.entity;

import com.bootx.entity.BaseEntity;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Entity
public class PlayRecord extends BaseEntity<Long> {

    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull
    @JoinColumn(nullable = false,updatable = false)
    private Member member;

    @Min(0)
    @NotNull
    @Column(nullable = false)
    private Integer levelCount;

    @Min(0)
    @NotNull
    @Column(nullable = false)
    private Integer continuousLeveCount;

    @Column(nullable = false, precision = 27, scale = 12)
    private BigDecimal money;

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public Integer getLevelCount() {
        return levelCount;
    }

    public void setLevelCount(Integer levelCount) {
        this.levelCount = levelCount;
    }

    public Integer getContinuousLeveCount() {
        return continuousLeveCount;
    }

    public void setContinuousLeveCount(Integer continuousLeveCount) {
        this.continuousLeveCount = continuousLeveCount;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }
}
