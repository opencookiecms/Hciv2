package com.hcidev.hciv2.model;

import com.google.gson.annotations.SerializedName;

public class Note {


    @SerializedName("note_id")
    private int note_id;
    @SerializedName("note_title")
    private String note_title;
    @SerializedName("note_content")
    private String note_content;
    @SerializedName("note_link")
    private String note_link;
    @SerializedName("note_type")
    private String note_type;
    @SerializedName("note_username")
    private String note_username;
    @SerializedName("note_reg")
    private String note_reg;


    public Note(int note_id, String note_title, String note_content, String note_link, String note_type, String note_username, String note_reg) {
        this.note_id = note_id;
        this.note_title = note_title;
        this.note_content = note_content;
        this.note_link = note_link;
        this.note_type = note_type;
        this.note_username = note_username;
        this.note_reg = note_reg;
    }

    public int getNote_id() {
        return note_id;
    }

    public String getNote_title() {
        return note_title;
    }

    public String getNote_content() {
        return note_content;
    }

    public String getNote_link() {
        return note_link;
    }

    public String getNote_type() {
        return note_type;
    }

    public String getNote_username() {
        return note_username;
    }

    public String getNote_reg() {
        return note_reg;
    }

}
