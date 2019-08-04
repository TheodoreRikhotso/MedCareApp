package com.example.user.medcareapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignInActivity extends AppCompatActivity {

    private EditText etInEmail, etInPassword;
    private TextView tvSignin, tvLinkSignup;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
       FirebaseApp.initializeApp(getApplicationContext());

        tvSignin = findViewById(R.id.tvSignin);
        tvLinkSignup = findViewById(R.id.tvLinkSignup);
        etInPassword = findViewById(R.id.etInPassword);
        etInEmail =findViewById(R.id.etInEmail);


        auth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                if (firebaseAuth.getCurrentUser() != null) {
                    startActivity(new Intent(SignInActivity.this, MainActivity.class));
                }
            }
        };

        if (auth.getCurrentUser() != null) {
            finish();
            startActivity(new Intent(getApplicationContext(), HomeActivity.class));
        }

        tvLinkSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), SignupActivity.class));
            }
        });

        tvSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = etInEmail.getText().toString();
                String password = etInPassword.getText().toString();


                if (email.isEmpty()) {
                    etInEmail.setError("Email is empty");

                } else {

                    if (password.isEmpty()) {
                        etInPassword.setError("Password is empty");
                    } else {
                        loginUser(email, password);
                    }
                }
            }
        });

        if (auth.getCurrentUser() != null) {

            startActivity(new Intent(getApplicationContext(), HomeActivity.class));

        }

    }

    private void loginUser(String email, final String password) {
        FirebaseUser user = auth.getCurrentUser();
        //user.sendEmailVerification();


        if(user!=null) {
            boolean emailVerified = user.isEmailVerified();

            if (emailVerified) {
                auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {


                        if (task.isSuccessful()) {
//
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));

                        } else {
                            String emails = task.getException().getMessage();
                            if (emails.contains("email")) {
                                etInEmail.setError(task.getException().getMessage());
                            } else {
                                etInPassword.setError(task.getException().getMessage());
                            }

//
                        }

                    }

                });
            }
            else
            {


                FirebaseAuth.getInstance().signOut();
                Toast.makeText(this, "Email is not verified", Toast.LENGTH_SHORT).show();



            }
        }else {

            auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {


                            if (task.isSuccessful()) {

                                startActivity(new Intent(getApplicationContext(), MainActivity.class));

                            } else {

                                String emails = task.getException().getMessage();
                                if (emails.contains("email")) {
                                    etInEmail.setError(task.getException().getMessage());
                                } else {
                                    etInPassword.setError(task.getException().getMessage());
                                }

                            }

                        }
                    });

        }
//

    }

}
