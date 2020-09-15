package com.geekbrains.android.homework.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.geekbrains.android.homework.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class AuthGoogleFragment extends Fragment {
    private GoogleSignInClient googleSignInClient;
    private Unbinder unbinder;
    private View view;

    private int RC_SIGN_IN = 100;

    @BindView(R.id.sign_in_button)
    SignInButton signInButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_auth_google, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.view = view;

        unbinder = ButterKnife.bind(this, view);

        initSignInParameters();
        initSignInBtn();
    }

    private void initSignInParameters() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        googleSignInClient = GoogleSignIn.getClient(getActivity(), gso);
    }

    private void initSignInBtn() {
        signInButton.setSize(SignInButton.SIZE_STANDARD);

        signInButton.setOnClickListener(v -> signIn());
    }

    @Override
    public void onStart() {
        super.onStart();
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(getContext());
        updateUI(account);
    }

    private void updateUI(GoogleSignInAccount account) {
        if (account == null || account.isExpired() || account.getIdToken() == null) {
        } else {
            Toast.makeText(getContext(), account.getIdToken(), Toast.LENGTH_SHORT).show();
        }
    }

    private void signIn() {
        Intent signInIntent = googleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            updateUI(account);
        } catch (ApiException e) {
            Log.w(AuthGoogleFragment.class.getSimpleName(), "signInResult:failed code=" + e.getStatusCode());
            updateUI(null);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        unbinder.unbind();
    }
}