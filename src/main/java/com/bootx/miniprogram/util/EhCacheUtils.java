package com.bootx.miniprogram.util;

import com.bootx.miniprogram.entity.Idiom1;
import com.bootx.miniprogram.entity.SiteInfo;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

public class EhCacheUtils {
    private static final CacheManager cacheManager = CacheManager.create();
    private static final Cache siteInfoCache = cacheManager.getCache("siteInfo");
    private static final Cache idiomListCache = cacheManager.getCache("idiomList");


    public static Object getCacheSiteInfo(Long siteInfoId){
        if(siteInfoCache!=null){
            Element element = siteInfoCache.get("siteInfo_"+siteInfoId);
            if(element==null){
                return null;
            }else{
                Object result = element.getObjectValue();
                if(result==null){
                    return null;
                }
                return result;
            }
        }else{
            return null;
        }
    }

    public static void setCacheSiteInfo(SiteInfo siteInfo){
        if(siteInfoCache!=null&&siteInfo!=null){
            Map<String,Object> result = new HashMap<>();
            result.putAll(siteInfo.getExtras());
            result.put("name",siteInfo.getName());
            result.put("logo",siteInfo.getLogo());
            result.put("bannerAdId",siteInfo.getBannerAdId());
            result.put("rewardedVideoAdId",siteInfo.getRewardedVideoAdId());
            result.put("interstitialAdId",siteInfo.getInterstitialAdId());
            result.put("videoAdId",siteInfo.getVideoAdId());
            result.put("videoFrontAdId",siteInfo.getVideoFrontAdId());
            result.put("gridAdId",siteInfo.getGridAdId());
            result.put("nativeAdId",siteInfo.getNativeAdId());
            result.put("isOpen",siteInfo.getIsOpen());
            siteInfoCache.put(new Element("siteInfo_"+siteInfo.getId(),result));
        }
    }

    public static void removeCacheVideo(Long siteInfoId){
        if(siteInfoCache!=null){
            siteInfoCache.remove("siteInfo_"+siteInfoId);
        }
    }




    public static Object getIdiomListInfo(Long level){
        if(idiomListCache!=null){
            Element element = idiomListCache.get("idiomList_"+level);
            if(element==null){
                return null;
            }else{
                Object result = element.getObjectValue();
                if(result==null){
                    return null;
                }
                return result;
            }
        }else{
            return null;
        }
    }

    public static void setCacheIdiomList(Idiom1 idiom){
        if(idiomListCache!=null&&idiom!=null){
            Map<String,Object> result = new HashMap<>();
            result.put("id",idiom.getId());
            result.put("idiom", StringUtils.join(idiom.getText(),""));
            result.put("level",idiom.getLevel());
            result.put("position",idiom.getPosition());
            idiomListCache.put(new Element("idiomList_"+idiom.getLevel(),result));
        }
    }

    public static void removeCacheIdiomList(Integer level){
        if(idiomListCache!=null){
            idiomListCache.remove("idiomList_"+level);
        }
    }


}
