package com.example.android.lagosdevs;

/**
 * Created by Ogunbowale on 8/20/2017.
 */

public class Profiles {

    private String userLogin;
    private String userImage;
    private String userUrl;



    public Profiles(String login, String avatar, String gitURL) {
        this.userLogin = login;
        this.userImage = avatar;
        this.userUrl = gitURL;
    }



    public String getUserLogin() {
        return userLogin;
    }

    public String getUserImage() {
        return userImage;
    }

    public String getUserUrl() {
        return userUrl;
    }

}
