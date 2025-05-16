package org.example.models;

public record Result(boolean success, String message) {

    @Override
    public String toString() {

        if(success){
            return Colors.foreColor(40) + message + Colors.RESET;
        }
        return Colors.foreColor(196) + message + Colors.RESET;
    }
}
