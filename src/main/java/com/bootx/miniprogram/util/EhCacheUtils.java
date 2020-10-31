package com.bootx.miniprogram.util;

import com.bootx.miniprogram.entity.SiteInfo;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

import java.util.HashMap;
import java.util.Map;

public class EhCacheUtils {
    private static final CacheManager cacheManager = CacheManager.create();
    private static final Cache siteInfoCache = cacheManager.getCache("siteInfo");

    public static SiteInfo getCacheSiteInfo(Long siteInfoId){
        if(siteInfoCache!=null){
            Element element = siteInfoCache.get("siteInfo_"+siteInfoId);
            if(element==null){
                return null;
            }else{
                Object result = element.getObjectValue();
                if(result==null){
                    return null;
                }
                return (SiteInfo) result;
            }
        }else{
            return null;
        }
    }

    public static void setCacheSiteInfo(SiteInfo siteInfo){
        if(siteInfoCache!=null&&siteInfo!=null){
            Map<String,Object> map = new HashMap<>();
            map.putAll(siteInfo.getExtras());
            map.put("",);
            map.put("",);
            map.put("",);
            map.put("",);


            siteInfoCache.put(new Element("siteInfo_"+siteInfo.getId(),siteInfo));
        }
    }

    public static void removeCacheVideo(Long siteInfoId){
        if(siteInfoCache!=null){
            siteInfoCache.remove("siteInfo_"+siteInfoId);
        }
    }
}
