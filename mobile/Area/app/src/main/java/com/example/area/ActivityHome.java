package com.example.area;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.area.API.APIArea;
import com.example.area.API.APIClient;
import com.example.area.API.User;
import com.google.android.material.button.MaterialButton;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * ActivityHome, the activity allowing the users to log in the OAUTH2 Services
 */
public class ActivityHome extends AppCompatActivity {

    private static final String TAG = ActivityHome.class.getSimpleName();
    public static final String myPrefs = "Area";
    Drawer appDrawer;
    Integer appDrawerIdentifier = 0;
    retrofit2.Retrofit client = APIClient.getClient();
    List<String> availableServices = new ArrayList<>();
    User currUser = null;

    /**
     * onCreate
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        MaterialButton twitchButton = findViewById(R.id.twitch_signin_button);
        MaterialButton spotifyButton = findViewById(R.id.spotify_signin_button);
        MaterialButton gmailButton = findViewById(R.id.gmail_signin_button);
        MaterialButton youtubeButton = findViewById(R.id.youtube_signin_button);
        MaterialButton redditButton = findViewById(R.id.reddit_signin_button);

        SharedPreferences sharedPreferences = getSharedPreferences(myPrefs, Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("Username", "");

        Toolbar topToolBar = findViewById(R.id.toolbar);

        topToolBar.setTitle("Home");
        topToolBar.setTitleTextColor(getResources().getColor(R.color.colorPrimary, getTheme()));

        twitchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startSignInTwitch(view);
            }
        });

        spotifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startSignInSpotify(view);
            }
        });

        gmailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startSignInGmail(view);
            }
        });

        youtubeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startSignInYoutube(view);
            }
        });

        redditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startSignInReddit(view);
            }
        });

        appDrawer = new DrawerBuilder().withActivity(this)
                .withToolbar(topToolBar)
                .withFullscreen(true)
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        switch (position) {
                            case 0:
                                break;
                            case 1:
                                Intent intent = new Intent(ActivityHome.this, ActivitySettings.class);
                                intent.putExtra("AREA_USERNAME", currUser.getUsername());
                                intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                                startActivity(intent);
                                finish();
                                break;

                            default:
                                intent = new Intent(ActivityHome.this, ActivityRegisterAction.class);
                                intent.putExtra("AREA_USERNAME", currUser.getUsername());
                                intent.putExtra("AREA_ACTION_CATEGORY", availableServices.get(position - 3));
                                intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                                startActivity(intent);
                                finish();
                                break;

                        }
                        return false;
                    }
                }).build();
        appDrawer.addItems(
                new PrimaryDrawerItem().withName("Home").withIdentifier(appDrawerIdentifier++),
                new PrimaryDrawerItem().withName("Settings").withIdentifier(appDrawerIdentifier++),
                new DividerDrawerItem()
        );

        APIArea area = client.create(APIArea.class);
        Call<User> request = area.getUser(username);

        request.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    currUser = response.body();
                    String subServices = currUser.getSubServices();
                    if (subServices != null) {
                        updateDrawerChoices();
                    }
                } else {
                    Toast.makeText(getBaseContext(), R.string.area_error_request, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(getBaseContext(), R.string.area_error_make_request, Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Add the values which need to be saved from the drawer to the bundle
     * @param outState
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState = appDrawer.saveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }

    /**
     * Close the drawer first and if the drawer is closed close the activity
     */
    @Override
    public void onBackPressed() {
        if (appDrawer != null && appDrawer.isDrawerOpen()) {
            appDrawer.closeDrawer();
        } else {
            super.onBackPressed();
        }
    }

