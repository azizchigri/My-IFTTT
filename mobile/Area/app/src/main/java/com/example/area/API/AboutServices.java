package com.example.area.API;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AboutServices {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("actions")
    @Expose
    private List<Action> actions = null;
    @SerializedName("reactions")
    @Expose
    private List<Reaction> reactions = null;

    /**
     * No args constructor for use in serialization
     *
     */
    public AboutServices() {
    }

    /**
     *
     * @param reactions
     * @param name
     * @param actions
     */
    public AboutServices(String name, List<Action> actions, List<Reaction> reactions) {
        super();
        this.name = name;
        this.actions = actions;
        this.reactions = reactions;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AboutServices withName(String name) {
        this.name = name;
        return this;
    }

    public List<Action> getActions() {
        return actions;
    }

    public void setActions(List<Action> actions) {
        this.actions = actions;
    }

    public AboutServices withActions(List<Action> actions) {
        this.actions = actions;
        return this;
    }

    public List<Reaction> getReactions() {
        return reactions;
    }

    public void setReactions(List<Reaction> reactions) {
        this.reactions = reactions;
    }

    public AboutServices withReactions(List<Reaction> reactions) {
        this.reactions = reactions;
        return this;
    }

}