package com.garvit.url_shortner.api.domain.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Created by dot on 24/11/19 9:55 PM
 * Package: com.garvit.url_shortner.api.domain
 */

@AllArgsConstructor
@Data
public class Url implements Serializable {

    private static final long serialVersionUID = -8586354120051817828L;

    private String url;
    private LocalDateTime expiry;
}
