package com.area.server.components.services.model;

import javax.persistence.Entity;

/**
 * The type Weather.
 */
@Entity
public class Weather extends Services {

    /**
     * Instantiates a new Weather.
     */
    public Weather() {
		super();
		this.setName("Weather");
		createAction("We_temperature_reached", "The selected temperature has been reached");
		createAction("We_bellow_temperature", "The city temperature is bellow the selected temperature");
	}
}