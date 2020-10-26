package com.example.area.API;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AboutClient {

    @SerializedName("host")
    @Expose
    private String host;

    /**
     * No args constructor for use in serialization
     *
     */
    public AboutClient() {
    }

    /**
     *
     * @param host
     */
    public AboutClient(String host) {
        super();
        this.host = host;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public AboutClient withHost(String host) {
        this.host = host;
        return this;
    }

}