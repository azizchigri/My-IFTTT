package com.example.area.API;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AboutServer {

    @SerializedName("current_time")
    @Expose
    private Integer currentTime;
    @SerializedName("services")
    @Expose
    private List<AboutServices> services = null;

    /**
     * No args constructor for use in serialization
     *
     */
    public AboutServer() {
    }

    /**
     *
     * @param currentTime
     * @param services
     */
    public AboutServer(Integer currentTime, List<AboutServices> services) {
        super();
        this.currentTime = currentTime;
        this.services = services;
    }

    public Integer getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(Integer currentTime) {
        this.currentTime = currentTime;
    }

    public AboutServer withCurrentTime(Integer currentTime) {
        this.currentTime = currentTime;
        return this;
    }

    public List<AboutServices> getServices() {
        return services;
    }

    public void setServices(List<AboutServices> services) {
        this.services = services;
    }

    public AboutServer withServices(List<AboutServices> services) {
        this.services = services;
        return this;
    }

}