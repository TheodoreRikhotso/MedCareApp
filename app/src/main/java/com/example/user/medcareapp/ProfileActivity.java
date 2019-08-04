package com.example.user.medcareapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.example.user.medcareapp.com.example.user.medcareapp.pojo.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity {

    private TextView tvID,tvIllness,tvKinNameContact,tvContact,tvEmail,tvAddress,tvName;
    private CircleImageView ibProfileImage;

    private Uri filePath;
    private StorageReference mStorageReference;
    private Uri uri, uriSecond;
    private FirebaseUser user;
    private DatabaseReference databaseUser;
    private Button btnSubmitProfile;
    private int PICK_IMAGE_REQUEST = 111;
    private User userpojo;
    private String  downloadImageUrl;
    private ImageView ivEditProfile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ivEditProfile = findViewById(R.id.ivEditProfile);
        tvID = findViewById(R.id.tvID);
        tvIllness= findViewById(R.id.tvIllness);
        tvEmail = findViewById(R.id.tvEmail);
        tvAddress = findViewById(R.id.tvAddress);
        tvName = findViewById(R.id.tvNames);
        tvContact = findViewById(R.id.tvContact);
        tvKinNameContact = findViewById(R.id.tvKinNameContact);
        ibProfileImage = findViewById(R.id.ibProfileImage);

        user = FirebaseAuth.getInstance().getCurrentUser();
        databaseUser = FirebaseDatabase.getInstance().getReference("Users/" + user.getUid());


        ivEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =  new Intent(getApplicationContext(),EditProfileActivity.class);
                startActivity(intent);
            }
        });

        databaseUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot != null) {
                    User person = dataSnapshot.getValue(User.class);
                    if (person != null) {
//                        mainName = person.getName();
//                        mainDepart = person.getDepartmentName();
//
//                        mainImage = person.getImage();


                        tvName.setText(person.getName());
                        tvAddress.setText(person.getAddresses());
                        tvContact.setText(person.getContact());
                        tvEmail.setText(person.getEmail());
                        tvIllness.setText(person.getIllnesses());
                        tvIllness.setText(person.getKins());
                        if (person.getId() != null) {
                            Glide.with(ProfileActivity.this).load(person.getId()).asBitmap().centerCrop().placeholder(R.drawable.person).into(new BitmapImageViewTarget(ibProfileImage) {
                                @Override
                                protected void setResource(Bitmap resource) {
                                    RoundedBitmapDrawable circularBitmapDrawable =
                                            RoundedBitmapDrawableFactory.create(ProfileActivity.this.getResources(), resource);
                                    circularBitmapDrawable.setCircular(true);
                                    ibProfileImage.setImageDrawable(circularBitmapDrawable);
                                }
                            });
                        } else {
                            System.out.println("Image is null");
                        }
                    }

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(ProfileActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }
}
