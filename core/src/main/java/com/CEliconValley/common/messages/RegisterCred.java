package com.CEliconValley.common.messages;

import com.CEliconValley.models.Gender;

public class RegisterCred {
    public String username;
    public String password;
    public String email;
    public String nickname;
    public Gender gender;
    public String question;
    public String answer;

    public RegisterCred(String username, String password, String email,
                        String nickname, Gender gender, String question, String answer) {
        this.answer = answer;
        this.email = email;
        this.gender = gender;
        this.nickname = nickname;
        this.password = password;
        this.question = question;
        this.username = username;
    }
}
