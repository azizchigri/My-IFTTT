package com.area.server.components.services.service;

import com.area.server.components.actions.model.ActionsConfig;
import com.area.server.components.actions.model.DbMap;
import com.area.server.components.actions.repository.ApplicationActionsConfigRepository;
import com.area.server.components.actions.repository.DbMapRepository;
import com.area.server.components.reactions.model.ReactionsConfig;
import com.area.server.components.user.model.ApplicationUser;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * The type Youtube service.
 */
@Service
public class YoutubeService {

    /**
     * The Db map repository.
     */
    @Autowired
    DbMapRepository dbMapRepository;

    /**
     * The Application actions config repository.
     */
    @Autowired
    ApplicationActionsConfigRepository applicationActionsConfigRepository;

    private JSONObject getChannelInfo(ActionsConfig actionsConfig) {
        DbMap channelId = DbMap.dbToMap(actionsConfig.getParameters()).get("channelId");
        String url = "https://www.googleapis.com/youtube/v3/channels?part=snippet,contentDetails,statistics&id=" + channelId.getValue();

        RestTemplate rest = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(actionsConfig.getUser().getYoutubeToken());
        ResponseEntity<String> resp = rest.exchange(
                url, HttpMethod.GET, new HttpEntity(headers), String.class);
        JSONObject response = new JSONObject(resp.getBody());
        JSONObject channel = response.getJSONArray("items").getJSONObject(0);
        return channel;
    }

    /**
     * Yt new video on channel boolean.
     *
     * @param actionsConfig the actions config
     * @return the boolean
     */
    public boolean Yt_new_video_on_channel(ActionsConfig actionsConfig) {
        boolean res;
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ssZ");
        DbMap channelId = DbMap.dbToMap(actionsConfig.getParameters()).get("channelId");
        String url = "https://www.googleapis.com/youtube/v3/activities?part=snippet,contentDetails&maxResults=1&channelId=" + channelId.getValue();

        RestTemplate rest = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(actionsConfig.getUser().getYoutubeToken());
        ResponseEntity<String> resp = rest.exchange(
                url, HttpMethod.GET, new HttpEntity(headers), String.class);
        JSONObject response = new JSONObject(resp.getBody());
        JSONObject video = response.getJSONArray("items").getJSONObject(0);
        DbMap dateDb = DbMap.dbToMap(actionsConfig.getParameters()).get("date");
        try {
            String newDate = video.getJSONObject("snippet").getString("publishedAt").replace(".000Z", "+0100");
            Date date = df.parse(newDate);
            res = df.parse(dateDb.getValue()).before(date);
            if (res) {
                dateDb.setValue(df.format(date));
                dbMapRepository.save(dateDb);
            }
        } catch (Exception e) {
            return false;
        }
        return res;
    }

    /**
     * Yt subscribers number boolean.
     *
     * @param actionsConfig the actions config
     * @return the boolean
     */
    public boolean Yt_subscribers_number(ActionsConfig actionsConfig) {
        boolean res = false;
        JSONObject channel = getChannelInfo(actionsConfig);
        DbMap limit = DbMap.dbToMap(actionsConfig.getParameters()).get("limit");
        try {
            Long limits = Long.parseLong(limit.getValue());
            if (Long.parseLong(channel.getJSONObject("statistics").getString("subscriberCount")) >= limits) {
                res = true;
                // call reaction
                applicationActionsConfigRepository.delete(actionsConfig);
            }
        } catch (Exception e) {
            return false;
        }
        return res;
    }

    /**
     * Yt view number boolean.
     *
     * @param actionsConfig the actions config
     * @return the boolean
     */
    public boolean Yt_view_number(ActionsConfig actionsConfig) {
        boolean res = false;
        JSONObject channel = getChannelInfo(actionsConfig);
        DbMap limit = DbMap.dbToMap(actionsConfig.getParameters()).get("limit");
        try {
            Long limits = Long.parseLong(limit.getValue());
            if (Long.parseLong(channel.getJSONObject("statistics").getString("viewCount")) >= limits) {
                res = true;
                // call reaction
                applicationActionsConfigRepository.delete(actionsConfig);
            }
        } catch (Exception e) {
            return false;
        }
        return res;
    }

