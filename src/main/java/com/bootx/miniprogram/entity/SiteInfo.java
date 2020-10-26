package com.bootx.miniprogram.entity;

import com.bootx.common.BaseAttributeConverter;
import com.bootx.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Entity
public class SiteInfo extends BaseEntity<Long> {

    @OneToOne
    @NotNull
    @JoinColumn(updatable = false,nullable = false)
    private App app;

    /**
     * 一次可以玩多少局
     */
    @JsonView({ViewView.class})
    private Integer ticketMax;

    /**
     * ticketMax 减为0，之后需要休息的时间。单位分钟
     */
    @JsonView({ViewView.class})
    private Integer ticketTime;

    /**
     * 是否可以分享
     */
    @JsonView({ViewView.class})
    private Boolean isShare;

    /**
     * 分享的图片地址
     */
    @JsonView({ViewView.class})
    private String shareImg;

    /**
     * 分享的标题
     */
    private String shareTitle;

    /**
     * 每局游戏进行时间
     */
    @JsonView({ViewView.class})
    private Integer gameTime;

    /**
     * 点击错误点，扣除的积分数
     */
    @JsonView({ViewView.class})
    private Integer downTime;

    /**
     *
     */
    @JsonView({ViewView.class})
    private String serviceImg;

    @JsonView({ViewView.class})
    private String serviceTitle;

    @JsonView({ViewView.class})
    private Integer useGold;

    /**
     * 是否显示广告
     */
    @JsonView({ViewView.class})
    private Boolean adshow;

    @JsonView({ViewView.class})
    @Column(nullable = false, length = 4000)
    @Convert(converter = RewardConverter.class)
    private Map<String,Object> reward  = new HashMap<>();

    @JsonView({ViewView.class})
    @Column(nullable = false, length = 4000)
    @Convert(converter = RewardConverter.class)
    private Map<String,Object> extras  = new HashMap<>();


    public App getApp() {
        return app;
    }

    public void setApp(App app) {
        this.app = app;
    }

    public Integer getTicketMax() {
        return ticketMax;
    }

    public void setTicketMax(Integer ticketMax) {
        this.ticketMax = ticketMax;
    }

    public Integer getTicketTime() {
        return ticketTime;
    }

    public void setTicketTime(Integer ticketTime) {
        this.ticketTime = ticketTime;
    }

    public Boolean getShare() {
        return isShare;
    }

    public void setShare(Boolean share) {
        isShare = share;
    }

    public String getShareImg() {
        return shareImg;
    }

    public void setShareImg(String shareImg) {
        this.shareImg = shareImg;
    }

    public String getShareTitle() {
        return shareTitle;
    }

    public void setShareTitle(String shareTitle) {
        this.shareTitle = shareTitle;
    }

    public Integer getGameTime() {
        return gameTime;
    }

    public void setGameTime(Integer gameTime) {
        this.gameTime = gameTime;
    }

    public Integer getDownTime() {
        return downTime;
    }

    public void setDownTime(Integer downTime) {
        this.downTime = downTime;
    }

    public String getServiceImg() {
        return serviceImg;
    }

    public void setServiceImg(String serviceImg) {
        this.serviceImg = serviceImg;
    }

    public String getServiceTitle() {
        return serviceTitle;
    }

    public void setServiceTitle(String serviceTitle) {
        this.serviceTitle = serviceTitle;
    }

    public Integer getUseGold() {
        return useGold;
    }

    public void setUseGold(Integer useGold) {
        this.useGold = useGold;
    }

    public Boolean getAdshow() {
        return adshow;
    }

    public void setAdshow(Boolean adshow) {
        this.adshow = adshow;
    }

    public Map<String, Object> getReward() {
        return reward;
    }

    public void setReward(Map<String, Object> reward) {
        this.reward = reward;
    }

    /**
     * deductionPoint:每次点击扣除积分数
     * browseVideoRewardPoint:看视频奖励积分数
     * everyLevelReward:连续通过多少关有奖励
     * everyLevelRewardMoney:连续通过多少关的奖励金额（这里是最高金额，具体金额根据随机算法算出来的）
     * firstLoginRewardMoney:注册奖励金额
     * firstLoginRewardPoint:注册奖励积分数额
     * @return
     */
    public Map<String, Object> getExtras() {
        return extras;
    }

    public void setExtras(Map<String, Object> extras) {
        this.extras = extras;
    }

    /**
     * 类型转换 - 可选项
     *
     * @author IGOMALL  Team
     * @version 1.0
     */
    @Converter
    public static class RewardConverter extends BaseAttributeConverter<Map<String,Object>> {
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Reward implements Serializable {

        private Boolean isOpen;

        private String unit;

        @Column(nullable = false, precision = 27, scale = 12)
        private BigDecimal maxUnit;

        private String img;

        private String msg;

        public Boolean getIsOpen() {
            return isOpen;
        }

        public void setIsOpen(Boolean isOpen) {
            this.isOpen = isOpen;
        }

        public String getUnit() {
            return unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }

        public BigDecimal getMaxUnit() {
            return maxUnit;
        }

        public void setMaxUnit(BigDecimal maxUnit) {
            this.maxUnit = maxUnit;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }
    }
}
