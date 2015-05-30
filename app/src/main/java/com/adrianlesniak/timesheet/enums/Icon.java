package com.adrianlesniak.timesheet.enums;

/**
 * Created by Adrian on 29-May-15.
 */
public enum Icon {

    START(0), STOP(1);

    private int id;

    Icon(int id) {
        this.id = id;
    }

    public int getValue() {
        return id;
    }
}
