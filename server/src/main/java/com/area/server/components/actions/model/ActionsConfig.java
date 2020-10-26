package com.area.server.components.actions.model;

import com.area.server.components.reactions.model.ReactionsConfig;
import com.area.server.components.user.model.ApplicationUser;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * The type Actions config.
 */
@Entity
@JsonIgnoreProperties(value = { "user" })
@Table(name = "actions_config")
public class ActionsConfig {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Integer id;

	@NotNull
	@Column(name = "action_name")
	private String name;

	@NotNull
	@Column(name = "service_name")
	private String serviceName;

	@ManyToOne
	private ApplicationUser user;

	@OneToOne()
	private ReactionsConfig reaction;

	@OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
	private Set<DbMap> parameters = new HashSet<>();

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
     * Gets service name.
     *
     * @return the service name
     */
    public String getServiceName() {
		return serviceName;
	}

    /**
     * Sets service name.
     *
     * @param description the description
     */
    public void setServiceName(String description) {
		this.serviceName = description;
	}

    /**
     * Gets user.
     *
     * @return the user
     */
    public ApplicationUser getUser() {
		return user;
	}

    /**
     * Sets user.
     *
     * @param user the user
     */
    public void setUser(ApplicationUser user) {
		this.user = user;
	}

    /**
     * Gets reaction.
     *
     * @return the reaction
     */
    public ReactionsConfig getReaction() {
		return reaction;
	}

    /**
     * Sets reaction.
     *
     * @param reaction the reaction
     */
    public void setReaction(ReactionsConfig reaction) {
		this.reaction = reaction;
	}

    /**
     * Gets parameters.
     *
     * @return the parameters
     */
    public Collection<DbMap> getParameters() {
		return parameters;
	}

    /**
     * Sets parameters.
     *
     * @param parameters the parameters
     */
    public void setParameters(Set<DbMap> parameters) {
		this.parameters = parameters;
	}

    /**
     * Add parameters.
     *
     * @param parameters the parameters
     */
    public void addParameters(DbMap parameters) {
		this.parameters.add(parameters);
	}
}