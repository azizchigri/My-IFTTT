package com.area.server.components.services.service;

import com.area.server.components.actions.model.ActionsConfig;
import com.area.server.components.actions.repository.DbMapRepository;
import com.area.server.components.reactions.model.ReactionsConfig;
import com.area.server.components.user.model.ApplicationUser;
import com.area.server.components.user.repository.ApplicationUserRepository;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

/**
 * The type Call services.
 */
@Service
public class CallServices {

    @Autowired
    TwitchService twitchService;

    @Autowired
    RedditService redditService;

    @Autowired
    SpotifyService spotifyService;

    @Autowired
    GmailService gmailService;

    @Autowired
    TimerService timerService;

    @Autowired
    WeatherService weatherService;

    @Autowired
    YoutubeService youtubeService;

    @Autowired
    DbMapRepository dbMapRepository;

    @Autowired
    ApplicationUserRepository applicationUserRepository;
    /**
     * Call action boolean.
     *
     * @param actionsConfig the actions config
     * @return the boolean
     */
    public boolean callAction(ActionsConfig actionsConfig) {
        CheckTokenValidity(actionsConfig.getUser());
        boolean ret = callFunction(actionsConfig.getName(), actionsConfig);
        if (ret)
            ret = callReaction(actionsConfig);
        else
            return false;
        return ret;
    }

    private boolean callReaction(ActionsConfig actionsConfig) {
        ReactionsConfig reactionsConfig = actionsConfig.getReaction();
        return callFunction(reactionsConfig.getName(), actionsConfig);
    }

    boolean callFunction(String functionName, ActionsConfig actionsConfig) {
        boolean ret = false;
        switch (functionName) {
            case "Tw_followers_number":
                ret = twitchService.Tw_followers_number(actionsConfig);
                break;
            case "Tw_is_affiliate":
                ret = twitchService.Tw_is_affiliate(actionsConfig);
                break;
            case "Tw_is_partner":
                ret = twitchService.Tw_is_partner(actionsConfig);
                break;
            case "Tw_view_number":
                ret = twitchService.Tw_view_number(actionsConfig);
                break;
            case "Tw_update_Description":
                ret = twitchService.Tw_update_Description(actionsConfig.getReaction(), actionsConfig.getUser());
                break;
            case "Rd_add_friend":
                ret = redditService.Rd_add_friend(actionsConfig.getReaction(), actionsConfig.getUser());
                break;
            case "Rd_rm_friend":
                ret = redditService.Rd_rm_friend(actionsConfig.getReaction(), actionsConfig.getUser());
                break;
            case "Gm_rcv_mail":
                ret = gmailService.Gm_rcv_mail(actionsConfig);
                break;
            case "Gm_send_mail":
                ret = gmailService.Gm_send_mail(actionsConfig.getReaction(), actionsConfig.getUser());
                break;
            case "Sp_artist_follower":
                ret = spotifyService.Sp_artist_followers(actionsConfig);
                break;
            case "Sp_unfollow_playlist":
                ret = spotifyService.Sp_unfollow_playlist(actionsConfig.getReaction(), actionsConfig.getUser());
                break;
            case "Sp_follow_playlist":
                ret = spotifyService.Sp_follow_playlist(actionsConfig.getReaction(), actionsConfig.getUser());
                break;
            case "Sp_follow_artist":
                ret = spotifyService.Sp_follow_artist(actionsConfig.getReaction(), actionsConfig.getUser());
                break;
            case "Ti_timeout":
                ret = timerService.Ti_timeout(actionsConfig);
                break;
            case "Ti_frequency":
                ret = timerService.Ti_frequency(actionsConfig);
                break;
            case "We_bellow_temperature":
                ret = weatherService.We_bellow_temperature(actionsConfig);
                break;
            case "We_temperature_reached":
                ret = weatherService.We_temperature_reached(actionsConfig);
                break;
            case "Yt_new_video_on_channel":
                ret = youtubeService.Yt_new_video_on_channel(actionsConfig);
                break;
            case "Yt_upload_number":
                ret = youtubeService.Yt_upload_number(actionsConfig);
                break;
            case "Yt_view_number":
                ret = youtubeService.Yt_view_number(actionsConfig);
                break;
            case "Yt_subscribers_number":
                ret = youtubeService.Yt_subscribers_number(actionsConfig);
                break;
            case "Yt_search_and_add_videos":
                ret = youtubeService.Yt_search_and_add_videos(actionsConfig.getReaction(), actionsConfig.getUser());
                break;
            case "Yt_sub":
                ret = youtubeService.Yt_sub(actionsConfig.getReaction(), actionsConfig.getUser());
                break;
            case "Yt_playlist":
                ret = youtubeService.Yt_playlist(actionsConfig.getReaction(), actionsConfig.getUser());
                break;
        }
        return ret;
    }

