package com.example.area;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;

import com.example.area.API.APIArea;
import com.example.area.API.APIClient;
import com.example.area.API.User;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
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
 * ActivitySettings, the activity allowing the users to set their settings
 */
public class ActivitySettings extends AppCompatActivity {

    private static final String TAG = ActivitySettings.class.getSimpleName();
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
        setContentView(R.layout.activity_settings);
        final TextInputEditText passwordEditText = findViewById(R.id.password_edit_text);
        final TextInputEditText emailEditText = findViewById(R.id.email_edit_text);
        final TextInputEditText firstNameEditText = findViewById(R.id.firstname_edit_text);
        final TextInputEditText lastNameEditText = findViewById(R.id.lastname_edit_text);
        final TextInputEditText usernameEditText = findViewById(R.id.username_edit_text);
        final CheckBox redditCheckBox = findViewById(R.id.reddit_checkbox);
        final CheckBox gmailCheckBox = findViewById(R.id.gmail_checkbox);
        final CheckBox weatherCheckBox = findViewById(R.id.meteo_checkbox);
        final CheckBox spotifyCheckBox = findViewById(R.id.spotify_checkbox);
        final CheckBox timerCheckBox = findViewById(R.id.timer_checkbox);
        final CheckBox twitchCheckBox = findViewById(R.id.twitch_checkbox);
        final CheckBox youtubeCheckBox = findViewById(R.id.youtube_checkbox);

        MaterialButton updateButton = findViewById(R.id.update_button);
        Toolbar topToolBar = findViewById(R.id.toolbar);
        String username = getIntent().getStringExtra("AREA_USERNAME");

        topToolBar.setTitle("Settings");
        topToolBar.setTitleTextColor(getResources().getColor(R.color.colorPrimary, getTheme()));

        appDrawer = new DrawerBuilder().withActivity(this)
                .withToolbar(topToolBar)
                .withFullscreen(true)
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        Intent intent = null;
                        switch (position) {
                            case 0:
                                intent = new Intent(ActivitySettings.this, ActivityHome.class);
                                intent.putExtra("AREA_USERNAME", currUser.getUsername());
                                intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                                startActivity(intent);
                                finish();
                                break;
                            case 1:
                                break;

                            default:
                                intent = new Intent(ActivitySettings.this, ActivityRegisterAction.class);
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
                    emailEditText.setText(currUser.getEmail());
                    usernameEditText.setText(currUser.getUsername());
                    lastNameEditText.setText(currUser.getLastName());
                    firstNameEditText.setText(currUser.getFirstName());
                    String subServices = currUser.getSubServices();
                    if (subServices != null) {
                        String[] myData = subServices.split(";");
                        for (String s : myData) {
                            if (s.equals("Youtube"))
                                youtubeCheckBox.setChecked(true);
                            else if (s.equals("Gmail"))
                                gmailCheckBox.setChecked(true);
                            else if (s.equals("Reddit"))
                                redditCheckBox.setChecked(true);
                            else if (s.equals("Weather"))
                                weatherCheckBox.setChecked(true);
                            else if (s.equals("Spotify"))
                                spotifyCheckBox.setChecked(true);
                            else if (s.equals("Timer"))
                                timerCheckBox.setChecked(true);
                            else if (s.equals("Twitch"))
                                twitchCheckBox.setChecked(true);
                        }
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

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User newDataUser = new User();

                newDataUser.setRedditToken(currUser.getRedditToken());
                newDataUser.setGmailToken(currUser.getGmailToken());
                newDataUser.setSpotifyToken(currUser.getSpotifyToken());
                newDataUser.setTwitchToken(currUser.getTwitchToken());
                newDataUser.setYoutubeToken(currUser.getYoutubeToken());
                newDataUser.setUsername(currUser.getUsername());

                newDataUser.setEmail(emailEditText.getText().toString());
                newDataUser.setFirstName(firstNameEditText.getText().toString());
                newDataUser.setLastName(lastNameEditText.getText().toString());
                if (!passwordEditText.getText().toString().equals(""))
                    newDataUser.setPassword(passwordEditText.getText().toString());
                String subServices = "";
                if (gmailCheckBox.isChecked())
                    subServices += "Gmail;";
                if (redditCheckBox.isChecked())
                    subServices += "Reddit;";
                if (weatherCheckBox.isChecked())
                    subServices += "Weather;";
                if (spotifyCheckBox.isChecked())
                    subServices += "Spotify;";
                if (timerCheckBox.isChecked())
                    subServices += "Timer;";
                if (twitchCheckBox.isChecked())
                    subServices += "Twitch;";
                if (youtubeCheckBox.isChecked())
                    subServices += "Youtube;";
                newDataUser.setSubServices(subServices);

                APIArea area = client.create(APIArea.class);
                Call<Void> request = area.UserUpdate(newDataUser);

                request.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.isSuccessful()) {
                            currUser = newDataUser;
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
}