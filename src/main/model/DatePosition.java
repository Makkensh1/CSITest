package main.model;

public enum DatePosition {

    BEFORE("BEFORE"),
    AT_THE_BEGIN("BEGIN"),
    IN_THE_MIDDLE("MIDDLE"),
    IN_THE_END("END"),
    AFTER("AFTER"),
    OVER("OVER");

    private String code;

    public String getCode() {
        return code;
    }

    DatePosition(String code) {
        this.code = code;
    }
}
