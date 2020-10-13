package com.bootx.miniprogram.entity;

import com.bootx.common.Constants;
import com.bootx.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonView;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
public class MemberRank extends BaseEntity<Long> {

    @NotNull
    @Column(nullable = false,unique = true)
    @JsonView({BaseEntity.ViewView.class})
    private String name;

    @NotNull
    @Column(nullable = false)
    @JsonView({BaseEntity.ViewView.class})
    private String title;

    @NotNull
    @Column(nullable = false)
    @JsonView({BaseEntity.ViewView.class})
    private Integer gold;

    @NotNull
    @Column(nullable = false)
    private Boolean isDefault;

    @NotNull
    @Min(0)
    @Column(nullable = false,unique = true)
    private Integer level;

    @OneToOne
    private MemberRank next;

    @OneToOne
    private MemberRank prv;












    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getGold() {
        return gold;
    }

    public void setGold(Integer gold) {
        this.gold = gold;
    }

    public Boolean getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(Boolean isDefault) {
        this.isDefault = isDefault;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public MemberRank getNext() {
        return next;
    }

    public void setNext(MemberRank next) {
        this.next = next;
    }

    public MemberRank getPrv() {
        return prv;
    }

    public void setPrv(MemberRank prv) {
        this.prv = prv;
    }

    @Transient
    @JsonView({BaseEntity.ViewView.class})
    public String getImg(){
        return Constants.RESOURCEURL+ "images/rank/dg"+getId()+".png";
    }
    @Transient
    @JsonView({BaseEntity.ViewView.class})
    public String getRankImg(){
        return Constants.RESOURCEURL+ "images/rank/rank"+getId()+".png";
    }
    @Transient
    @JsonView({BaseEntity.ViewView.class})
    public String getRankNameImg(){
        return Constants.RESOURCEURL+ "images/rank/rank"+getId()+"_1.png";
    }
}
