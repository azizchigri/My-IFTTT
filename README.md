# AREA

Web application that works similar to [IFTTT](https://ifttt.com/). Easy automation for busy people. Area moves info between your web apps automatically, so you can focus on your most important work.

## Services available

### Twitch

|Name|Type|Descriptions|
|:---|:---:|:---|
|**Tw_view_number**| *Action* | The User reached the the number of view
|**Tw_is_affiliate**| *Action* | The User is now affiliate with Twitch
|**Tw_is_partner**| *Action* | The User is now a partner with Twitch
|**Tw_followers_number**| *Action* | The User reached the the number of followers
|**Tw_update_Description**| *Reaction* | Update the User's profile description

### Reddit
Name|Type|Descriptions|
|:---|:---:|:---|
|**Rd_add_friend**| *Reaction* | Add a friend
|**Rd_rm_friend**| *Reaction* | Remove a friend

### Gmail
Name|Type|Descriptions|
|:---|:---:|:---|
|**Gm_rcv_mail**| *Action* | I have received an email
|**Gm_send_mail**| *Reaction* | Send an mail to someone

### Spotify
Name|Type|Descriptions|
|:---|:---:|:---|
|**Sp_artist_follower**| *Action* | An artist Reach the number of followers
|**Sp_follow_playlist**| *Reaction* | Follow a given playlist
|**Sp_unfollow_playlist**| *Reaction* | Unfollow a given playlist
|**Sp_follow_artist**| *Reaction* | Follow a given artist

### Timer
Name|Type|Descriptions|
|:---|:---:|:---|
|**Ti_timeout**| *Action* | The selected time is over
|**Ti_frequency**| *Action* | Make an action every frequency (frequency is an Integer and will be multiplied by 30 sec)

### Weather
Name|Type|Descriptions|
|:---|:---:|:---|
|**We_temperature_reached**| *Action* | The selected temperature has been reached
|**We_bellow_temperature**| *Action* | The city temperature is bellow the selected temperature


### Youtube
Name|Type|Descriptions|
|:---|:---:|:---|
|**Yt_new_video_on_channel**| *Action* | The channel owner has posted a new video
|**Yt_subscribers_number**| *Action* | The channel reached the selected number of subscribers
|**Yt_view_number**| *Action* |The channel reached the selected number of views
|**Yt_upload_number**| *Action* | The channel reached the selected number of video uploaded
|**Yt_sub**| *Reaction* | Subscript to a youtube chanel
|**Yt_playlist**| *Reaction* | SCreate a new playlist
|**Yt_search_and_add_videos**| *Reaction* | Search a videos and add the results in a playlist

### Total
|Type|Number
|---:|:---|
|Services| 7
|Actions| 16
|Reactions| 8
|**Total**| 24 Actions/Reactions

## Getting Started

To build and use this project, you'll have to run two commands : 
1. `docker-compose build`
2. `docker-compose up`

### Prerequisites

To run this project, you'll need to install the following software :
* Docker : [link to Docker](https://docs.docker.com/install/)

## Built With

* [Gradle](https://gradle.org/) - Dependency Management
* [Docker](https://docs.docker.com/) - Create container

## Authors

* **Aziz Chigri** - [Linkedin](www.linkedin.com/in/aziz-chigri-198709140)
* **Benjamin Chadelaud** - [Linkedin](www.linkedin.com/in/benjamin-chadelaud-b15652144)
* **Thibaud Salsa** - 
* **Julien Chassard** -
* **Robin Chatain** - 

See also the [activity](https://gitlab.com/achigri/dev_area/activity) of this project.