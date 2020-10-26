var subscribed_services = [];

function    updateProfile()
{
    let area_list_services = "";
    let subs = document.getElementById("subscribe-group").getElementsByTagName("input");
    subscribed_services = [];

    for (i = 0 ; i < subs.length ; i++) {
        if (subs[i].checked) {
            if (area_list_services !== "") {
                area_list_services += ";";
            }
            area_list_services += all_services[i].name;
            subscribed_services.push(all_services[i].name);
        }
    }

    $.ajax({
        url: 'http://127.0.0.1:8080/users',
        type: 'PUT',
        data: JSON.stringify({ email: $('#updateEmail').val(), 
            username : $('#updateUsername').val(), 
            firstName : $('#updateFirstname').val(), 
            lastName : $('#updateLastname').val(), 
            password : $('#updatePassword').val(), 
            gmailToken : localStorage.getItem('gmail_token'),
            youtubeToken : localStorage.getItem('youtube_token'),
            spotifyToken : localStorage.getItem('spotify_token'),
            redditToken : localStorage.getItem('reddit_token'),
            twitchToken : localStorage.getItem('twitch_token'),
            subServices : area_list_services }),
        beforeSend: function(xhr){xhr.setRequestHeader('authorization', getCookie('authorization'));},
        contentType: "application/json",
        complete: function(result, status) {
            if (status != 'success') {
                var alert = '<div class="alert alert-success alert-dismissible fade in " >';
                alert += '<a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>';
                alert += '<strong>Error</strong>';
                alert += '</div>';
                $("#profile").append(alert)
            } else {
                var alert = '<div class="alert alert-success alert-dismissible fade in " >';
                alert += '<a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>';
                alert += '<strong>Saved</strong>';
                alert += '</div>';
                $("#profile").append(alert)
                set_services_available();
                refresh_list_act_react();
            }
        }
    });
}
