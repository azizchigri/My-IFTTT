package com.example.area.Login;

import android.os.Bundle;
import androidx.annotation.NonNull;
import com.google.android.material.button.MaterialButton;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.area.NavigationHost;
import com.example.area.R;

/**
 * Fragment representing the choice between the login and signup screens for Area.
 */
public class FragmentSignChoice extends Fragment {

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
        View view = inflater.inflate(R.layout.area_fragment_sign_choice, container, false);

        MaterialButton signInButton = view.findViewById(R.id.signin_button);
        MaterialButton signUpButton = view.findViewById(R.id.signup_button);

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    ((NavigationHost) getActivity()).navigateTo(new FragmentLogin(), true);
            }
        });
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    ((NavigationHost) getActivity()).navigateTo(new FragmentSignUp(), true);
            }
        });
        return view;
    }
}
