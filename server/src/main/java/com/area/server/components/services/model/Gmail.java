package com.area.server.components.services.model;

import javax.persistence.Entity;

/**
 * The type Gmail.
 */
@Entity
public class Gmail extends Services {

    /**
     * Instantiates a new Gmail.
     */
    public Gmail() {
		super();
		this.setName("Gmail");
		createAction("Gm_rcv_mail", "I have received an email after a given date");
		createReaction("Gm_send_mail", "Send an mail to someone");
	}
}