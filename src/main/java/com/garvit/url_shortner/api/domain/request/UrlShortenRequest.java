package com.garvit.url_shortner.api.domain.request;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by dot on 24/11/19 1:35 PM
 * Package: com.garvit.url_shortner.api.app.domain.request.response
 */

@Data
public class UrlShortenRequest implements Serializable {

    private static final long serialVersionUID = 4916780375228059114L;

    private String url;

}
