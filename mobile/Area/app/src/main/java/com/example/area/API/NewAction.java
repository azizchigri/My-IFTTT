package com.example.area.API;

import com.google.gson.JsonArray;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NewAction {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("serviceName")
    @Expose
    private String serviceName;
    @SerializedName("reaction")
    @Expose
    private NewReaction reaction;
    @SerializedName("parameters")
    @Expose
    private JsonArray parameters = null;

    /**
     * No args constructor for use in serialization
     *
     */
    public NewAction() {
    }

    /**
     *
     * @param reaction
     * @param name
     * @param parameters
     * @param serviceName
     */
    public NewAction(String name, String serviceName, NewReaction reaction, JsonArray parameters) {
        super();
        this.name = name;
        this.serviceName = serviceName;
        this.reaction = reaction;
        this.parameters = parameters;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public NewAction withName(String name) {
        this.name = name;
        return this;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public NewAction withServiceName(String serviceName) {
        this.serviceName = serviceName;
        return this;
    }

    public NewReaction getReaction() {
        return reaction;
    }

    public void setReaction(NewReaction reaction) {
        this.reaction = reaction;
    }

    public NewAction withReaction(NewReaction reaction) {
        this.reaction = reaction;
        return this;
    }

    public JsonArray getParameters() {
        return parameters;
    }

    public void setParameters(JsonArray parameters) {
        this.parameters = parameters;
    }

    public NewAction withParameters(JsonArray parameters) {
        this.parameters = parameters;
        return this;
    }

}