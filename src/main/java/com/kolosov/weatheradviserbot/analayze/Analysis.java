package com.kolosov.weatheradviserbot.analayze;

public record Analysis(Result result, String description) {

    public enum Result {
        BAD,
        UNDEFINED,
        GOOD,
        PERFECT
    }
}
