package com.yaro.jwtbackend.dto;

public class JwtTokenResponse {
    String access_token;
    Long issued_at;
    Long expires_at;

    public JwtTokenResponse(String access_token, Long issued_at, Long expires_at) {
        this.access_token = access_token;
        this.issued_at = issued_at;
        this.expires_at = expires_at;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public Long getIssued_at() {
        return issued_at;
    }

    public void setIssued_at(Long issued_at) {
        this.issued_at = issued_at;
    }

    public Long getExpires_at() {
        return expires_at;
    }

    public void setExpires_at(Long expires_at) {
        this.expires_at = expires_at;
    }

}
