package com.area.server.components.services.model;

import javax.persistence.Entity;

/**
 * The type Spotify.
 */
@Entity
public class Spotify extends Services {

    /**
     * Instantiates a new Spotify.
     */
    public Spotify() {
		super();
		this.setName("Spotify");
		createAction("Sp_artist_follower", "An artist Reach the number of followers");
		createReaction("Sp_follow_playlist", "Follow a given playlist");
		createReaction("Sp_unfollow_playlist", "Unfollow a given playlist");
		createReaction("Sp_follow_artist", "Follow a given artist");
	}
}