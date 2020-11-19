package com.bootx.miniprogram.entity;

import com.bootx.common.BaseAttributeConverter;
import com.bootx.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonView;
import com.github.binarywang.java.emoji.EmojiConverter;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Member extends BaseEntity<Long> {

    private static final EmojiConverter emojiConverter = EmojiConverter.getInstance();

    @NotNull
    @Column(nullable = false,updatable = false,unique = true)
    private String openId;

    @ManyToOne
    @NotNull
    @JoinColumn(updatable = false,nullable = false)
    private App app;

    @ManyToOne
    @NotNull
    @JoinColumn(nullable = false)
    private MemberRank memberRank;

    @JsonView({ListView.class})
    private String nickName;

    private String sessionKey;

    private String token;

    private String unionid;

    @JsonView({ListView.class})
    private Integer gender;

    @JsonView({ListView.class})
    private String city;

    @JsonView({ListView.class})
    private String province;

    @JsonView({ListView.class})
    private String country;

    @JsonView({ListView.class})
    private String avatarUrl;

    private String wechat;

    private String name;

    @JsonView({ListView.class})
    private String mobile;

    @Column(nullable = false, precision = 27, scale = 12)
    @JsonView({ListView.class})
    private BigDecimal money;

    @NotNull
    @Column(nullable = false)
    private Integer gameLevel;

    @NotNull
    @Min(0)
    @Column(nullable = false)
    @JsonView({ListView.class})
    private Integer level;

    @NotNull
    @Min(0)
    @Column(nullable = false)
    private Integer cartIndex;

    @NotNull
    @Min(0)
    @Column(nullable = false)
    private Integer houseIndex;

    @NotNull
    @Min(0)
    @Column(nullable = false)
    private Integer jobIndex;

    @Column(nullable = false)
    @JsonView({ListView.class})
    private Long point;

    @Column(updatable = false)
    private Long parentId;

    @Lob
    @Convert(converter = ParentIdsConverter.class)
    private List<Long> parentIds = new ArrayList<>();

    /**
     * 消费金额
     */
    @Column(nullable = false, precision = 27, scale = 12)
    private BigDecimal amount;

    private Boolean isAuth;

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public App getApp() {
        return app;
    }

    public void setApp(App app) {
        this.app = app;
    }

    public MemberRank getMemberRank() {
        return memberRank;
    }

    public void setMemberRank(MemberRank memberRank) {
        this.memberRank = memberRank;
    }

    public String getNickName() {
        if(StringUtils.isNotBlank(nickName)){
            nickName = emojiConverter.toUnicode(nickName);
        }
        return nickName;
    }

    public void setNickName(String nickName) {
        if(StringUtils.isNotBlank(nickName)){
            nickName = emojiConverter.toHtml(nickName);
        }
        this.nickName = nickName;
    }

    public String getSessionKey() {
        return sessionKey;
    }

    public void setSessionKey(String sessionKey) {
        this.sessionKey = sessionKey;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUnionid() {
        return unionid;
    }

    public void setUnionid(String unionid) {
        this.unionid = unionid;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public Integer getGameLevel() {
        return gameLevel;
    }

    public void setGameLevel(Integer gameLevel) {
        this.gameLevel = gameLevel;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getCartIndex() {
        return cartIndex;
    }

    public void setCartIndex(Integer cartIndex) {
        this.cartIndex = cartIndex;
    }

    public Integer getHouseIndex() {
        return houseIndex;
    }

    public void setHouseIndex(Integer houseIndex) {
        this.houseIndex = houseIndex;
    }

    public Integer getJobIndex() {
        return jobIndex;
    }

    public void setJobIndex(Integer jobIndex) {
        this.jobIndex = jobIndex;
    }

    public Long getPoint() {
        return point;
    }

    public void setPoint(Long point) {
        this.point = point;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getWechat() {
        return wechat;
    }

    public void setWechat(String wechat) {
        this.wechat = wechat;
    }

    public Boolean getIsAuth() {
        return isAuth;
    }

    public void setIsAuth(Boolean isAuth) {
        this.isAuth = isAuth;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public List<Long> getParentIds() {
        return parentIds;
    }

    public void setParentIds(List<Long> parentIds) {
        this.parentIds = parentIds;
    }

    @Converter
    public static class ParentIdsConverter extends BaseAttributeConverter<List<Long>> {
    }
}
