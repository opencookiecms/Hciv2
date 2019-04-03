package com.hcidev.hciv2.model;

import com.google.gson.annotations.SerializedName;

public class Subscribefollow {

    @SerializedName("user_id")
    private int user_id;
    @SerializedName("hci_fid")
    private int hci_fid;
    @SerializedName("user_firstname")
    private String user_firstname;
    @SerializedName("user_lastname")
    private String user_lastname;
    @SerializedName("user_username")
    private String user_username;
    @SerializedName("user_email")
    private String user_email;
    @SerializedName("user_collage")
    private String user_collage;
    @SerializedName("user_positition")
    private String user_positition;
    @SerializedName("user_caption")
    private String user_caption;
    @SerializedName("user_pic")
    private String user_pic;

    public Subscribefollow(int user_id, int hci_fid, String user_firstname, String user_lastname, String user_username, String user_email, String user_collage, String user_positition, String user_caption, String user_pic) {
        this.user_id = user_id;
        this.hci_fid = hci_fid;
        this.user_firstname = user_firstname;
        this.user_lastname = user_lastname;
        this.user_username = user_username;
        this.user_email = user_email;
        this.user_collage = user_collage;
        this.user_positition = user_positition;
        this.user_caption = user_caption;
        this.user_pic = user_pic;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getHci_fid() {
        return hci_fid;
    }

    public void setHci_fid(int hci_fid) {
        this.hci_fid = hci_fid;
    }

    public String getUser_firstname() {
        return user_firstname;
    }

    public void setUser_firstname(String user_firstname) {
        this.user_firstname = user_firstname;
    }

    public String getUser_lastname() {
        return user_lastname;
    }

    public void setUser_lastname(String user_lastname) {
        this.user_lastname = user_lastname;
    }

    public String getUser_username() {
        return user_username;
    }

    public void setUser_username(String user_username) {
        this.user_username = user_username;
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    public String getUser_collage() {
        return user_collage;
    }

    public void setUser_collage(String user_collage) {
        this.user_collage = user_collage;
    }

    public String getUser_positition() {
        return user_positition;
    }

    public void setUser_positition(String user_positition) {
        this.user_positition = user_positition;
    }

    public String getUser_caption() {
        return user_caption;
    }

    public void setUser_caption(String user_caption) {
        this.user_caption = user_caption;
    }

    public String getUser_pic() {
        return user_pic;
    }

    public void setUser_pic(String user_pic) {
        this.user_pic = user_pic;
    }
}
