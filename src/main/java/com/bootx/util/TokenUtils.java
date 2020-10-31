package com.bootx.util;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.crypto.symmetric.SymmetricCrypto;
import com.fasterxml.jackson.core.type.TypeReference;

import java.util.HashMap;
import java.util.Map;

public final class TokenUtils {

    private static final SymmetricCrypto SM4 = new SymmetricCrypto("SM4");


    private TokenUtils(){}

    /**
     * 加密
     * @param map
     * @return
     */
    public static String encrypt(Map<String,Object> map){
        String content = JsonUtils.toJson(map);
        return SM4.encryptHex(content);

    }

    /**
     * 解密
     * @param cotnent
     * @return
     */
    public static Map<String,Object> decrypt(String content){
        try {
            String a = SM4.decryptStr(content, CharsetUtil.CHARSET_GBK);
            return JsonUtils.toObject( a, new TypeReference<Map<String, Object>>() {
            });
        }catch (Exception e){
            e.printStackTrace();
            return new HashMap<>();
        }
    }
}
