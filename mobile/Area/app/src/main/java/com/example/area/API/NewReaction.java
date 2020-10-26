
package com.example.area.API;

import com.google.gson.JsonArray;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NewReaction {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("serviceName")
    @Expose
    private String serviceName;
    @SerializedName("parameters")
    @Expose
    private JsonArray parameters = null;

    /**
     * No args constructor for use in serialization
     */
    public NewReaction() {
    }

    /**
     * @param name
     * @param parameters
     * @param serviceName
     */
    public NewReaction(String name, String serviceName, JsonArray parameters) {
        super();
        this.name = name;
        this.serviceName = serviceName;
        this.parameters = parameters;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public NewReaction withName(String name) {
        this.name = name;
        return this;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public NewReaction withServiceName(String serviceName) {
        this.serviceName = serviceName;
        return this;
    }

    public JsonArray getParameters() {
        return parameters;
    }

    public void setParameters(JsonArray parameters) {
        this.parameters = parameters;
    }

    public NewReaction withParameters(JsonArray parameters) {
        this.parameters = parameters;
        return this;
    }
}
