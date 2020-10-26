package com.area.server.components.user.model;

import com.area.server.components.actions.model.ActionsConfig;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.HashSet;
import java.util.Set;

/**
 * The type Application user.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user")
public class ApplicationUser {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "user_id")
	private int id;

	@Column(name = "email")
	@Email(message = "Please provide a valid Email")
	@NotEmpty(message = "Please provide an email")
	private String email;

	@Column(name = "password")
	@Length(min = 5, message = "Your password must have at least 5 characters")
	@NotEmpty(message = "Please provide your password")
	private String password;

	@Column(name = "username", unique=true)
	@NotEmpty(message = "Please provide your username")
	private String username;

	@Column(name = "last_name")
	@NotEmpty(message = "Please provide your last name")
	private String lastName;

	@Column(name = "first_name")
	@NotEmpty(message = "Please provide your first name")
	private String firstName;

	@Column(name = "active")
	private int active;

	@Column(name = "token_gmail")
	private String gmailToken;

	@Column(name = "token_youtube")
	private String youtubeToken;

	@Column(name = "token_spotify")
	private String spotifyToken;

	@Column(name = "token_reddit")
	private String redditToken;

	@Column(name = "token_twitch")
	private String twitchToken;

	@Column(name = "sub_services")
	private String subServices;

	@Builder.Default
	@JsonIgnore
	@OneToMany(fetch = FetchType.EAGER)
	@JoinColumn(name="user_id")
	private Set<ActionsConfig> actions = new HashSet<>();

    /**
     * Gets gmail token.
     *
     * @return the gmail token
     */
    public String getGmailToken() {
		return gmailToken;
	}

    /**
     * Sets gmail token.
     *
     * @param gmailToken the gmail token
     */
    public void setGmailToken(String gmailToken) {
		this.gmailToken = gmailToken;
	}

    /**
     * Gets youtube token.
     *
     * @return the youtube token
     */
    public String getYoutubeToken() {
		return youtubeToken;
	}

    /**
     * Sets youtube token.
     *
     * @param youtubeToken the youtube token
     */
    public void setYoutubeToken(String youtubeToken) {
		this.youtubeToken = youtubeToken;
	}

    /**
     * Gets spotify token.
     *
     * @return the spotify token
     */
    public String getSpotifyToken() {
		return spotifyToken;
	}

    /**
     * Sets spotify token.
     *
     * @param spotifyToken the spotify token
     */
    public void setSpotifyToken(String spotifyToken) {
		this.spotifyToken = spotifyToken;
	}

    /**
     * Gets facebook token.
     *
     * @return the facebook token
     */
    public String getRedditToken() {
		return redditToken;
	}

    /**
     * Sets facebook token.
     *
     * @param redditToken the facebook token
     */
    public void setRedditToken(String redditToken) {
		this.redditToken = redditToken;
	}

    /**
     * Gets twitch token.
     *
     * @return the twitch token
     */
    public String getTwitchToken() {
		return twitchToken;
	}

    /**
     * Sets twitch token.
     *
     * @param twitchToken the twitch token
     */
    public void setTwitchToken(String twitchToken) {
		this.twitchToken = twitchToken;
	}

    /**
     * Gets email.
     *
     * @return the email
     */
    public String getEmail() {
		return email;
	}

    /**
     * Sets email.
     *
     * @param email the email
     */
    public void setEmail(String email) {
		this.email = email;
	}

    /**
     * Gets password.
     *
     * @return the password
     */
    public String getPassword() {
		return password;
	}

    /**
     * Sets password.
     *
     * @param password the password
     */
    public void setPassword(String password) {
		this.password = password;
	}

    /**
     * Gets username.
     *
     * @return the username
     */
    public String getUsername() {
		return username;
	}

    /**
     * Sets username.
     *
     * @param name the name
     */
    public void setUsername(String name) {
		this.username = name;
	}

    /**
     * Gets last name.
     *
     * @return the last name
     */
    public String getLastName() {
		return lastName;
	}

    /**
     * Sets last name.
     *
     * @param lastName the last name
     */
    public void setLastName(String lastName) {
		this.lastName = lastName;
	}

    /**
     * Gets active.
     *
     * @return the active
     */
    public int getActive() {
		return active;
	}

    /**
     * Sets active.
     *
     * @param active the active
     */
    public void setActive(int active) {
		this.active = active;
	}

    /**
     * Gets first name.
     *
     * @return the first name
     */
    public String getFirstName() {
		return firstName;
	}

    /**
     * Sets first name.
     *
     * @param firstName the first name
     */
    public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

    /**
     * Gets actions.
     *
     * @return the actions
     */
    public Set<ActionsConfig> getActions() {
		return actions;
	}

    /**
     * Sets actions.
     *
     * @param actions the actions
     */
    public void setActions(Set<ActionsConfig> actions) {
		this.actions = actions;
	}

    /**
     * Add action.
     *
     * @param actions the actions
     */
    public void addAction(ActionsConfig actions) {
		this.actions.add(actions);
	}

	public String getSubServices() {
		return subServices;
	}

	public void setSubServices(String subServices) {
		this.subServices = subServices;
	}
}