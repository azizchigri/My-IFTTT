package com.example.area.API;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User {

    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("lastName")
    @Expose
    private String lastName;
    @SerializedName("firstName")
    @Expose
    private String firstName;
    @SerializedName("gmailToken")
    @Expose
    private String gmailToken;
    @SerializedName("youtubeToken")
    @Expose
    private String youtubeToken;
    @SerializedName("spotifyToken")
    @Expose
    private String spotifyToken;
    @SerializedName("redditToken")
    @Expose
    private String redditToken;
    @SerializedName("twitchToken")
    @Expose
    private String twitchToken;
    @SerializedName("subServices")
    @Expose
    private String subServices;

    /**
     * No args constructor for use in serialization
     *
     */
    public User() {
    }

    /**
     *
     * @param lastName
     * @param username
     * @param gmailToken
     * @param email
     * @param twitchToken
     * @param youtubeToken
     * @param redditToken
     * @param subServices
     * @param firstName
     * @param spotifyToken
     * @param password
     */
    public User(String email, String password, String username, String lastName, String firstName, String gmailToken, String youtubeToken, String spotifyToken, String redditToken, String twitchToken, String subServices) {
        super();
        this.email = email;
        this.password = password;
        this.username = username;
        this.lastName = lastName;
        this.firstName = firstName;
        this.gmailToken = gmailToken;
        this.youtubeToken = youtubeToken;
        this.spotifyToken = spotifyToken;
        this.redditToken = redditToken;
        this.twitchToken = twitchToken;
        this.subServices = subServices;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public User withEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public User withPassword(String password) {
        this.password = password;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public User withUsername(String username) {
        this.username = username;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public User withLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public User withFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getGmailToken() {
        return gmailToken;
    }

    public void setGmailToken(String gmailToken) {
        this.gmailToken = gmailToken;
    }

    public User withGmailToken(String gmailToken) {
        this.gmailToken = gmailToken;
        return this;
    }

    public String getYoutubeToken() {
        return youtubeToken;
    }

    public void setYoutubeToken(String youtubeToken) {
        this.youtubeToken = youtubeToken;
    }

    public User withYoutubeToken(String youtubeToken) {
        this.youtubeToken = youtubeToken;
        return this;
    }

    public String getSpotifyToken() {
        return spotifyToken;
    }

    public void setSpotifyToken(String spotifyToken) {
        this.spotifyToken = spotifyToken;
    }

    public User withSpotifyToken(String spotifyToken) {
        this.spotifyToken = spotifyToken;
        return this;
    }

    public String getRedditToken() {
        return redditToken;
    }

    public void setRedditToken(String redditToken) {
        this.redditToken = redditToken;
    }

    public User withRedditToken(String redditToken) {
        this.redditToken = redditToken;
        return this;
    }

    public String getTwitchToken() {
        return twitchToken;
    }

    public void setTwitchToken(String twitchToken) {
        this.twitchToken = twitchToken;
    }

    public User withTwitchToken(String twitchToken) {
        this.twitchToken = twitchToken;
        return this;
    }

    public String getSubServices() {
        return subServices;
    }

    public void setSubServices(String subServices) {
        this.subServices = subServices;
    }

    public User withSubServices(String subServices) {
        this.subServices = subServices;
        return this;
    }

}
