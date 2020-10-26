package com.example.area.API;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AboutActions {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("description")
    @Expose
    private String description;

    /**
     * No args constructor for use in serialization
     *
     */
    public AboutActions() {
    }

    /**
     *
     * @param description
     * @param name
     */
    public AboutActions(String name, String description) {
        super();
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AboutActions withName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public AboutActions withDescription(String description) {
        this.description = description;
        return this;
    }

}