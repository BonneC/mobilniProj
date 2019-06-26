package com.example.bonnana.tusky;

import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
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

public class LoginActivity extends AppCompatActivity {
    Login login;
    EditText usernameEditText;
    EditText passwordEditText;
    Button loginButton;
    ProgressBar loadingProgressBar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usernameEditText = findViewById(R.id.username);
        passwordEditText = findViewById(R.id.password);
        loginButton = findViewById(R.id.login);
        loadingProgressBar = findViewById(R.id.loading);
        login = new Login();

        TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // ignore
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                loginButton.setEnabled(isDataValid());

                if (validateUsername() != null) {
                    usernameEditText.setError(validateUsername());
                }
                if (validatePassword() != null) {
                    passwordEditText.setError(validatePassword());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                login.setEmail(usernameEditText.getText().toString());
                login.setPassword(passwordEditText.getText().toString());
            }
        };
        usernameEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.addTextChangedListener(afterTextChangedListener);

        passwordEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    // login here
                }
                return false;
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingProgressBar.setVisibility(View.VISIBLE);
                // login here
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
