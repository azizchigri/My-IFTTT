package com.example.area;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.text.InputType;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.area.API.APIArea;
import com.example.area.API.APIClient;
import com.example.area.API.About;
import com.example.area.API.AboutServices;
import com.example.area.API.Action;
import com.example.area.API.NewAction;
import com.example.area.API.NewReaction;
import com.example.area.API.Param;
import com.example.area.API.Reaction;
import com.example.area.API.SpecificAction;

import com.example.area.API.SpecificReaction;

import com.example.area.API.User;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

/**
 * ActivityRegisterAction, the activity allowing the users to create new Action/Reaction couples
 */
public class ActivityRegisterAction extends AppCompatActivity {

    private static final String TAG = ActivityRegisterAction.class.getSimpleName();
    public static final String myPrefs = "Area";

    Drawer appDrawer;
    Integer appDrawerIdentifier = 0;
    List<String> availableServices = new ArrayList<>();

    retrofit2.Retrofit client = APIClient.getClient();
    List<Action> availableActions = new ArrayList<>();
    List<Reaction> availableReactions = new ArrayList<>();
    List<TextInputEditText> textInputEditTextListReactions = new ArrayList<>();
    List<TextInputLayout> textInputLayoutListReactions = new ArrayList<>();
    List<TextInputEditText> textInputEditTextListActions = new ArrayList<>();
    List<TextInputLayout> textInputLayoutListActions = new ArrayList<>();
    SpecificAction specAction = null;
    SpecificReaction specReaction = null;
    User currUser = null;
    String actionCategory = "";

