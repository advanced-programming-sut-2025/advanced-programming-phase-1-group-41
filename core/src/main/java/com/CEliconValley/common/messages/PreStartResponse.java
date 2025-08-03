package com.CEliconValley.common.messages;

import com.CEliconValley.models.locations.FarmType;

public class PreStartResponse {
    public String username;
    public FarmType farmType;

    public PreStartResponse(String username, FarmType farmType) {
        this.username = username;
        this.farmType = farmType;
    }
}
