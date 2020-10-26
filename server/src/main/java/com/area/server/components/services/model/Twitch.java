package com.area.server.components.services.model;

import javax.persistence.Entity;

/**
 * The type Twitch.
 */
@Entity
public class Twitch extends Services {

    /**
     * Instantiates a new Twitch.
     */
    public Twitch() {
		super();
		this.setName("Twitch");
		createAction("Tw_view_number", "The User reached the the number of view");
		createAction("Tw_is_affiliate", "The User is now affiliate with Twitch");
		createAction("Tw_is_partner", "The User is now a partner with Twitch");
		createAction("Tw_followers_number", "The User reached the the number of followers");
		createReaction("Tw_update_Description","Update the User's profile description");
	}
}