package com.bootx.miniprogram.entity;

import com.bootx.entity.OrderedEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class MoreGame extends OrderedEntity<Long> {

    @NotNull
    @JoinColumn(nullable = false,updatable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private App app;


    @NotNull
    @Column(nullable = false,updatable = false)
    private String appId1;

    @NotNull
    @Column(nullable = false,unique = true)
    private String name;

    @NotNull
    @Column(nullable = false,unique = true)
    private String memo;

    @NotNull
    @Column(nullable = false,unique = true)
    private String logo;

    @NotNull
    @Column(nullable = false)
    private String path;

    @NotNull
    @Column(nullable = false)
    private Boolean isEnabled;

    public App getApp() {
        return app;
    }

    public void setApp(App app) {
        this.app = app;
    }

    public String getAppId1() {
        return appId1;
    }

    public void setAppId1(String appId1) {
        this.appId1 = appId1;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Boolean getIsEnabled() {
        return isEnabled;
    }

    public void setIsEnabled(Boolean isEnabled) {
        this.isEnabled = isEnabled;
    }
}
