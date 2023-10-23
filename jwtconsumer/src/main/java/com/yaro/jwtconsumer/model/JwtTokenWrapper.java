package com.yaro.jwtconsumer.model;

public class JwtTokenWrapper {
    private Long Status;
    private String Message;
    private JwtToken jwtToken;

    private String jwtDecoded;

    public JwtTokenWrapper() {
    }

    public JwtTokenWrapper(Long status, String message, JwtToken jwtToken, String jwtDecoded) {
        Status = status;
        Message = message;
        this.jwtToken = jwtToken;
        this.jwtDecoded = jwtDecoded;
    }

    public Long getStatus() {
        return Status;
    }

    public void setStatus(Long status) {
        Status = status;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public JwtToken getJwtToken() {
        return jwtToken;
    }

    public void setJwtToken(JwtToken jwtToken) {
        this.jwtToken = jwtToken;
    }

    public String getJwtDecoded() {
        return jwtDecoded;
    }

    public void setJwtDecoded(String jwtDecoded) {
        this.jwtDecoded = jwtDecoded;
    }
}
