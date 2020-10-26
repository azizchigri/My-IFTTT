package com.area.server.components.services.model;

import javax.persistence.Entity;

/**
 * The type Timer.
 */
@Entity
public class Timer extends Services {

    /**
     * Instantiates a new Timer.
     */
    public Timer() {
		super();
		this.setName("Timer");
		createAction("Ti_timeout", "The selected time is over");
		createAction("Ti_frequency", "Make an action every frequency (frequency is an Integer and will be multiplied by 30 sec)");
	}
}