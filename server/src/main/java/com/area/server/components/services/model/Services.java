package com.area.server.components.services.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.area.server.components.actions.model.Actions;
import com.area.server.components.reactions.model.Reactions;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

/**
 * The type Services.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "services")
public abstract class Services {

	@Id
	@NotNull
    @Size(max = 100)
	@Column(name = "service_name", unique=true)
	private String name;
	
	@OneToMany
	@JoinColumn(name="service_name")
	private Set<Actions> actions = new HashSet<Actions>();

	@OneToMany
	@JoinColumn(name="service_name")
	private Set<Reactions> reactions = new HashSet<Reactions>();

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
     * Gets actions.
     *
     * @return the actions
     */
    public Set<Actions> getActions() {
		return actions;
	}

    /**
     * Sets actions.
     *
     * @param actions the actions
     */
    public void setActions(Set<Actions> actions) {
		this.actions = actions;
	}

    /**
     * Add action.
     *
     * @param params the params
     */
    public void addAction(Actions params) {
		this.actions.add(params);
	}

    /**
     * Gets reactions.
     *
     * @return the reactions
     */
    public Set<Reactions> getReactions() {
		return reactions;
	}

    /**
     * Sets reactions.
     *
     * @param reactions the reactions
     */
    public void setReactions(Set<Reactions> reactions) {
		this.reactions = reactions;
	}

    /**
     * Add reaction.
     *
     * @param params the params
     */
    public void addReaction(Reactions params) {
		this.reactions.add(params);
	}

    /**
     * Create action.
     *
     * @param name        the name
     * @param description the description
     */
    protected void createAction(String name, String description) {
		Actions act = new Actions();
		act.setName(name);
		act.setDescription(description);
		this.addAction(act);
	}

    /**
     * Create reaction.
     *
     * @param name        the name
     * @param description the description
     */
    protected void createReaction(String name, String description) {
		Reactions act = new Reactions();
		act.setName(name);
		act.setDescription(description);
		this.addReaction(act);
	}
}