    /**
     * onResume, allows to catch the OAUTH2 Callbacks & parse the tokens
     */
    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences sharedPreferences = getSharedPreferences(myPrefs, Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("Username", "");

        APIArea area = client.create(APIArea.class);
        Call<User> request = area.getUser(username);

        request.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    currUser = response.body();
                    String subServices = currUser.getSubServices();
                    if (subServices != null) {
                        updateDrawerChoices();
                    }
                    if(getIntent() !=null && getIntent().getAction() != null && getIntent().getAction().equals(Intent.ACTION_VIEW)) {
                        Uri uri = getIntent().getData();
                        if(uri.getQueryParameter("error") != null) {
                            String error = uri.getQueryParameter("error");
                            Toast.makeText(getBaseContext(), "OAUTH2 Error", Toast.LENGTH_SHORT).show();
                        } else {
                            if (uri.getPath().equals("/area_twitch_authorize_callback")) {
                                String token = uri.getFragment().split("&")[0].split("=")[1];
                                currUser.setTwitchToken(token);
                                updateUser();
                            } else if (uri.getPath().equals("/area_spotify_authorize_callback")) {
                                String token = uri.getFragment().split("&")[0].split("=")[1];
                                currUser.setSpotifyToken(token);
                                updateUser();
                            } else if (uri.getPath().equals("/area_gmail_authorize_callback")) {
                                String token = uri.getFragment().split("&")[0].split("=")[1];
                                currUser.setGmailToken(token);
                                updateUser();
                            } else if (uri.getPath().equals("/area_youtube_authorize_callback")) {
                                String token = uri.getFragment().split("&")[0].split("=")[1];
                                currUser.setYoutubeToken(token);
                                updateUser();
                            } else if (uri.getPath().equals("/area_reddit_authorize_callback")) {
                                String token = uri.getFragment().split("&")[0].split("=")[1];
                                currUser.setRedditToken(token);
                                updateUser();
                            }
                        }
                    }
                } else {
                    Toast.makeText(getBaseContext(), R.string.area_error_request, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(getBaseContext(), R.string.area_error_make_request, Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Clean the previous drawer choices
     * Fill the drawer with new categories
     */
    public void updateDrawerChoices() {
        String subServices = currUser.getSubServices();
        for (int idx = 2 ; idx < appDrawerIdentifier; idx++){
            appDrawer.removeItem(idx);
        }
        appDrawerIdentifier = 2;
        availableServices.clear();
        if (subServices != null) {
            String[] myData = subServices.split(";");
            for (String s : myData) {
                if (s.isEmpty())
                    continue;
                appDrawer.addItem(
                        new PrimaryDrawerItem().withName(s).withIdentifier(appDrawerIdentifier++)
                );
                availableServices.add(s);
            }
        }
    }

    /**
     * Opens a new Intent for the OAUTH2 on the Twitch service
     * @param view
     */
    public void startSignInTwitch(View view) {
        String url = "https://id.twitch.tv/oauth2/authorize"
                + "?client_id=" + "4gcoknpmsa9f9dwj71pro1z7i5xbo3"
                + "&redirect_uri=" + "http%3A%2F%2F127.0.0.1%2Farea_twitch_authorize_callback"
                + "&scope=" + "user:edit"
                + "&response_type=token";
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(intent);
    }

    /**
     * Opens a new Intent for the OAUTH2 on the Spotify service
     * @param view
     */
    public void startSignInSpotify(View view) {
        String url = "https://accounts.spotify.com/authorize"
                + "?client_id=" + "7327e7ce27f844c784f0f23d1688d01d"
                + "&redirect_uri=" + "http%3A%2F%2F127.0.0.1%2Farea_spotify_authorize_callback"
                + "&scope=" + "user-follow-modify playlist-modify-public"
                + "&response_type=token";
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(intent);
    }

    /**
     * Opens a new Intent for the OAUTH2 on the Youtube service
     * @param view
     */
    public void startSignInYoutube(View view) {
        String url = "https://accounts.google.com/o/oauth2/v2/auth"
                + "?client_id=" + "316790949167-05depihif5jong940mpmpkkbdormnphs.apps.googleusercontent.com"
                + "&redirect_uri=" + "http%3A%2F%2F127.0.0.1%2Farea_youtube_authorize_callback"
                + "&scope=" + "https://www.googleapis.com/auth/youtube https://www.googleapis.com/auth/youtube.force-ssl https://www.googleapis.com/auth/youtube.readonly https://www.googleapis.com/auth/youtube.upload https://www.googleapis.com/auth/youtubepartner https://www.googleapis.com/auth/youtubepartner-channel-audit"
                + "&response_type=token";
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(intent);
    }

    /**
     * Opens a new Intent for the OAUTH2 on the Gmail service
     * @param view
     */
    public void startSignInGmail(View view) {
        String url = "https://accounts.google.com/o/oauth2/v2/auth"
                + "?client_id=" + "316790949167-05depihif5jong940mpmpkkbdormnphs.apps.googleusercontent.com"
                + "&redirect_uri=" + "http%3A%2F%2F127.0.0.1%2Farea_gmail_authorize_callback"
                + "&scope=" + "https://mail.google.com/ https://www.googleapis.com/auth/gmail.modify https://www.googleapis.com/auth/gmail.readonly https://www.googleapis.com/auth/gmail.metadata https://www.googleapis.com/auth/gmail.compose https://www.googleapis.com/auth/gmail.send"
                + "&response_type=token";
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(intent);
    }

    /**
     * Opens a new Intent for the OAUTH2 on the Reddit service
     * @param view
     */
    public void startSignInReddit(View view) {
        String url = "https://www.reddit.com/api/v1/authorize"
                + "?client_id=" + "rcWN1Rvr6QrRGQ"
                + "&redirect_uri=" + "http://127.0.0.1/area_reddit_authorize_callback"
                + "&scope=" + "mysubreddits subscribe"
                + "&response_type=token"
                + "&state=Area";
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(intent);
    }

    /**
     * Requests the server and updates the drawer
     */
    public void updateUser() {
        APIArea area = client.create(APIArea.class);
        Call<Void> request = area.UserUpdate(currUser);

        request.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    updateDrawerChoices();
                    Toast.makeText(getBaseContext(), R.string.area_success, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getBaseContext(), R.string.area_error_request, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(getBaseContext(), R.string.area_error_make_request, Toast.LENGTH_SHORT).show();
            }
        });

    }
}