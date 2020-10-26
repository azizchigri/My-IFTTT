package com.area.server.components.actions.controller;

import com.area.server.components.actions.model.Actions;
import com.area.server.components.actions.model.ActionsConfig;
import com.area.server.components.actions.repository.ApplicationActionsConfigRepository;
import com.area.server.components.actions.repository.ApplicationActionsRepository;
import com.area.server.components.reactions.repository.ApplicationReactionsConfigRepository;
import com.area.server.components.user.model.ApplicationUser;
import com.area.server.components.user.repository.ApplicationUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * The type Actions controller.
 */
@RestController
@RequestMapping("/actions")
public class ActionsController {

    /**
     * The Application actions repository.
     */
    @Autowired
    ApplicationActionsRepository applicationActionsRepository;
    @Autowired
    ApplicationUserRepository applicationUserRepository;
    @Autowired
    ApplicationActionsConfigRepository applicationActionsConfigRepository;
    @Autowired
    ApplicationReactionsConfigRepository applicationReactionsConfigRepository;
    /**
     * Get actions list.
     *
     * @return the list
     */
    @GetMapping
    public List<Actions> getActions(){
        return applicationActionsRepository.findAll();
    }

    /**
     * Gets gmail msg.
     *
     * @return the gmail msg
     */
    @GetMapping("/Gm_rcv_mail")
    public String getGmailMsg () {
        return "{\n" +
                "\t\"service\": \"Gmail\",\n" +
                "\t\"action\": \"Gm_rcv_mail\",\n" +
                "\t\"params\": [{\"name\": \"date\", \"type\" : \"Date\"}]\n" +
                "}";
    }

    /**
     * Gets spotify new song.
     *
     * @return the spotify new song
     */
    @GetMapping("/Sp_artist_follower")
    public String getSpotifyNewSong() {
        return "{\n" +
                "\"service\" : \"Spotify\",\n" +
                "\"action\" : \"Sp_artist_follower\",\n" +
                "\"params\" : [{\"name\" : \"ArtistSpotifyId\", \"type\" : \"String\"}, {\"name\" : \"followersNumber\", \"type\" : \"Integer\"}]\n" +
                "}";
    }

    /**
     * Gets timer timeout.
     *
     * @return the timer timeout
     */
    @GetMapping("/Ti_timeout")
    public String getTimerTimeout() {
        return "{\n" +
                "\t\"service\": \"Timer\",\n" +
                "\t\"action\": \"Ti_timeout\",\n" +
                "\t\"params\": [{\"name\": \"date\", \"type\" : \"Date\"}]\n" +
                "}";
    }

    /**
     * Gets timerequency.
     *
     * @return the timerequency
     */
    @GetMapping("/Ti_frequency")
    public String getTimerequency() {
        return "{\n" +
                "\"service\" : \"Timer\",\n" +
                "\"action\" : \"Ti_frequency\",\n" +
                "\"params\" : [{\"name\" : \"date\", \"type\" : \"Date\"}, {\"name\" : \"frequency\", \"type\" : \"Integer\"}]\n" +
                "}";
    }

    /**
     * Gets temperature reached.
     *
     * @return the temperature reached
     */
    @GetMapping("/We_temperature_reached")
    public String getTemperatureReached() {
        return "{\n" +
                "\t\"service\": \"Weather\",\n" +
                "\t\"action\": \"We_temperature_reached\",\n" +
                "\t\"params\": [{\"name\": \"temperature\", \"type\" : \"Double\"}, {\"name\" : \"city\", \"type\" : \"String\"}]\n" +
                "}";
    }

    /**
     * Gets temperature bellow.
     *
     * @return the temperature bellow
     */
    @GetMapping("/We_bellow_temperature")
    public String getTemperatureBellow() {
        return "{\n" +
                "\t\"service\": \"Weather\",\n" +
                "\t\"action\": \"We_bellow_temperature\",\n" +
                "\t\"params\": [{\"name\": \"temperature\", \"type\" : \"Double\"}, {\"name\" : \"city\", \"type\" : \"String\"}]\n" +
                "}";
    }

