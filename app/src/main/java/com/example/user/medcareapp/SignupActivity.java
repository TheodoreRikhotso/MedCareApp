package com.example.user.medcareapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.medcareapp.com.example.user.medcareapp.pojo.User;
import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignupActivity extends AppCompatActivity {

    Button btnSignup;
    //input
    EditText etUpEmail, etUpName,etUpPassword,etUpContact;
    RelativeLayout activity_sign_up;
    private ProgressDialog mDialog;
    private TextView tvSignup,tvLinkSignIn;

    private FirebaseAuth auth;
    private DatabaseReference userDB;
    private String email,name,contact,password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        tvSignup =findViewById(R.id.tvSignup);
        tvLinkSignIn =  findViewById(R.id.tvLinkSignIn);
        etUpEmail = findViewById(R.id.etUpEmail);
        etUpName =  findViewById(R.id.etUpName);
        etUpPassword = findViewById(R.id.etUpPassword);
        etUpContact =  findViewById(R.id.etUpContact);

        tvSignup =  findViewById(R.id.tvSignup);
        tvLinkSignIn =  findViewById(R.id.tvLinkSignIn);


        userDB = FirebaseDatabase.getInstance().getReference("Users");
        auth = FirebaseAuth.getInstance();

        tvSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //email & password
                email = etUpEmail.getText().toString();

                contact = etUpContact.getText().toString();
                name = etUpName.getText().toString();
                password = etUpPassword.getText().toString();

//                if (name.isEmpty()) {
//                    etName.setError("Username must not be empty");
//                } else {
//                    if (email.isEmpty()) {
//
//                        etEmail.setError("Email is empty");
//                    } else {
//
//                        if (password.isEmpty()) {
//                            etPassword.setError("Password is empty");
//                        } else {
//                            if (password.length() >= 4) {
                //signUpUser(email, password);

                auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>(){
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    String id = auth.getCurrentUser().getUid();
                                    userDB = FirebaseDatabase.getInstance().getReference("Users/" + id );
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d("mAIN", "createUserWithEmail:success");

                                    //updateUI(user);
                                    Firebase.setAndroidContext(SignupActivity.this);

                                    User user_ = new User();

                                    user_.setContact(contact);
                                    user_.setName(name);
                                    user_.setEmail(email);
                                    userDB.setValue(user_);
                                    startActivity(new Intent(getApplicationContext(), HomeActivity.class));

                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w("sss", "createUserWithEmail:failure", task.getException());
                                    Toast.makeText(SignupActivity.this, "Authentication failed."+ task.getException(),
                                            Toast.LENGTH_SHORT).show();

                                }

                                // ...
                            }
                        });

//
                Toast.makeText(getApplicationContext(), "Email "+email+" Password "+password, Toast.LENGTH_SHORT).show();
//                            } else {
//                                etPassword.setError("Password must contains more than 6 characters  ");
//                            }
//                        }
//                    }
//                }
            }
        });

        tvLinkSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), SignInActivity.class));
            }
        });


    }
}
