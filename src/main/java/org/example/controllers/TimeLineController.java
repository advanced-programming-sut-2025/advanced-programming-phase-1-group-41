package org.example.controllers;

import org.example.models.App;
import org.example.models.Result;
import org.example.models.TimeLine;

import java.util.regex.Matcher;

public class TimeLineController {
    TimeLine tl = App.getGame().getTime();


    public Result getTime(Matcher matcher){
        return new Result(true, "clock:"+tl.getHour());
    }

    public Result getDate(Matcher matcher){
        StringBuilder message=new StringBuilder();
        message.append("day:").append(convertDay(tl.getDay())).append("\n");
        message.append("season:").append(tl.getSeason()).append("\n");
        message.append("year:").append(tl.getYear()).append("\n");
        return new Result(true,message.toString());
    }

    public Result getDateAndTime(Matcher matcher){
        StringBuilder message=new StringBuilder();
        message.append("hour:").append(tl.getHour()).append("\n");
        message.append("day:").append(convertDay(tl.getDay())).append("\n");
        message.append("season:").append(tl.getSeason()).append("\n");
        message.append("year:").append(tl.getYear()).append("\n");
        return new Result(true,message.toString());
    }

    public Result getDayOfWeek(Matcher matcher){
        return new Result(true,"day:"+convertDay(tl.getDay()));
    }

    private String convertDay(int dayNum){
        dayNum %= 7;
        return switch(dayNum){
            case 0 -> "Saturday";
            case 1 -> "Sunday";
            case 2 -> "Monday";
            case 3 -> "Tuesday";
            case 4 -> "Wednesday";
            case 5 -> "Thursday";
            case 6 -> "Friday";
            default -> throw new IllegalStateException("Unexpected value: " + dayNum);
        };
    }

    public Result getSeason(Matcher matcher){
        return new Result(true,"Season:"+tl.getSeason());
    }

    // TODO handle foraging stages D:

    public Result cheatAdvanceTime(Matcher matcher){
        String hourRaw = matcher.group(1).trim();
        int deltaH = Integer.parseInt(hourRaw);
        if(deltaH < 0){
            return new Result(false,"Invalid hour");
        }
        for (int i = 0; i < deltaH; i++) {
            tl.advanceOneHour();
        }
//        int deltaD = deltaH / 24;
//        advanceDay(deltaD);
//        for(int i=0;i<deltaH % 24;i++){
//            tl.advanceOneHour();
//        }
        return new Result(true,"advanced time");
    }

    public Result cheatAdvanceDate(Matcher matcher){
        String dayRaw = matcher.group(1).trim();
        int deltaD = Integer.parseInt(dayRaw);
        if(deltaD < 0){
            return new Result(false,"Invalid date");
        }
        advanceDay(deltaD);
        return new Result(true,"advanced date");
    }


    private void advanceDay(int deltaD){
        for(int i=0;i<deltaD;i++){
            tl.advanceOneDay();
        }
    }
}
