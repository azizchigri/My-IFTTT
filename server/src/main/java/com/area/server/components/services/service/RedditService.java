package com.area.server.components.services.service;

import com.area.server.components.actions.model.DbMap;
import com.area.server.components.actions.repository.DbMapRepository;
import com.area.server.components.reactions.model.ReactionsConfig;
import com.area.server.components.user.model.ApplicationUser;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * The type Reddit service.
 */
@Service
public class RedditService {

    /**
     * The Db map repository.
     */
    @Autowired
    DbMapRepository dbMapRepository;

    /**
     * Post request response entity.
     *
     * @param url   the url
     * @param body  the body
     * @param token the token
     * @return the response entity
     */
    public static ResponseEntity<String> putRequest(String url, JSONObject body, String token) {
        RestTemplate rest = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("User-Agent", "AREA epitech");
        headers.setBearerAuth(token);
        return rest.exchange(
                url, HttpMethod.PUT, new HttpEntity<>(body.toString(), headers), String.class);
    }

    /**
     * Add friend on reddit
     * @param reactionsConfig
     * @param user
     * @return
     */
    public boolean Rd_add_friend(ReactionsConfig reactionsConfig, ApplicationUser user) {
        String friendName = DbMap.dbToMap(reactionsConfig.getParameters()).get("friendName").getValue();
        String url = "https://oauth.reddit.com/api/v1/me/friends/" + friendName;

        JSONObject json = new JSONObject();
        json.put("name", friendName);
        json.put("note", DbMap.dbToMap(reactionsConfig.getParameters()).get("note").getValue());
        JSONObject body = new JSONObject();
        body.put("json", json);
        ResponseEntity<String> resp = putRequest(url, body, user.getRedditToken());
        return resp.getStatusCode() == HttpStatus.OK;
    }

    /**
     * Delete friend on reddit
     * @param reactionsConfig
     * @param user
     * @return
     */
    public boolean Rd_rm_friend(ReactionsConfig reactionsConfig, ApplicationUser user) {
        String friendName = DbMap.dbToMap(reactionsConfig.getParameters()).get("friendName").getValue();
        String url = "https://oauth.reddit.com/api/v1/me/friends/" + friendName;

        RestTemplate rest = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("User-Agent", "AREA epitech");
        headers.setBearerAuth(user.getRedditToken());
        ResponseEntity<String> resp = rest.exchange(
                url, HttpMethod.DELETE, new HttpEntity<>(headers), String.class);
        return resp.getStatusCode() == HttpStatus.OK;
    }
}
