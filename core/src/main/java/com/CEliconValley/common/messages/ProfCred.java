package com.CEliconValley.common.messages;

import com.CEliconValley.common.UserData;

public class ProfCred {
    public String key;
    public String value;
    public UserData userData;
    public ProfCred(String key, String value, UserData userData) {
        this.key = key;
        this.value = value;
        this.userData = userData;
    }
}
