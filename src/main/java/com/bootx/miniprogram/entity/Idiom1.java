package com.bootx.miniprogram.entity;

import com.bootx.common.BaseAttributeConverter;
import com.bootx.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonView;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Converter;
import javax.persistence.Entity;
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
