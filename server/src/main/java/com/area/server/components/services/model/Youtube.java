package com.area.server.components.services.model;

import javax.persistence.Entity;

/**
 * The type Youtube.
 */
@Entity
public class Youtube extends Services {

    /**
     * Instantiates a new Youtube.
     */
    public Youtube() {
		super();
		this.setName("Youtube");
		createAction("Yt_new_video_on_channel", "The channel owner has posted a new video");
		createAction("Yt_subscribers_number", "The channel reached the selected number of subscribers");
		createAction("Yt_view_number", "The channel reached the selected number of views");
		createAction("Yt_upload_number", "The channel reached the selected number of video uploaded");
		createReaction("Yt_playlist", "Create a new playlist");
		createReaction("Yt_sub", "Subscribe to a youtube chanel");
		createReaction("Yt_search_and_add_videos", "Search a videos and add the results in a playlist");
	}
}