    /**
     * onCreate
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar topToolBar = this.findViewById(R.id.toolbar);

        String username = getIntent().getStringExtra("AREA_USERNAME");
        actionCategory = getIntent().getStringExtra("AREA_ACTION_CATEGORY");

        topToolBar.setTitle(actionCategory);
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
                                intent = new Intent(ActivityRegisterAction.this, ActivityHome.class);
                                intent.putExtra("AREA_USERNAME", currUser.getUsername());
                                intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                                startActivity(intent);
                                finish();
                                break;
                            case 1:
                                intent = new Intent(ActivityRegisterAction.this, ActivitySettings.class);
                                intent.putExtra("AREA_USERNAME", currUser.getUsername());
                                intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                                startActivity(intent);
                                finish();
                                break;

                            default:
                                intent = new Intent(ActivityRegisterAction.this, ActivityRegisterAction.class);
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

        MaterialButton createButton = this.findViewById(R.id.create_button);

        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (specAction != null && specReaction != null)
                    createNewAction();
            }
        });

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

        Call<About> requestAbout = area.getAbout();
        Call<List<Action>> requestActions = area.ListActions();
        Call<List<Reaction>> requestReactions = area.ListReactions();

        requestAbout.enqueue(new Callback<About>() {
            @Override
            public void onResponse(Call<About> call, Response<About> response) {
                if (response.isSuccessful()) {
                    List<AboutServices> services = response.body().getServer().getServices();
                    for (AboutServices service : services) {
                        if (service.getName().toLowerCase().equals(actionCategory.toLowerCase())) {
                            availableActions = service.getActions();
                        }
                        if (isCategoryInSubscription(service.getName())) {
                            for (Reaction r : service.getReactions()) {
                                availableReactions.add(r);
                            }
                        }
                    }
                    Spinner dropdown = findViewById(R.id.spinner);
                    String[] itemsDropdown = new String[availableActions.size()];
                    for (int i = 0; i < availableActions.size(); i++) {
                        itemsDropdown[i] = availableActions.get(i).getDescription();
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout
                            .simple_spinner_dropdown_item, itemsDropdown);
                    dropdown.setAdapter(adapter);
                    dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view,
                                                   int position, long id) {
                            ConstraintLayout cl = findViewById(R.id.constrainlayout);
                            for (Iterator<TextInputEditText> it = textInputEditTextListActions.iterator(); it.hasNext(); ) {
                                TextInputEditText next = it.next();
                                cl.removeView(next);
                                it.remove();
                            }
                            for (Iterator<TextInputLayout> it = textInputLayoutListActions.iterator(); it.hasNext(); ) {
                                TextInputLayout next = it.next();
                                cl.removeView(next);
                                it.remove();
                            }
                            loadSpecificAction(id);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {
                        }
                    });
                    Spinner dropdown2 = findViewById(R.id.spinner2);
                    String[] itemsDropdown2 = new String[availableReactions.size()];
                    for (int i = 0; i < availableReactions.size(); i++) {
                        itemsDropdown2[i] = availableReactions.get(i).getDescription();
                    }
                    ArrayAdapter<String> adapter2 = new ArrayAdapter<>(getApplicationContext(), android.R.layout
                            .simple_spinner_dropdown_item, itemsDropdown2);
                    dropdown2.setAdapter(adapter2);
                    dropdown2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view,
                                                   int position, long id) {
                            ConstraintLayout cl = findViewById(R.id.constrainlayout);
                            for (Iterator<TextInputEditText> it = textInputEditTextListReactions.iterator(); it.hasNext(); ) {
                                TextInputEditText next = it.next();
                                cl.removeView(next);
                                it.remove();
                            }
                            for (Iterator<TextInputLayout> it = textInputLayoutListReactions.iterator(); it.hasNext(); ) {
                                TextInputLayout next = it.next();
                                cl.removeView(next);
                                it.remove();
                            }
                            loadSpecificReaction(id);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {
                        }
                    });

                } else {
                    Toast.makeText(getBaseContext(), R.string.area_error_request, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<About> call, Throwable t) {
                Toast.makeText(getBaseContext(), R.string.area_error_make_request, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void loadSpecificAction(long id) {
        Log.d(TAG, "Got as ID : " + String.valueOf(id));
        APIArea area = client.create(APIArea.class);
        Call<SpecificAction> request = area.getSpecificAction(availableActions.get((int) id).getName());

        request.enqueue(new Callback<SpecificAction>() {
            @Override
            public void onResponse(Call<SpecificAction> call, Response<SpecificAction> response) {
                if (response.isSuccessful()) {
                    specAction = response.body();
                    List<Param> params = specAction.getParams();
                    ConstraintLayout cl = findViewById(R.id.constrainlayout);
                    ConstraintSet set = new ConstraintSet();
                    set.clone(cl);
                    for (int i = 0; i < params.size(); i++) {

                        TextInputLayout newLayout = new TextInputLayout(new ContextThemeWrapper(getBaseContext(), R.style.Theme_Area));
                        newLayout.setId(View.generateViewId());
                        cl.addView(newLayout);

                        set.constrainHeight(newLayout.getId(), ConstraintSet.WRAP_CONTENT);
                        set.constrainWidth(newLayout.getId(), ConstraintSet.WRAP_CONTENT);

                        if (textInputLayoutListActions.size() == 0)
                            set.connect(newLayout.getId(), ConstraintSet.TOP, R.id.spinner, ConstraintSet.BOTTOM, 0);
                        else
                            set.connect(newLayout.getId(), ConstraintSet.TOP, textInputLayoutListActions.get(textInputLayoutListActions.size() - 1).getId(), ConstraintSet.BOTTOM, 100);

                        TextInputEditText myButton = new TextInputEditText(newLayout.getContext());

                        myButton.setHint(params.get(i).getName());
                        myButton.setId(View.generateViewId());

                        String paramType = params.get(i).getType();
                        if (paramType.equals("Date"))
                            myButton.setInputType(InputType.TYPE_CLASS_DATETIME);
                        else if (paramType.equals("Integer") || paramType.equals("Long"))
                            myButton.setInputType(InputType.TYPE_CLASS_NUMBER);
                        else if (paramType.equals("Double"))
                            myButton.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);

                        set.constrainHeight(myButton.getId(), ConstraintSet.WRAP_CONTENT);
                        set.constrainWidth(myButton.getId(), ConstraintSet.WRAP_CONTENT);

                        set.connect(myButton.getId(), ConstraintSet.TOP, newLayout.getId(), ConstraintSet.TOP, 0);

                        cl.addView(myButton);

                        set.applyTo(cl);

                        textInputEditTextListActions.add(myButton);
                        textInputLayoutListActions.add(newLayout);
                    }
                } else {
                    specAction = null;
                    Toast.makeText(getBaseContext(), R.string.area_error_request, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SpecificAction> call, Throwable t) {
                specAction = null;
                Toast.makeText(getBaseContext(), R.string.area_error_make_request, Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void loadSpecificReaction(long id) {
        Log.d(TAG, "Got as ID : " + String.valueOf(id));
        APIArea area = client.create(APIArea.class);
        Call<SpecificReaction> request = area.getSpecificReaction(availableReactions.get((int) id).getName());

        request.enqueue(new Callback<SpecificReaction>() {
            @Override
            public void onResponse(Call<SpecificReaction> call, Response<SpecificReaction> response) {
                if (response.isSuccessful()) {
                    specReaction = response.body();
                    List<Param> params = specReaction.getParams();
                    ConstraintLayout cl = findViewById(R.id.constrainlayout);
                    ConstraintSet set = new ConstraintSet();
                    set.clone(cl);
                    for (int i = 0; i < params.size(); i++) {

                        TextInputLayout newLayout = new TextInputLayout(new ContextThemeWrapper(getBaseContext(), R.style.Theme_Area));
                        newLayout.setId(View.generateViewId());
                        cl.addView(newLayout);

                        set.constrainHeight(newLayout.getId(), ConstraintSet.WRAP_CONTENT);
                        set.constrainWidth(newLayout.getId(), ConstraintSet.WRAP_CONTENT);

                        if (textInputLayoutListReactions.size() == 0)
                            set.connect(newLayout.getId(), ConstraintSet.TOP, R.id.spinner2, ConstraintSet.BOTTOM, 0);
                        else
                            set.connect(newLayout.getId(), ConstraintSet.TOP, textInputLayoutListReactions.get(textInputLayoutListReactions.size() - 1).getId(), ConstraintSet.BOTTOM, 100);

                        TextInputEditText myButton = new TextInputEditText(newLayout.getContext());

                        myButton.setHint(params.get(i).getName());
                        myButton.setId(View.generateViewId());

                        String paramType = params.get(i).getType();
                        if (paramType.equals("Date"))
                            myButton.setInputType(InputType.TYPE_CLASS_DATETIME);
                        else if (paramType.equals("Integer") || paramType.equals("Long"))
                            myButton.setInputType(InputType.TYPE_CLASS_NUMBER);
                        else if (paramType.equals("Double"))
                            myButton.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);

                        set.constrainHeight(myButton.getId(), ConstraintSet.WRAP_CONTENT);
                        set.constrainWidth(myButton.getId(), ConstraintSet.WRAP_CONTENT);

                        set.connect(myButton.getId(), ConstraintSet.TOP, newLayout.getId(), ConstraintSet.TOP, 0);

                        cl.addView(myButton);

                        set.applyTo(cl);

                        textInputEditTextListReactions.add(myButton);
                        textInputLayoutListReactions.add(newLayout);
                    }
                } else {
                    specReaction = null;
                    Toast.makeText(getBaseContext(), R.string.area_error_request, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SpecificReaction> call, Throwable t) {
                specReaction = null;
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
     * Creates a new Action on the server for a given couple of Action/Reaction
     */
    public void createNewAction() {
        NewAction newAction = new NewAction();
        newAction.setName(this.specAction.getAction());
        newAction.setServiceName(this.specAction.getService());

        NewReaction newReaction = new NewReaction();
        newReaction.setName(this.specReaction.getAction());
        newReaction.setServiceName(this.specReaction.getService());

        JsonArray parametersAction = new JsonArray();
        JsonArray parametersReaction = new JsonArray();

        List<Param> paramsAction = this.specAction.getParams();
        List<Param> paramsReaction = this.specReaction.getParams();
        for (int i = 0; i < paramsAction.size(); i++) {
            Param currParam = paramsAction.get(i);
            JsonObject newParameter = new JsonObject();
            newParameter.addProperty("id", i);
            newParameter.addProperty("name", currParam.getName());
            newParameter.addProperty("type", currParam.getType());
            try {
                if (currParam.getType().equals("Long"))
                    newParameter.addProperty("value", Long.parseLong(this.textInputEditTextListActions.get(i).getText().toString()));
                else if (currParam.getType().equals("Integer"))
                    newParameter.addProperty("value", Integer.parseInt(this.textInputEditTextListActions.get(i).getText().toString()));
                else if (currParam.getType().equals("Double"))
                    newParameter.addProperty("value", Double.parseDouble(this.textInputEditTextListActions.get(i).getText().toString()));
                else if (currParam.getType().equals("Date")) {
                    SimpleDateFormat format = new SimpleDateFormat("dd/M/yyyy", Locale.FRANCE);
                    format.setLenient(false);
                    Date date = format.parse(this.textInputEditTextListActions.get(i).getText().toString());
                    String newFormat = "yyyy-MM-dd'T'hh:mm:ssZ";
                    format.applyPattern(newFormat);
                    newParameter.addProperty("value", format.format(date));
                }
                else if (currParam.getType().equals("Privacy")) {
                    String privacyValue = this.textInputEditTextListActions.get(i).getText().toString();
                    if(privacyValue.equals("private") || privacyValue.equals("public") || privacyValue.equals("unlisted"))
                        newParameter.addProperty("value", privacyValue);
                    else
                        throw new Exception("Invalid value for Privacy field");
                }
                else if (currParam.getType().equals("String")) {
                    newParameter.addProperty("value", this.textInputEditTextListActions.get(i).getText().toString());
                }
                else
                    throw new Exception("Unknown data type");
            } catch (Exception e) {
                if (currParam.getName().equals("privacy"))
                    Toast.makeText(getBaseContext(), "Privacy field must be private, public or unlisted", Toast.LENGTH_SHORT ).show();
                else
                    Toast.makeText(getBaseContext(), "Invalid value for field " + currParam.getName(), Toast.LENGTH_SHORT ).show();
                return;
            }
            parametersAction.add(newParameter);
        }
        newAction.setParameters(parametersAction);

        for (int i = 0; i < paramsReaction.size(); i++) {
            Param currParam = paramsReaction.get(i);
            JsonObject newParameter = new JsonObject();
            newParameter.addProperty("id", i);
            newParameter.addProperty("name", currParam.getName());
            newParameter.addProperty("type", currParam.getType());
            Log.d("TYPE", currParam.getType());
            try {
                if (currParam.getType().equals("Long"))
                    newParameter.addProperty("value", Long.parseLong(this.textInputEditTextListReactions.get(i).getText().toString()));
                else if (currParam.getType().equals("Integer"))
                    newParameter.addProperty("value", Integer.parseInt(this.textInputEditTextListReactions.get(i).getText().toString()));
                else if (currParam.getType().equals("Double"))
                    newParameter.addProperty("value", Double.parseDouble(this.textInputEditTextListReactions.get(i).getText().toString()));
                else if (currParam.getType().equals("Date")) {
                    SimpleDateFormat format = new SimpleDateFormat("dd/M/yyyy", Locale.FRANCE);
                    format.setLenient(false);
                    Date date = format.parse(this.textInputEditTextListReactions.get(i).getText().toString());
                    newParameter.addProperty("value", this.textInputEditTextListReactions.get(i).getText().toString());
                }
                else if (currParam.getType().equals("Privacy")) {
                    String privacyValue = this.textInputEditTextListReactions.get(i).getText().toString();
                    if(privacyValue.equals("private") || privacyValue.equals("public") || privacyValue.equals("unlisted"))
                        newParameter.addProperty("value", privacyValue);
                    else
                        throw new Exception("Invalid value for Privacy field");
                }
                else if (currParam.getType().equals("String")) {
                    newParameter.addProperty("value", this.textInputEditTextListReactions.get(i).getText().toString());
                }
                else
                    throw new Exception("Unknown data type");
            } catch (Exception e) {
                if (currParam.getName().equals("privacy"))
                    Toast.makeText(getBaseContext(), "Privacy field must be private, public or unlisted", Toast.LENGTH_SHORT ).show();
                else
                    Toast.makeText(getBaseContext(), "Invalid value for field " + currParam.getName(), Toast.LENGTH_SHORT ).show();
                return;
            }
            parametersReaction.add(newParameter);
        }
        newReaction.setParameters(parametersReaction);
        newAction.setReaction(newReaction);
        APIArea area = client.create(APIArea.class);
        Call<Void> request =  area.CreateAction(newAction);

        request.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getBaseContext(), R.string.area_success, Toast.LENGTH_SHORT ).show();
                } else {
                    Toast.makeText(getBaseContext(), R.string.area_error_request, Toast.LENGTH_SHORT ).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(getBaseContext(), R.string.area_error_make_request, Toast.LENGTH_SHORT ).show();
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
     * Compare a category against the list of services in currUser
     * @param category
     * @return
     */
    public boolean isCategoryInSubscription(String category) {
        for (String s : currUser.getSubServices().split(";")) {
            if (category.toLowerCase().equals(s.toLowerCase()))
                return true;
        }
        return false;
    }
}