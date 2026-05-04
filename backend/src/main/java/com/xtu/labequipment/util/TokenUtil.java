package com.xtu.labequipment.util;

import com.xtu.labequipment.common.LoginUser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Component
public class TokenUtil {
    @Value("${lab.token-secret}")
    private String secret;

    @Value("${lab.token-expire-hours}")
    private Long expireHours;

    public String createToken(LoginUser user) {
        long expireTime = System.currentTimeMillis() + expireHours * 60 * 60 * 1000;
        String payload = user.getUserId() + ":" + user.getUsername() + ":" + user.getRoleCode() + ":" + expireTime;
        String sign = sign(payload);
        return Base64.getUrlEncoder().withoutPadding()
                .encodeToString((payload + ":" + sign).getBytes(StandardCharsets.UTF_8));
    }

    public LoginUser parseToken(String token) {
        try {
            String raw = new String(Base64.getUrlDecoder().decode(token), StandardCharsets.UTF_8);
            String[] arr = raw.split(":");
            if (arr.length != 5) {
                return null;
            }
            String payload = arr[0] + ":" + arr[1] + ":" + arr[2] + ":" + arr[3];
            String sign = arr[4];
            if (!sign(payload).equals(sign)) {
                return null;
            }
            long expireTime = Long.parseLong(arr[3]);
            if (System.currentTimeMillis() > expireTime) {
                return null;
            }
            return new LoginUser(Long.parseLong(arr[0]), arr[1], arr[2]);
        } catch (Exception e) {
            return null;
        }
    }

    private String sign(String data) {
        try {
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), "HmacSHA256"));
            byte[] bytes = mac.doFinal(data.getBytes(StandardCharsets.UTF_8));
            return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
        } catch (Exception e) {
            throw new RuntimeException("生成 token 失败", e);
        }
    }
}
