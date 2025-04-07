package models;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public interface Command {
    public Matcher getMatcher(String input);

    public static Matcher getMatcher(String input, String pattern){
        Matcher matcher = Pattern.compile(pattern).matcher(input);
        if(matcher.matches()){
            return matcher;
        }
        return null;
    }
}
