package com.area.server.components.services.model;

import javax.persistence.Entity;

/**
 * The type Reddit.
 */
@Entity
public class Reddit extends Services {

    /**
     * Instantiates a new Reddit.
     */
    public Reddit() {
		super();
		this.setName("Reddit");
		createReaction("Rd_add_friend", "Add a friend");
		createReaction("Rd_rm_friend", "Remove a friend");
	}
}