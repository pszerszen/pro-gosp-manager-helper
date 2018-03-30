package com.manager.store.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class PasswordChangeToken {

    private static final String PASSWORD_VALIDATION_MESSAGE = "A password should has at least one lowercase character, one uppercase and one digit.";

    private static final String PASSWORD_PATTERN = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).*$";

    @NotNull
    private String oldPassword;

    @NotNull
    @Size(min = 8, max = 20)
    @Pattern(regexp = PASSWORD_PATTERN, message = PASSWORD_VALIDATION_MESSAGE)
    private String password;

    @NotNull
    @Size(min = 8, max = 20)
    private String passwordConfirmation;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordConfirmation() {
        return passwordConfirmation;
    }

    public void setPasswordConfirmation(String passwordConfirmation) {
        this.passwordConfirmation = passwordConfirmation;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(final String oldPassword) {
        this.oldPassword = oldPassword;
    }
}
