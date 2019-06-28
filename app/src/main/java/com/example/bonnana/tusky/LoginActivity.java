package com.example.bonnana.tusky;

import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bonnana.tusky.R;
import com.example.bonnana.tusky.model.Login;
import com.example.bonnana.tusky.model.Token;
import com.example.bonnana.tusky.network.RetrofitInstance;
import com.example.bonnana.tusky.services.UserServices;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    Login login;
    EditText usernameEditText;
    EditText passwordEditText;
    Button loginButton;
    ProgressBar loadingProgressBar;
    UserServices userServices;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_action_logo_ver12);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();

        usernameEditText = findViewById(R.id.username);
        passwordEditText = findViewById(R.id.password);
        loginButton = findViewById(R.id.login);
        loadingProgressBar = findViewById(R.id.loading);
        login = new Login();

        userServices = RetrofitInstance.getRetrofitInstance(LoginActivity.this).create(UserServices.class);

        TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // ignore
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // empty
            }

            @Override
            public void afterTextChanged(Editable s) {
                login.setEmail(usernameEditText.getText().toString());
                login.setPassword(passwordEditText.getText().toString());

                loginButton.setEnabled(isDataValid());

                if (validateUsername() != null) {
                    usernameEditText.setError(validateUsername());
                }
                if (validatePassword() != null) {
                    passwordEditText.setError(validatePassword());
                }
            }
        };
        usernameEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.addTextChangedListener(afterTextChangedListener);

        LoginActivity activityContext = this;

        passwordEditText.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                loadingProgressBar.setVisibility(View.VISIBLE);
                activityContext.login();
            }
            return false;
        });

        loginButton.setOnClickListener(v -> {
            loadingProgressBar.setVisibility(View.VISIBLE);
            activityContext.login();
        });
    }

    private void login() {
        Call<Token> call = userServices.login(login);
        call.enqueue(new Callback<Token>() {
            @Override
            public void onResponse(@NonNull Call<Token> call, @NonNull Response<Token> response) {
                loadingProgressBar.setVisibility(View.GONE);

                Token token = response.body();
                if (token == null) {
                    return;
                }

                SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor editor = sharedPref.edit();

                editor.putString("idToken", token.getToken());
                editor.apply();
                editor.commit();

                Toast.makeText(LoginActivity.this, "Successfully logged in!", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onFailure(@NonNull Call<Token> call, @NonNull Throwable t) {
                loadingProgressBar.setVisibility(View.GONE);
                Toast.makeText(LoginActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }


    private void showLoginFailed(@StringRes Integer errorString) {
        Toast.makeText(getApplicationContext(), errorString, Toast.LENGTH_SHORT).show();
    }

    private Boolean isDataValid() {
        return validatePassword() == null && validateUsername() == null;
    }

    private String validateUsername() {
        String username = login.getEmail();
        if (username == null) {
            return "No email input";
        }
        if (username.length() < 3) {
            return "Not enough characters";
        }
        if (!isEmailValid(username)) {
            return "Not valid email";
        }
        return null;
    }

    private String validatePassword() {
        String password = login.getPassword();
        if (password == null) {
            return "No password entered";
        }
        if (password.length() < 6) {
            return "Password can't be shorter than 6 characters";
        }
        return null;
    }

    boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}
