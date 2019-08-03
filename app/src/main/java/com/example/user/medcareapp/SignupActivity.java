package com.example.user.medcareapp;

import android.app.ProgressDialog;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

public class SignupActivity extends AppCompatActivity {

    Button btnSignup;
    //input
    EditText etName, etSurname,etContact,etEmail,etPassword;
    RelativeLayout activity_sign_up;
    private ProgressDialog mDialog;

    private FirebaseAuth auth;
    private DatabaseReference UserDB;
    private String email,name, surname,contact,password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);



    }
}
