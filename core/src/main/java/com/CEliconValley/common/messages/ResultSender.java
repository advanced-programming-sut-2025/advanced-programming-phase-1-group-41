package com.CEliconValley.common.messages;

import com.CEliconValley.models.Result;

public class ResultSender {
    public boolean code;
    public String message;
    public ResultSender(Result result){
        code = result.success();
        message = result.message();
    }
}
