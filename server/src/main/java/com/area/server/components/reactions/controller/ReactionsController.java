package com.area.server.components.reactions.controller;

import com.area.server.components.reactions.model.Reactions;
import com.area.server.components.reactions.repository.ApplicationReactionsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * The type Reactions controller.
 */
@RestController
@RequestMapping("/reactions")
public class ReactionsController {
    /**
     * The Application reactions repository.
     */
    @Autowired
    ApplicationReactionsRepository applicationReactionsRepository;

    /**
     * Get actions list.
     *
     * @return the list
     */
    @GetMapping
    public List<Reactions> getActions(){
        return applicationReactionsRepository.findAll();
    }

    /**
     * Gets gmail send msg.
     *
     * @return the gmail send msg
     */
    @GetMapping("/Gm_send_mail")
    public String getGmailSendMsg() {
        return "{\n" +
                "\"service\":\"Gmail\",\n" +
                "\"action\":\"Gm_send_mail\",\n" +
                "\"params\":[\n" +
                "{\n" +
                "\"name\":\"fromName\",\n" +
                "\"type\":\"String\"\n" +
                "},\n" +
                "{\n" +
                "\"name\":\"fromMail\",\n" +
                "\"type\":\"String\"\n" +
                "},\n" +
                "{\n" +
                "\"name\":\"toName\",\n" +
                "\"type\":\"String\"\n" +
                "},\n" +
                "{\n" +
                "\"name\":\"toMail\",\n" +
                "\"type\":\"String\"\n" +
                "},\n" +
                "{\n" +
                "\"name\":\"subject\",\n" +
                "\"type\":\"String\"\n" +
                "},\n" +
                "{\n" +
                "\"name\":\"content\",\n" +
                "\"type\":\"String\"\n" +
                "}\n" +
                "]\n" +
                "}";
    }

    /**
     * Gets sp follow playlist.
     *
     * @return the sp follow playlist
     */
    @GetMapping("/Sp_follow_playlist")
    public String getSpFollowPlaylist() {
        return "{\n" +
                "\t\"service\": \"Spotify\",\n" +
                "\t\"action\": \"Sp_follow_playlist\",\n" +
                "\t\"params\": [{\"name\": \"playlistSpotifyId\", \"type\" : \"String\"}]\n" +
                "}";
    }

    /**
     * Gets sp follow artist.
     *
     * @return the sp follow artist
     */
    @GetMapping("/Sp_follow_artist")
    public String getSpFollowArtist() {
        return "{\n" +
                "\t\"service\": \"Spotify\",\n" +
                "\t\"action\": \"Sp_follow_artist\",\n" +
                "\t\"params\": [{\"name\": \"ArtistSpotifyId\", \"type\" : \"String\"}]\n" +
                "}";
    }

    /**
     * Gets sp unfollow playlist.
     *
     * @return the sp unfollow playlist
     */
    @GetMapping("/Sp_unfollow_playlist")
    public String getSpUnfollowPlaylist() {
        return "{\n" +
                "\t\"service\": \"Spotify\",\n" +
                "\t\"action\": \"Sp_unfollow_playlist\",\n" +
                "\t\"params\": [{\"name\": \"playlistSpotifyId\", \"type\" : \"String\"}]\n" +
                "}";
    }

    /**
     * Gets yt subs.
     *
     * @return the yt subs
     */
    @GetMapping("/Yt_sub")
    public String getYtSubs() {
        return "{\n" +
                "\t\"service\": \"Youtube\",\n" +
                "\t\"action\": \"Yt_sub\",\n" +
                "\t\"params\": [{\"name\": \"channelId\", \"type\" : \"String\"}]\n" +
                "}";
    }

    /**
     * Gets create playlist.
     *
     * @return the create playlist
     */
    @GetMapping("/Yt_playlist")
    public String getCreatePlaylist() {
        return "{\n" +
                "\"service\" : \"Youtube\",\n" +
                "\"action\" : \"Yt_playlist\",\n" +
                "\"params\" : [{\"name\" : \"title\", \"type\" : \"String\"}, {\"name\" : \"privacy\", \"type\" : \"Privacy\"}]\n" +
                "}";
    }

    /**
     * Gets search and create playlist.
     *
     * @return the search and create playlist
     */
    @GetMapping("/Yt_search_and_add_videos")
    public String getSearchAndCreatePlaylist() {
        return "{\n" +
                "\"service\" : \"Youtube\",\n" +
                "\"action\" : \"Yt_search_and_add_videos\",\n" +
                "\"params\" : [{\"name\" : \"playlistId\", \"type\" : \"String\"}, {\"name\" : \"query \", \"type\" : \"String\"}, {\"name\" : \"number \", \"type\" : \"Integer\"}]\n" +
                "}";
    }

    @GetMapping("/Tw_update_Description")
    public String getTw_update_Description() {
        return "{\n" +
                "\"service\" : \"Twitch\",\n" +
                "\"action\" : \"Tw_update_Description\",\n" +
                "\"params\" : [{\"name\" : \"description\", \"type\" : \"String\"}]\n" +
                "}";
    }

    @GetMapping("/Rd_add_friend")
    public String getRd_add_friend() {
        return "{\n" +
                "\"service\" : \"Reddit\",\n" +
                "\"action\" : \"Rd_add_friend\",\n" +
                "\"params\" : [{\"name\" : \"friendName\", \"type\" : \"String\"}, {\"name\" : \"note\", \"type\" : \"String\"}]\n" +
                "}";
    }

    @GetMapping("/Rd_rm_friend")
    public String getRd_rm_friend() {
        return "{\n" +
                "\"service\" : \"Reddit\",\n" +
                "\"action\" : \"Rd_rm_friend\",\n" +
                "\"params\" : [{\"name\" : \"friendName\", \"type\" : \"String\"}]\n" +
                "}";
    }
}
