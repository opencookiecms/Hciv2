package com.hcidev.hciv2.model;

import com.google.gson.annotations.SerializedName;

public class Follows {

    @SerializedName("hci_following")
    private String hci_following;
    @SerializedName("hci_follower")
    private String hci_follower;

    @SerializedName("follower")
    private String follower;
    @SerializedName("following")
    private String following;

    public Follows(String hci_following, String hci_follower, String follower, String following) {
        this.hci_following = hci_following;
        this.hci_follower = hci_follower;
        this.follower = follower;
        this.following = following;
    }

    public String getFollower() {
        return follower;
    }

    public void setFollower(String follower) {
        this.follower = follower;
    }

    public String getFollowing() {
        return following;
    }

    public void setFollowing(String following) {
        this.following = following;
    }

    public String getHci_following() {
        return hci_following;
    }

    public void setHci_following(String hci_following) {
        this.hci_following = hci_following;
    }

    public String getHci_follower() {
        return hci_follower;
    }

    public void setHci_follower(String hci_follower) {
        this.hci_follower = hci_follower;
    }
}
