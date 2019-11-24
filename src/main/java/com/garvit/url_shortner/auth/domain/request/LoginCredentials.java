package com.garvit.url_shortner.auth.domain.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * User: garvit
 * Date: 24/11/19 2:59 PM
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginCredentials implements Serializable {

    private static final long serialVersionUID = 6740170569406263863L;

    private String username;
    private String password;
}