    /**
     * Gets yt new video.
     *
     * @return the yt new video
     */
    @GetMapping("/Yt_new_video_on_channel")
    public String getYtNewVideo() {
        return "{\n" +
                "\"service\" : \"Youtube\",\n" +
                "\"action\" : \"Yt_new_video_on_channel\",\n" +
                "\"params\" : [{\"name\" : \"date\", \"type\" : \"Date\"}, {\"name\" : \"channelId\", \"type\" : \"String\"}]\n" +
                "}";
    }

    /**
     * Gets yt subscribers.
     *
     * @return the yt subscribers
     */
    @GetMapping("/Yt_subscribers_number")
    public String getYtSubscribers() {
        return "{\n" +
                "\t\"service\": \"Youtube\",\n" +
                "\t\"action\": \"Yt_subscribers_number\",\n" +
                "\t\"params\": [{\"name\": \"limit\", \"type\" : \"Long\"}, {\"name\": \"channelId\", \"type\" : \"String\"}]\n" +
                "}";
    }

    /**
     * Gets yt view number.
     *
     * @return the yt view nulmber
     */
    @GetMapping("/Yt_view_number")
    public String getYtViewNumber() {
        return "{\n" +
                "\t\"service\": \"Youtube\",\n" +
                "\t\"action\": \"Yt_view_number\",\n" +
                "\t\"params\": [{\"name\": \"limit\", \"type\" : \"Long\"}, {\"name\": \"channelId\", \"type\" : \"String\"}]\n" +
                "}";
    }

    /**
     * Gets yt upload number.
     *
     * @return the yt upload number
     */
    @GetMapping("/Yt_upload_number")
    public String getYtUploadNumber() {
        return "{\n" +
                "\t\"service\": \"Youtube\",\n" +
                "\t\"action\": \"Yt_upload_number\",\n" +
                "\t\"params\": [{\"name\": \"limit\", \"type\" : \"Long\"}, {\"name\": \"channelId\", \"type\" : \"String\"}]\n" +
                "}";
    }

    @GetMapping("/Tw_view_number")
    public String getTw_view_number() {
        return "{\n" +
                "\t\"service\": \"Twitch\",\n" +
                "\t\"action\": \"Tw_view_number\",\n" +
                "\t\"params\": [{\"name\": \"limit\", \"type\" : \"Long\"}]\n" +
                "}";
    }

    @GetMapping("/Tw_is_affiliate")
    public String getTw_is_affiliate() {
        return "{\n" +
                "\t\"service\": \"Twitch\",\n" +
                "\t\"action\": \"Tw_is_affiliate\",\n" +
                "\t\"params\": []\n" +
                "}";
    }

    @GetMapping("/Tw_is_partner")
    public String getTw_is_partner() {
        return "{\n" +
                "\t\"service\": \"Twitch\",\n" +
                "\t\"action\": \"Tw_is_partner\",\n" +
                "\t\"params\": []\n" +
                "}";
    }

    @GetMapping("/Tw_followers_number")
    public String getTw_followers_number() {
        return "{\n" +
                "\t\"service\": \"Twitch\",\n" +
                "\t\"action\": \"Tw_followers_number\",\n" +
                "\t\"params\": [{\"name\": \"limit\", \"type\" : \"Long\"}, {\"name\": \"twitchId\", \"type\" : \"String\"}]\n" +
                "}";
    }

    /**
     * insert action linked with reaction in database
     *
     * @return the saved Action
     */
    @PostMapping()
    public ActionsConfig addAction(@RequestBody @Valid ActionsConfig actionConfig) {
        String username = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        if (username == null)
            return null;
        ApplicationUser myUSer = applicationUserRepository.findByUsername(username);
        if (myUSer == null)
            return null;
        actionConfig.setUser(myUSer);
        applicationReactionsConfigRepository.save(actionConfig.getReaction());
        applicationActionsConfigRepository.save(actionConfig);
        actionConfig.setUser(null);
        return actionConfig;
    }
}
