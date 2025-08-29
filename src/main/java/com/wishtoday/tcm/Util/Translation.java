package com.wishtoday.tcm.Util;

import com.google.gson.Gson;
import com.wishtoday.tcm.Config.KeysConfig;
import com.wishtoday.tcm.TranslationCommandMod;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.time.Instant;
import java.util.UUID;

public class Translation {
    private Translation() {
    }
    private static final String API_URL = "https://openapi.youdao.com/api";
    private static final String APP_ID = KeysConfig.map.get("APP_KEY");
    private static final String APP_KEY = KeysConfig.map.get("SECRET_KEY");
    public static String translate(String text) {
        return translate(text, "zh-CN");
    }

    public static String translate(String text, String to) {
        return translate(text, "auto", to);
    }

    public static String translate(String text, String from, String to) {
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            UUID uuid = UUID.randomUUID();
            String curtime = String.valueOf(Instant.now().getEpochSecond());
            String signInput = APP_ID + text + uuid + curtime + APP_KEY;
            String sha256 = sha256(signInput);
            URI uri = new URIBuilder(API_URL)
                    .setParameter("q", text)
                    .setParameter("from", from)
                    .setParameter("to", to)
                    .setParameter("appKey", APP_ID)
                    .setParameter("salt", uuid.toString())
                    .setParameter("sign", sha256)
                    .setParameter("signType", "v3")
                    .setParameter("curtime", curtime)
                    .build();
            HttpGet request = new HttpGet(uri);
            try (CloseableHttpResponse response = client.execute(request)) {
                String string = EntityUtils.toString(response.getEntity());
                //System.out.println(string);
                TranslationResult result = new Gson().fromJson(string, TranslationResult.class);
                if ("0".equals(result.errorCode)) {
                    return result.translation[0];
                }
            }
        } catch (Exception e) {
            TranslationCommandMod.LOGGER.error(e.toString());
            return text;
        }
        return text;
    }
    public static class TranslationResult {
        private String errorCode;
        private String query;
        private String[] translation;
    }


    // SHA256加密方法
    private static String sha256(String input) throws Exception {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(input.getBytes(StandardCharsets.UTF_8));

        // 转换为十六进制字符串
        StringBuilder hexString = new StringBuilder();
        for (byte b : hash) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }
}

