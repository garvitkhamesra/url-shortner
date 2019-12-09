package com.garvit.url_shortner.api.controller;

import com.garvit.url_shortner.api.domain.request.UrlShortenRequest;
import com.garvit.url_shortner.api.utility.UrlAnalyser;
import com.garvit.url_shortner.api.service.UrlConverterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by dot on 24/11/19 1:31 PM
 * Package: com.garvit.url_shortner.api.app.controller
 */

@RestController
@CrossOrigin
@Slf4j
public class UrlShortnerController {

    @Autowired
    private UrlConverterService urlConverterService;

    @PostMapping(value = "/shortener")
    public ResponseEntity shortenUrl(@RequestBody UrlShortenRequest shortenRequest, HttpServletRequest request) {
        log.info("Received url to shorten:  {}", shortenRequest.getUrl());
        String longUrl = shortenRequest.getUrl();

        if (UrlAnalyser.INSTANCE.validateURL(longUrl)) {
            String localURL = request.getRequestURL().toString();
            String shortenedUrl = urlConverterService.shortenURL(localURL, shortenRequest.getUrl());
            log.info("Shortened url to: " + shortenedUrl);
            return ResponseEntity.ok().body(shortenedUrl);
        } else {
            return ResponseEntity.badRequest().body("Invalid Url");
        }
    }

    @GetMapping(value = "/{shortUrl}")
    public RedirectView redirectUrl(@PathVariable String shortUrl) {
        log.info("Received shortened url to redirect: {} ", shortUrl);
        String redirectUrlString = urlConverterService.getLongURLFromID(shortUrl);
        log.info("Original URL: " + redirectUrlString);
        RedirectView redirectView = new RedirectView();
        redirectView.setUrl(redirectUrlString);
        return redirectView;
    }
}
