package com.CEliconValley.models;

import com.CEliconValley.models.ui.TerminalColors;

public record Result(boolean success, String message) {

    @Override
    public String toString() {

        if(success){
            return TerminalColors.foreColor(85) + message + TerminalColors.RESET;
//            return Colors.foreColor(40) + message + Colors.RESET;
        }
        return TerminalColors.foreColor(196) + message + TerminalColors.RESET;
    }
}
