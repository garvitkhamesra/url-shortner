package com.garvit.url_shortner.api.utility;

import com.garvit.url_shortner.api.domain.response.Url;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by dot on 24/11/19 1:52 PM
 * Package: com.garvit.url_shortner.api.utility
 */

@Data
@Component
public class UrlShortener {

    static final Map<String, Url> shortToLongMap = new HashMap<>();
    static final Map<String, Url> longToShortMap = new HashMap<>();
    static int counter = 1000;

    public String encode(String longUrl) {
        if (longToShortMap.containsKey(longUrl)) {
            if (longToShortMap.get(longUrl).getExpiry().isBefore(LocalDateTime.now())) {
                String shortUrl = longToShortMap.get(longUrl).getUrl();
                longToShortMap.remove(longUrl);
                shortToLongMap.remove(shortUrl);
            } else {
                return longToShortMap.get(longUrl).getUrl();
            }
        }

        String shortUrl = convertDecimalToBase62(counter++);
        shortToLongMap.put(shortUrl, new Url(longUrl, LocalDateTime.now().plusDays(5)));
        longToShortMap.put(longUrl, new Url(shortUrl, LocalDateTime.now().plusDays(5    )));
        return shortUrl;
    }

    public String decode(String shortUrl) {
        if (!LocalDateTime.now().minusDays(5).isAfter(shortToLongMap.get(shortUrl).getExpiry())) {
            return shortToLongMap.get(shortUrl).getUrl();
        }
        return "Url Expired";
    }

    private String convertDecimalToBase62(int n) {
        final char[] BASE62 = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".toCharArray();
        StringBuilder sb = new StringBuilder();

        while (n > 0) {
            sb.append(BASE62[n % 62]);
            n /= 62;
        }
        return sb.reverse().toString();
    }
}
