package com.area.server.components.reactions.model;

import com.area.server.components.actions.model.DbMap;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * The type Reactions config.
 */
@Entity
@Table(name = "reactions_config")
public class ReactionsConfig{

	@Id
	@JsonIgnore
	@GeneratedValue(strategy = GenerationType.AUTO)
	Integer id;

	@NotNull
    @Size(max = 100)
	@Column(name = "reaction_name")
	private String name;

	@NotNull
	@Column(name = "service_name")
	private String serviceName;

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
}