package com.area.server.components.services.service;

import com.area.server.components.actions.model.ActionsConfig;
import com.area.server.components.actions.model.DbMap;
import com.area.server.components.actions.repository.ApplicationActionsConfigRepository;
import com.area.server.components.actions.repository.DbMapRepository;
import com.area.server.components.reactions.model.ReactionsConfig;
import com.area.server.components.user.model.ApplicationUser;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

/**
 * The type Spotify service.
 */
@Service
public class SpotifyService {
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

    /**
     * Sp artist followers boolean.
     *
     * @param actionsConfig the actions config
     * @return the boolean
     */
    public boolean Sp_artist_followers(ActionsConfig actionsConfig) {

        String urlArtist;
        DbMap artistDb = DbMap.dbToMap(actionsConfig.getParameters()).get("ArtistSpotifyId");
        String artistId = artistDb.getValue();
        DbMap followersNumberDb = DbMap.dbToMap(actionsConfig.getParameters()).get("followersNumber");
        Integer followersNumber = Integer.parseInt(followersNumberDb.getValue());
        urlArtist = "https://api.spotify.com/v1/artists/" + artistId;
        RestTemplate rest = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(actionsConfig.getUser().getSpotifyToken());
        ResponseEntity<String> resp = rest.exchange(
                urlArtist, HttpMethod.GET, new HttpEntity(headers), String.class);
        JSONObject object = new JSONObject(resp.getBody());
        JSONObject follows = ((JSONObject)object.get("followers"));
        Integer artistFollerwersNumber = Integer.parseInt(follows.get("total").toString());
        if (followersNumber <= artistFollerwersNumber) {
            //Execute reaction
            System.out.println("Followers number Reached, reactions Executed");
            applicationActionsConfigRepository.delete(actionsConfig);
            return true;
        }
        return false;
    }

    /**
     * Sp follow artist boolean.
     *
     * @param reactionsConfig the reactions config
     * @param user            the user
     * @return the boolean
     */
    public boolean Sp_follow_artist(ReactionsConfig reactionsConfig, ApplicationUser user) {
        DbMap artistDb = DbMap.dbToMap(reactionsConfig.getParameters()).get("ArtistSpotifyId");
        String artistId = artistDb.getValue();
        String url = "https://api.spotify.com/v1/me/following?type=artist&ids=" + artistId;
        RestTemplate rest = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setBearerAuth(user.getSpotifyToken());
        ResponseEntity<String> resp = rest.exchange(
                url, HttpMethod.PUT, new HttpEntity(headers), String.class);
        if (resp.getStatusCode().toString().equals("204 NO_CONTENT")) {
            return true;
        }
        return false;
    }

    /**
     * Sp follow playlist boolean.
     *
     * @param reactionsConfig the reactions config
     * @param user            the user
     * @return the boolean
     */
    public boolean Sp_follow_playlist(ReactionsConfig reactionsConfig, ApplicationUser user) {
        DbMap artistDb = DbMap.dbToMap(reactionsConfig.getParameters()).get("playlistSpotifyId");
        String artistId = artistDb.getValue();
        String url = "https://api.spotify.com/v1/playlists/" + artistId + "/followers";
        RestTemplate rest = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setBearerAuth(user.getSpotifyToken());
        ResponseEntity<String> resp = rest.exchange(
                url, HttpMethod.PUT, new HttpEntity("{public: false}", headers), String.class);
        if (resp.getStatusCode().toString().equals("200 OK")) {
            return true;
        }
        return false;
    }

    /**
     * Sp unfollow playlist boolean.
     *
     * @param reactionsConfig the reactions config
     * @param user            the user
     * @return the boolean
     */
    public boolean Sp_unfollow_playlist(ReactionsConfig reactionsConfig, ApplicationUser user) {
        DbMap artistDb = DbMap.dbToMap(reactionsConfig.getParameters()).get("playlistSpotifyId");
        String artistId = artistDb.getValue();
        String url = "https://api.spotify.com/v1/playlists/" + artistId + "/followers";
        RestTemplate rest = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setBearerAuth(user.getSpotifyToken());
        ResponseEntity<String> resp = rest.exchange(
                url, HttpMethod.DELETE, new HttpEntity(headers), String.class);
        if (resp.getStatusCode().toString().equals("200 OK")) {
            return true;
        }
        return false;
    }


}
