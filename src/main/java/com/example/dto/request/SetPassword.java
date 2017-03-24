package com.example.dto.request;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by charmanesantiago on 09/03/2016.
 */
public class SetPassword {

    @NotNull
    private String token;

    @NotNull
    @Size(max = 60)
    private String password;

    public SetPassword(){

    }

    public SetPassword(String token, String password){
        this.token = token;
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public String getToken() {
        return token;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
