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

/**
 * The type Twitch service.
 */
@Service
public class TwitchService {
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

    public boolean Tw_view_number(ActionsConfig actionsConfig) {
        String url = "https://api.twitch.tv/helix/users";

        RestTemplate rest = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(actionsConfig.getUser().getTwitchToken());
        ResponseEntity<String> resp = rest.exchange(
                url, HttpMethod.GET, new HttpEntity(headers), String.class);
        JSONObject response = new JSONObject(resp.getBody());
        try {
            DbMap limitDb = DbMap.dbToMap(actionsConfig.getParameters()).get("limit");
            long limits = Long.parseLong(limitDb.getValue());
            JSONArray dataJsonArray = response.getJSONArray("data");
            JSONObject dataJson = (JSONObject) dataJsonArray.get(0);
            long limit = dataJson.getLong("view_count");
            if (limits <= limit) {
                applicationActionsConfigRepository.delete(actionsConfig);
                return true;
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean Tw_is_affiliate(ActionsConfig actionsConfig) {
        String url = "https://api.twitch.tv/helix/users";

        RestTemplate rest = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(actionsConfig.getUser().getTwitchToken());
        ResponseEntity<String> resp = rest.exchange(
                url, HttpMethod.GET, new HttpEntity(headers), String.class);
        JSONObject response = new JSONObject(resp.getBody());
        try {
            JSONArray dataJsonArray = response.getJSONArray("data");
            JSONObject dataJson = (JSONObject) dataJsonArray.get(0);
            String status = dataJson.getString("broadcaster_type");
            if (status.equals("affiliate")) {
                applicationActionsConfigRepository.delete(actionsConfig);
                return true;
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean Tw_is_partner(ActionsConfig actionsConfig) {
        String url = "https://api.twitch.tv/helix/users";

        RestTemplate rest = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(actionsConfig.getUser().getTwitchToken());
        ResponseEntity<String> resp = rest.exchange(
                url, HttpMethod.GET, new HttpEntity(headers), String.class);
        JSONObject response = new JSONObject(resp.getBody());
        try {
            JSONArray dataJsonArray = response.getJSONArray("data");
            JSONObject dataJson = (JSONObject) dataJsonArray.get(0);
            String status = dataJson.getString("broadcaster_type");
            if (status.equals("partner")) {
                applicationActionsConfigRepository.delete(actionsConfig);
                return true;
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean Tw_followers_number(ActionsConfig actionsConfig) {
        RestTemplate rest = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(actionsConfig.getUser().getTwitchToken());
        try {
            DbMap artistDb = DbMap.dbToMap(actionsConfig.getParameters()).get("twitchId");
            String twitchId = artistDb.getValue();
            String url = "https://api.twitch.tv/helix/users/follows?to_id=" + twitchId;
            ResponseEntity<String> resp = rest.exchange(
                    url, HttpMethod.GET, new HttpEntity(headers), String.class);
            JSONObject response = new JSONObject(resp.getBody());
            Integer total = response.getInt("total");
            DbMap limitDb = DbMap.dbToMap(actionsConfig.getParameters()).get("limit");
            long limit = Long.parseLong(limitDb.getValue());
            if (limit <= total) {
                applicationActionsConfigRepository.delete(actionsConfig);
                return true;
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean Tw_update_Description(ReactionsConfig reactionsConfig, ApplicationUser user) {
        RestTemplate rest = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(user.getTwitchToken());
        try {
            DbMap descriptionDb = DbMap.dbToMap(reactionsConfig.getParameters()).get("description");
            String description = descriptionDb.getValue();
            String url = "https://api.twitch.tv/helix/users?description=" + description;
            ResponseEntity<String> resp = rest.exchange(
                    url, HttpMethod.PUT, new HttpEntity(headers), String.class);
            if (resp.getStatusCode().toString().equals("200 OK"))
                return true;
            else
                return false;
        } catch (Exception e) {
            return false;
        }
    }
}
