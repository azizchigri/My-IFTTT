package com.example.area.API;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SpecificReaction {

    @SerializedName("service")
    @Expose
    private String service;
    @SerializedName("action")
    @Expose
    private String action;
    @SerializedName("params")
    @Expose
    private List<Param> params = null;

    /**
     * No args constructor for use in serialization
     *
     */
    public SpecificReaction() {
    }

    /**
     *
     * @param action
     * @param service
     * @param params
     */
    public SpecificReaction(String service, String action, List<Param> params) {
        super();
        this.service = service;
        this.action = action;
        this.params = params;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public SpecificReaction withService(String service) {
        this.service = service;
        return this;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public SpecificReaction withAction(String action) {
        this.action = action;
        return this;
    }

    public List<Param> getParams() {
        return params;
    }

    public void setParams(List<Param> params) {
        this.params = params;
    }

    public SpecificReaction withParams(List<Param> params) {
        this.params = params;
        return this;
    }

}