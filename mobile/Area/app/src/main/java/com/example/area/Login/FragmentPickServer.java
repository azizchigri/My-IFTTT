package com.example.area.Login;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.area.API.APIArea;
import com.example.area.API.APIClient;
import com.example.area.API.About;
import com.example.area.NavigationHost;
import com.example.area.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Fragment representing the server picker screen for Area.
 */
public class FragmentPickServer extends Fragment {

    SharedPreferences sharedPreferences;
    public static final String myPrefs = "Area" ;

    /**
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.area_fragment_pick_server, container, false);

        final TextInputLayout serverTextInput = view.findViewById(R.id.server_text_input);
        final TextInputEditText serverEditText = view.findViewById(R.id.server_edit_text);

        sharedPreferences = getActivity().getSharedPreferences(myPrefs, Context.MODE_PRIVATE);
        serverEditText.setText(sharedPreferences.getString("Default_server", ""));
        MaterialButton nextButton = view.findViewById(R.id.next_button);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String server = serverEditText.getText().toString();

                if (!server.startsWith("http") && !server.startsWith("https")) {
                    return ;
                }

                APIClient.setServer(server);
                retrofit2.Retrofit client = APIClient.getClient();

                APIArea area = client.create(APIArea.class);
                Call<About> request =  area.getAbout();

                request.enqueue(new Callback<About>() {
                    @Override
                    public void onResponse(Call<About> call, Response<About> response) {
                        if (response.isSuccessful()) {
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("Default_server", server);
                            editor.commit();

                            ((NavigationHost) getActivity()).navigateTo(new FragmentSignChoice(), true);
                        } else {
                            serverTextInput.setError(getString(R.string.area_error_server));
                        }
                    }

                    @Override
                    public void onFailure(Call<About> call, Throwable t) {
                        serverTextInput.setError(getString(R.string.area_error_internet));
                    }
                });
            }
        });
        return view;
    }
}
