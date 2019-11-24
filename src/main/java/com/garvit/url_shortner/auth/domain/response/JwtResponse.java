package com.garvit.url_shortner.auth.domain.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * User: garvit
 * Date: 24/11/19 3:00 PM
 */

@Data
@AllArgsConstructor
public class JwtResponse implements Serializable {

    private static final long serialVersionUID = 3024942567852039488L;

    private String token;

}
