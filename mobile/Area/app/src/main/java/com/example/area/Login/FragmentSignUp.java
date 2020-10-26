package com.example.area.Login;

import android.os.Bundle;
import androidx.annotation.NonNull;

import com.example.area.API.User;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import androidx.fragment.app.Fragment;
import android.text.Editable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.area.API.APIClient;
import com.example.area.API.APIArea;
import com.example.area.API.UserSignup;
import com.example.area.NavigationHost;
import com.example.area.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Fragment representing the signup screen for Area.
 */
public class FragmentSignUp extends Fragment {

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
        View view = inflater.inflate(R.layout.area_fragment_signup, container, false);
        final TextInputLayout passwordTextInput = view.findViewById(R.id.password_text_input);
        final TextInputEditText passwordEditText = view.findViewById(R.id.password_edit_text);

        final TextInputLayout passwordConfirmTextInput = view.findViewById(R.id.password_confirm_text_input);
        final TextInputEditText passwordConfirmEditText = view.findViewById(R.id.password_confirm_edit_text);

        final TextInputLayout emailTextInput = view.findViewById(R.id.email_text_input);
        final TextInputEditText emailEditText = view.findViewById(R.id.email_edit_text);

        final TextInputLayout fistNameTextInput = view.findViewById(R.id.firstname_text_input);
        final TextInputEditText firstNameEditText = view.findViewById(R.id.firstname_edit_text);

        final TextInputLayout lastNameTextInput = view.findViewById(R.id.lastname_text_input);
        final TextInputEditText lastNameEditText = view.findViewById(R.id.lastname_edit_text);

        final TextInputLayout usernameTextInput = view.findViewById(R.id.username_text_input);
        final TextInputEditText usernameEditText = view.findViewById(R.id.username_edit_text);

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
                String firstPassInput = passwordEditText.getText().toString();
                String secondPassInput = passwordConfirmEditText.getText().toString();
                String firstName = firstNameEditText.getText().toString();
                String lastName = lastNameEditText.getText().toString();
                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                String username = usernameEditText.getText().toString();
                String gender = "";

                if (!firstPassInput.equals(secondPassInput)) {
                    passwordConfirmTextInput.setError(getString((R.string.area_error_confirm_password)));
                    return;
                }

                retrofit2.Retrofit client = APIClient.getClient();

                APIArea area = client.create(APIArea.class);

                UserSignup postData = new UserSignup(username, password, lastName, firstName, email);
                Call<User> request =  area.UserSignup(postData);

                request.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        if (response.isSuccessful()) {
                            passwordTextInput.setError(null);
                            ((NavigationHost) getActivity()).navigateTo(new FragmentLogin(), false);
                        } else {
                            Toast.makeText(getActivity().getBaseContext(), R.string.area_error_create_account, Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        Toast.makeText(getActivity().getBaseContext(), R.string.area_error_make_request, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        passwordEditText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                Editable firstPassInput = passwordEditText.getText();
                Editable secondPassInput = passwordConfirmEditText.getText();

                if (firstPassInput.equals(secondPassInput)) {
                    passwordConfirmTextInput.setError(null);
                }
                return false;
            }
        });

        passwordConfirmEditText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                Editable firstPassInput = passwordEditText.getText();
                Editable secondPassInput = passwordConfirmEditText.getText();

                if (firstPassInput.equals(secondPassInput)) {
                    passwordConfirmTextInput.setError(null);
                }
                return false;
            }
        });

        return view;
    }
}
