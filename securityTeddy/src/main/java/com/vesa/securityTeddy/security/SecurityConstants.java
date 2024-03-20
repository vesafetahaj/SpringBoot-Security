package com.vesa.securityTeddy.security;


/*This is a constants class where you would define static final variables used across your security setup.
This might include the secret key used to sign JWT tokens, the token expiration time, the token prefix (e.g., "Bearer "),
and the header string where the token is expected to be found in HTTP requests (e.g., "Authorization").*/
public class SecurityConstants{
    public static final long JWT_EXPIRATION = 70000;
}
