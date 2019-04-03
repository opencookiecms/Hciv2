package com.hcidev.hciv2.model;

public class Notecount {

    private String notecounting;
    private String note_username;

    public Notecount(String notecounting, String note_username) {
        this.notecounting = notecounting;
        this.note_username = note_username;
    }

    public String getNotecounting() {
        return notecounting;
    }

    public void setNotecounting(String notecounting) {
        this.notecounting = notecounting;
    }

    public String getNote_username() {
        return note_username;
    }

    public void setNote_username(String note_username) {
        this.note_username = note_username;
    }
}
