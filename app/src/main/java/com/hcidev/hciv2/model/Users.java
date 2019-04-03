package com.hcidev.hciv2.model;

import com.google.gson.annotations.SerializedName;

public class Users {


    @SerializedName("user_id")
    private Integer user_id;
    @SerializedName("user_firstname")
    private String user_firstname;
    @SerializedName("user_lastname")
    private String user_lastname;
    @SerializedName("user_username")
    private String user_username;
    @SerializedName("user_password")
    private String user_password;
    @SerializedName("user_email")
    private String user_email;
    @SerializedName("user_collage")
    private String user_collage;
    @SerializedName("user_position")
    private String user_position;
    @SerializedName("user_pic")
    private String user_pic;
    @SerializedName("user_caption")
    private String user_caption;
    @SerializedName("user_reg")
    private String user_reg;

    public Users(String user_firstname, String user_lastname, String user_username, String user_password, String user_email, String user_collage, String user_position, String user_pic, String user_caption, String user_reg) {
        //this.user_id = user_id;
        this.user_firstname = user_firstname;
        this.user_lastname = user_lastname;
        this.user_username = user_username;
        this.user_password = user_password;
        this.user_email = user_email;
        this.user_collage = user_collage;
        this.user_position = user_position;
        this.user_pic = user_pic;
        this.user_caption = user_caption;
        this.user_reg = user_reg;
    }

    //getter

    public int getUser_id() {
        return user_id;
    }

    public String getUser_firstname() {
        return user_firstname;
    }

    public String getUser_lastname() {
        return user_lastname;
    }

    public String getUser_username() {
        return user_username;
    }

    public String getUser_password() {
        return user_password;
    }

    public String getUser_email() {
        return user_email;
    }

    public String getUser_collage() {
        return user_collage;
    }

    public String getUser_position() {
        return user_position;
    }

    public String getUser_pic() {
        return user_pic;
    }

    public String getUser_caption() {
        return user_caption;
    }

    public String getUser_reg() {
        return user_reg;
    }

    //setter


    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public void setUser_firstname(String user_firstname) {
        this.user_firstname = user_firstname;
    }

    public void setUser_lastname(String user_lastname) {
        this.user_lastname = user_lastname;
    }

    public void setUser_username(String user_username) {
        this.user_username = user_username;
    }

    public void setUser_password(String user_password) {
        this.user_password = user_password;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    public void setUser_collage(String user_collage) {
        this.user_collage = user_collage;
    }

    public void setUser_position(String user_position) {
        this.user_position = user_position;
    }

    public void setUser_pic(String user_pic) {
        this.user_pic = user_pic;
    }

    public void setUser_caption(String user_caption) {
        this.user_caption = user_caption;
    }

    public void setUser_reg(String user_reg) {
        this.user_reg = user_reg;
    }
}