    /**
     * Get request response entity.
     *
     * @param url   the url
     * @param token the token
     * @return the response entity
     */
    public static ResponseEntity<String> getRequest(String url, String token) {
        RestTemplate rest = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);
        headers.set("User-Agent", "AREA epitech");
        return rest.exchange(
                url, HttpMethod.GET, new HttpEntity<>(headers), String.class);
    }

    private void RemoveSub(ApplicationUser user, String serviceName){
        String [] arrOfStr = user.getSubServices().split(";");
        List<String> dan = Arrays.asList(arrOfStr);
        if(!dan.contains("serviceName")){
            dan.remove("serviceName");
        }
        user.setSubServices(String.join(";", dan));
        applicationUserRepository.save(user);
    }

    public boolean CheckTokenValidity(ApplicationUser user){
        boolean res = true;
        if (user.getSpotifyToken() != null) {
            try {
                ResponseEntity<String> resp = getRequest("https://api.spotify.com/v1/me", user.getSpotifyToken());
                if (resp.getStatusCode() != HttpStatus.OK)
                {
                    user.setSpotifyToken(null);
                    RemoveSub(user, "Spotify");
                }
            } catch (Exception e){
                user.setSpotifyToken(null);
                RemoveSub(user, "Spotify");
            }
        }

        if (user.getTwitchToken() != null) {
            try {
                ResponseEntity<String> resp = getRequest("https://api.twitch.tv/helix/users", user.getTwitchToken());
                if (resp.getStatusCode() != HttpStatus.OK)
                {
                    user.setTwitchToken(null);
                    RemoveSub(user, "Twitch");
                }
            } catch (Exception e){
                user.setTwitchToken(null);
                RemoveSub(user, "Twitch");
            }
        }
/*
        if (user.getYoutubeToken() != null) {
            try {
                ResponseEntity<String> resp = getRequest("https://api.twitch.tv/helix/users", user.getYoutubeToken());
                if (resp.getStatusCode() == HttpStatus.UNAUTHORIZED)
                {
                    user.setYoutubeToken(null);
                    RemoveSub(user, "Youtube");
                }
            } catch (Exception e){
                user.setYoutubeToken(null);
                RemoveSub(user, "Youtube");
            }
        } */
        if (user.getGmailToken() != null) {
            try {
                ResponseEntity<String> resp = getRequest("https://www.googleapis.com/gmail/v1/users/me/profile", user.getGmailToken());
                if (resp.getStatusCode() != HttpStatus.OK)
                {
                    user.setGmailToken(null);
                    RemoveSub(user, "Gmail");
                }
            } catch (Exception e){
                user.setGmailToken(null);
                RemoveSub(user, "Gmail");
            }
        }

        if (user.getRedditToken() != null) {
            try {
                ResponseEntity<String> resp = getRequest("https://oauth.reddit.com/api/v1/me/karma", user.getRedditToken());
                if (resp.getStatusCode() != HttpStatus.OK)
                {
                    user.setRedditToken(null);
                    RemoveSub(user, "Reddit");
                }
            } catch (Exception e){
                user.setRedditToken(null);
                RemoveSub(user, "Reddit");
            }
        }
        return res;
    }
}
