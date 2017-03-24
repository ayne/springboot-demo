package com.example.dto.request;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by charmanesantiago on 09/03/2016.
 */
public class ChangePassword {

    @NotNull
    @Size(max = 60)
    private String oldPassword;
    @NotNull
    @Size(max = 60)
    private String newPassword;

    public ChangePassword(){}

    public ChangePassword(String oldPassword, String newPassword){
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }
}

