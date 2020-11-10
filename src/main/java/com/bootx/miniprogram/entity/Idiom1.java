package com.bootx.miniprogram.entity;

import com.bootx.common.BaseAttributeConverter;
import com.bootx.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonView;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Idiom1 extends BaseEntity<Long> {

    @NotNull
    @Column(nullable = false,unique = true)
    @JsonView({ViewView.class})
    private Integer level;

    @NotNull
    @Column(nullable = false)
    @Convert(converter = AnswerConverter.class)
    @JsonView({ViewView.class})
    private List<String> text  = new ArrayList<>();

    @NotNull
    @Column(nullable = false)
    @Convert(converter = AnswerConverter.class)
    @JsonView({ViewView.class})
    private List<String> pinYin  = new ArrayList<>();

    @NotNull
    @Min(0)
    @Column(nullable = false)
    private Integer position;

    @NotNull
    @Column(nullable = false,unique = true)
    private String fullText;

    private String jinYiCi;

    private String fanYiCi;

    private String yongFa;
    private String jieSi;
    private String chuChu;
    private String liZi;
    private String xieHouYu;
    private String miYu;

    @Lob
    private String chengYuGuShi;

    private String jianPin;

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public List<String> getText() {
        return text;
    }

    public void setText(List<String> text) {
        this.text = text;
    }

    public List<String> getPinYin() {
        return pinYin;
    }

    public void setPinYin(List<String> pinYin) {
        this.pinYin = pinYin;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public String getFullText() {
        return fullText;
    }

    public void setFullText(String fullText) {
        this.fullText = fullText;
    }

    public String getJinYiCi() {
        return jinYiCi;
    }

    public void setJinYiCi(String jinYiCi) {
        this.jinYiCi = jinYiCi;
    }

    public String getFanYiCi() {
        return fanYiCi;
    }

    public void setFanYiCi(String fanYiCi) {
        this.fanYiCi = fanYiCi;
    }

    public String getYongFa() {
        return yongFa;
    }

    public void setYongFa(String yongFa) {
        this.yongFa = yongFa;
    }

    public String getJieSi() {
        return jieSi;
    }

    public void setJieSi(String jieSi) {
        this.jieSi = jieSi;
    }

    public String getChuChu() {
        return chuChu;
    }

    public void setChuChu(String chuChu) {
        this.chuChu = chuChu;
    }

    public String getLiZi() {
        return liZi;
    }

    public void setLiZi(String liZi) {
        this.liZi = liZi;
    }

    public String getXieHouYu() {
        return xieHouYu;
    }

    public void setXieHouYu(String xieHouYu) {
        this.xieHouYu = xieHouYu;
    }

    public String getMiYu() {
        return miYu;
    }

    public void setMiYu(String miYu) {
        this.miYu = miYu;
    }

    public String getChengYuGuShi() {
        return chengYuGuShi;
    }

    public void setChengYuGuShi(String chengYuGuShi) {
        this.chengYuGuShi = chengYuGuShi;
    }

    public String getJianPin() {
        return jianPin;
    }

    public void setJianPin(String jianPin) {
        this.jianPin = jianPin;
    }

    /**
     * 类型转换 - 可选项
     *
     * @author 好源++ Team
     * @version 6.1
     */
    @Converter
    public static class AnswerConverter extends BaseAttributeConverter<List<String>> {
    }
}
