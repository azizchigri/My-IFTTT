package com.area.server.components.actions.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * The type Actions.
 */
@Entity
@Table(name = "actions")
public class Actions {

	@Id
	@NotNull
    @Size(max = 100)
	@Column(name = "action_name", unique=true)
	private String name;

	@NotNull
    @Size(max = 100)
	@Column(name = "action_description")
	private String description;

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
		return name;
	}

    /**
     * Sets name.
     *
     * @param name the name
     */
    public void setName(String name) {
		this.name = name;
	}

    /**
     * Gets description.
     *
     * @return the description
     */
    public String getDescription() {
		return description;
	}

    /**
     * Sets description.
     *
     * @param description the description
     */
    public void setDescription(String description) {
		this.description = description;
	}
}