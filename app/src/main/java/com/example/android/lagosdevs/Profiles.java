package com.example.android.lagosdevs;

/**
 * Created by Ogunbowale on 8/20/2017.
 */

public class Profiles {

    private String userLogin;
    private String userImage;
    private String userUrl;
    private String profileFullName;
    private String profileRepos;
    private String profileFFs;
    private String profileFollowing;


    public Profiles(String login, String avatar, String gitURL) {
        this.userLogin = login;
        this.userImage = avatar;
        this.userUrl = gitURL;
    }

       //  Overload the Profiles class in order to bring in additional parameters that would be
       // used the the DetailProfile.java file.
            public Profiles(String profileFullName, String profileRepos, String profileFFs, String profileFollowing) {
                this.profileFullName = profileFullName;
                this.profileRepos = profileRepos;
                this.profileFFs = profileFFs;
                this.profileFollowing = profileFollowing;
            }

    // Getter methods
        public String getUserLogin() {
            return userLogin;
        }
        public String getUserImage() {
            return userImage;
        }
        public String getUserUrl() {
            return userUrl;
        }
        public String getProfileFullName() {return profileFullName;}
        public String getProfileRepos() {return profileRepos;}
        public String getProfileFFs() {return profileFFs;}
        public String getProfileFollowing() {return profileFollowing;}

}
