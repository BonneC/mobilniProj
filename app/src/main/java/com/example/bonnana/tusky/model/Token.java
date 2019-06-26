package com.example.bonnana.tusky.model;

import com.google.gson.annotations.SerializedName;

public class Token {
    @SerializedName("access_token")
    private String token;
    @SerializedName("token_type")
    private String tokenType;

    public Token(String token, String tokenType) {
        this.token = token;
        this.tokenType = tokenType;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }
}
