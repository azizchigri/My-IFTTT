package com.example.area.API;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class About {

    @SerializedName("client")
    @Expose
    private AboutClient client;
    @SerializedName("server")
    @Expose
    private AboutServer server;

    /**
     * No args constructor for use in serialization
     *
     */
    public About() {
    }

    /**
     *
     * @param client
     * @param server
     */
    public About(AboutClient client, AboutServer server) {
        super();
        this.client = client;
        this.server = server;
    }

    public AboutClient getClient() {
        return client;
    }

    public void setClient(AboutClient client) {
        this.client = client;
    }

    public About withClient(AboutClient client) {
        this.client = client;
        return this;
    }

    public AboutServer getServer() {
        return server;
    }

    public void setServer(AboutServer server) {
        this.server = server;
    }

    public About withServer(AboutServer server) {
        this.server = server;
        return this;
    }

}