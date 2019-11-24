package com.garvit.url_shortner.api.service;

import com.garvit.url_shortner.api.utility.UrlShortener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by dot on 24/11/19 21:31 PM
 * Package: com.garvit.url_shortner.api.service
 */

@Slf4j
@Service
public class UrlConverterService {
    private final UrlShortener urlShortener;

    @Autowired
    public UrlConverterService(UrlShortener urlShortener) {
        this.urlShortener = urlShortener;
    }

    public String shortenURL(String localURL, String longUrl) {
        log.info("Shortening {}", longUrl);
        String uniqueID = urlShortener.encode(longUrl);
        String baseString = formatLocalURLFromShortener(localURL);
        String shortenedURL = baseString + uniqueID;
        return shortenedURL;
    }

    public String getLongURLFromID(String shortUrl) {
        String longUrl = urlShortener.decode(shortUrl);
        log.info("Converting shortened URL back to {}", longUrl);
        return longUrl;
    }

    private String formatLocalURLFromShortener(String localURL) {
        String[] addressComponents = localURL.split("/");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < addressComponents.length - 1; ++i) {
            sb.append(addressComponents[i]);
            if (addressComponents[i].equals("http:") || addressComponents[i].equals("https:")) {
                sb.append("//");
            }
        }
        sb.append('/');
        return sb.toString();
    }
}
