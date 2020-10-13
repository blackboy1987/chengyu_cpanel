package com.bootx.miniprogram.entity;

import com.bootx.entity.BaseEntity;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

@Entity
public class App extends BaseEntity<Long> {

    @NotNull
    @Column(nullable = false,updatable = false,unique = true)
    private String appId;

    @NotNull
    @Column(nullable = false,updatable = false,unique = true)
    private String appSecret;

    @NotNull
    @Length(min = 18,max = 18)
    @Column(nullable = false,updatable = false,unique = true)
    private String code;

    @NotNull
    @Column(nullable = false,updatable = false,unique = true)
    private String secret;

    @OneToOne
    private SiteInfo siteInfo;


    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public SiteInfo getSiteInfo() {
        return siteInfo;
    }

    public void setSiteInfo(SiteInfo siteInfo) {
        this.siteInfo = siteInfo;
    }
}
