package com.example.area.Login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.NonNull;

import com.example.area.ActivityHome;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import androidx.fragment.app.Fragment;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.area.API.APIClient;
import com.example.area.API.Login;
import com.example.area.API.APIArea;
import com.example.area.NavigationHost;
import com.example.area.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Fragment representing the login screen for Area.
 */
public class FragmentLogin extends Fragment {

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
        View view = inflater.inflate(R.layout.area_fragment_login, container, false);
        final TextInputLayout passwordTextInput = view.findViewById(R.id.password_text_input);
        final TextInputEditText passwordEditText = view.findViewById(R.id.password_edit_text);

        final TextInputLayout emailTextInput = view.findViewById(R.id.email_text_input);
        final TextInputEditText emailEditText = view.findViewById(R.id.email_edit_text);

        MaterialButton nextButton = view.findViewById(R.id.next_button);
        MaterialButton cancelButton = view.findViewById(R.id.cancel_button);

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((NavigationHost) getActivity()).navigateTo(new FragmentSignChoice(), false);
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                if (username.equals("") || password.equals(""))
                    return ;
                retrofit2.Retrofit client = APIClient.getClient();

                APIArea area = client.create(APIArea.class);
                Login postData = new Login(username, password);
                Call<Void> request =  area.UserLogin(postData);

                request.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.isSuccessful()) {
                            sharedPreferences = getActivity().getSharedPreferences(myPrefs, Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("Authorization", response.headers().get("Authorization"));
                            editor.putString("Username", username);
                            editor.commit();
                            APIClient.setAuthorization(response.headers().get("Authorization"));
                            Intent intent =  new Intent(getActivity(), ActivityHome.class);
                            intent.putExtra("AREA_USERNAME", username);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                            startActivity(intent);
                            getActivity().finish();
                        } else {
                            passwordTextInput.setError(getString(R.string.area_error_login));
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        passwordTextInput.setError(getString(R.string.area_error_internet));
                    }
                });
            }
        });

        passwordEditText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                passwordTextInput.setError(null);
                return false;
            }
        });
        return view;
    }
}
