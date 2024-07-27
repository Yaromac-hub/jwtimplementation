package com.yaro.jwtconsumer.model;

import java.util.Date;

public class JwtToken {
    private String subject;
    private String name;
    private String role;
    private Date issuedAt;
    private Date expire;

    public JwtToken() {
    }

    public JwtToken(String subject, String name, String role, Date issuedAt, Date expire) {
        this.subject = subject;
        this.name = name;
        this.role = role;
        this.issuedAt = issuedAt;
        this.expire = expire;
    }

    public String getSubject() {
        return subject;
    }

    public String getName() {
        return name;
    }

    public String getRole() {
        return role;
    }

    public Date getIssuedAt() {
        return issuedAt;
    }

    public Date getExpire() {
        return expire;
    }
}
