package com.CEliconValley.common.messages;

public class ForgotpassCred {
    public String username;
    public String answer;
    public String newPassword;

    public ForgotpassCred(String username, String answer, String newPassword) {
        this.answer = answer;
        this.newPassword = newPassword;
        this.username = username;
    }

}
