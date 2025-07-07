package com.CEliconValley.models;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public interface Command {
    Matcher getMatcher(String input);

    static Matcher getMatcher(String input, String pattern){
        Matcher matcher = Pattern.  compile(pattern).matcher(input);
        if(matcher.matches()){
            return matcher;
        }
        return null;
    }
}
