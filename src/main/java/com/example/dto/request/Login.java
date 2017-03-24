package com.example.dto.request;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by charmanesantiago on 28/02/2016.
 */
@Data
public class Login {

    @NotNull
    @Size(max = 100)
    private String email;

    @NotNull
    @Size(max = 20)
    private String password;

    public Login(){}

    public Login(String email, String password){
        this.email = email;
        this.password = password;
    }

}