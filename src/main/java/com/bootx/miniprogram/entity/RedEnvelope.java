package com.bootx.miniprogram.entity;

/**
 * 红包
 * @author blackboy
 * 0：离线红包
 * 1：过关红包
 * 2：翻倍红包
 * 3：签到红包
 * 4：新人红包
 */
public enum RedEnvelope {

    /**
     * 离线红包
     */
    RedEnvelope0(0,"离线红包"),

    /**
     * 过关红包
     */
    RedEnvelope1(1,"过关红包"),

    /**
     * 翻倍红包
     */
    RedEnvelope2(2,"翻倍红包"),

    /**
     * 签到红包
     */
    RedEnvelope3(3,"签到红包"),

    /**
     * 新人红包
     */
    RedEnvelope4(4,"新人红包"),
    /**
     * 幸运红包
     */
    DefaultRedEnvelope(50,"幸运红包");

    /**
     * 类型
     */
    private int type;

    /**
     * 描述
     */
    private String memo;

    private RedEnvelope(int type,String memo){
        this.type = type;
        this.memo = memo;
    }

    public String getMemo() {
        return memo;
    }

    public static RedEnvelope getRedEnvelope(int type){
        switch (type){
            case 0:
                return RedEnvelope0;
            case 1:
                return RedEnvelope1;
            case 2:
                return RedEnvelope2;
            case 3:
                return RedEnvelope3;
            case 4:
                return RedEnvelope4;
            default:
                return DefaultRedEnvelope;

        }
    }
}