    /**
     * Yt upload number boolean.
     *
     * @param actionsConfig the actions config
     * @return the boolean
     */
    public boolean Yt_upload_number(ActionsConfig actionsConfig) {
        boolean res = false;
        JSONObject channel = getChannelInfo(actionsConfig);
        DbMap limit = DbMap.dbToMap(actionsConfig.getParameters()).get("limit");
        try {
            Long limits = Long.parseLong(limit.getValue());
            if (Long.parseLong(channel.getJSONObject("statistics").getString("videoCount")) >= limits) {
                res = true;
                // call reaction
                applicationActionsConfigRepository.delete(actionsConfig);
            }
        } catch (Exception e) {
            return false;
        }
        return res;
    }

    /**
     * Post request response entity.
     *
     * @param url   the url
     * @param body  the body
     * @param token the token
     * @return the response entity
     */
    public static ResponseEntity<String> postRequest(String url, JSONObject body, String token) {
        RestTemplate rest = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);
        return rest.exchange(
                url, HttpMethod.POST, new HttpEntity<>(body.toString(), headers), String.class);
    }

    /**
     * Yt playlist boolean.
     *
     * @param reactionsConfig the reactions config
     * @param user            the user
     * @return the boolean
     */
    public boolean Yt_playlist(ReactionsConfig reactionsConfig, ApplicationUser user) {
        DbMap title = DbMap.dbToMap(reactionsConfig.getParameters()).get("title");
        DbMap privacy = DbMap.dbToMap(reactionsConfig.getParameters()).get("privacy");

        String url = "https://www.googleapis.com/youtube/v3/playlists?part=snippet,status";

        JSONObject body = new JSONObject("{ \"snippet\":{ \"title\": " + title.getValue() + " }, \"status\": { \"privacyStatus\": " + privacy.getValue() + " } }");

        ResponseEntity<String> resp = postRequest(url, body, user.getYoutubeToken());
        return resp.getStatusCode() == HttpStatus.OK;
    }

    /**
     * Yt sub boolean.
     *
     * @param reactionsConfig the reactions config
     * @param user            the user
     * @return the boolean
     */
    public boolean Yt_sub(ReactionsConfig reactionsConfig, ApplicationUser user) {
        DbMap channelId = DbMap.dbToMap(reactionsConfig.getParameters()).get("channelId");

        String url = "https://www.googleapis.com/youtube/v3/subscriptions?part=snippet";

        JSONObject body = new JSONObject("{ \"snippet\": { \"resourceId\": { \"channelId\": "+ channelId.getValue() + ", \"kind\": \"youtube#channel\" } } }");

        ResponseEntity<String> resp = postRequest(url, body, user.getYoutubeToken());
        return resp.getStatusCode() == HttpStatus.OK;
    }

    private ResponseEntity<String> addVideoToPlaylist(String videoId, String token, DbMap playlistId) {
        String url = "https://www.googleapis.com/youtube/v3/playlistItems?part=snippet";

        JSONObject body = new JSONObject("{ \"snippet\": { \"playlistId\": " + playlistId.getValue() +", \"resourceId\": { \"kind\": \"youtube#video\", \"videoId\": " + videoId + " } } }");

        return postRequest(url, body, token);
    }

    /**
     * Yt search and add videos boolean.
     *
     * @param reactionsConfig the reactions config
     * @param user            the user
     * @return the boolean
     */
    public boolean Yt_search_and_add_videos(ReactionsConfig reactionsConfig, ApplicationUser user) {
        DbMap playlistId = DbMap.dbToMap(reactionsConfig.getParameters()).get("playlistId");
        DbMap query = DbMap.dbToMap(reactionsConfig.getParameters()).get("query");
        DbMap number = DbMap.dbToMap(reactionsConfig.getParameters()).get("number");

        String url = "https://www.googleapis.com/youtube/v3/search?part=snippet&maxResults=" + number.getValue() + "&q=" + query.getValue() + "&type=video";

        RestTemplate rest = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(user.getYoutubeToken());
        ResponseEntity<String> resp = rest.exchange(
                url, HttpMethod.GET, new HttpEntity(headers), String.class);
        JSONObject response = new JSONObject(resp.getBody());
        JSONArray array = response.getJSONArray("items");
        for (int i = 0; i < array.length(); i++) {
            JSONObject row = array.getJSONObject(i);
            JSONObject id = row.getJSONObject("id");
            resp = addVideoToPlaylist(id.getString("videoId"), user.getYoutubeToken(), playlistId);
            if (resp.getStatusCode() != HttpStatus.OK)
                return false;
        }
        return true;
    }
}
