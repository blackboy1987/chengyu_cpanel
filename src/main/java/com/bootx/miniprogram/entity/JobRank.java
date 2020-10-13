package com.bootx.miniprogram.entity;

import com.bootx.common.BaseAttributeConverter;
import com.bootx.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Converter;
import javax.persistence.Entity;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class JobRank extends BaseEntity<Long> {

    @NotNull
    @Column(name = "orders",nullable = false,unique = true)
    private Integer index;

    @NotNull
    @Column(nullable = false,unique = true)
    private Integer level;

    @NotNull
    @Column(nullable = false,unique = true)
    private String name;

    @NotEmpty
    @Column(name = "icons",nullable = false, length = 4000)
    @Convert(converter = ResourceConverter.class)
    private Map<String,String> resource = new HashMap<>();

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, String> getResource() {
        return resource;
    }

    public void setResource(Map<String, String> resource) {
        this.resource = resource;
    }

    /**
     * 类型转换 - 可选项
     *
     * @author 好源++ Team
     * @version 6.1
     */
    @Converter
    public static class ResourceConverter extends BaseAttributeConverter<Map<String,String>> {
    }
}
