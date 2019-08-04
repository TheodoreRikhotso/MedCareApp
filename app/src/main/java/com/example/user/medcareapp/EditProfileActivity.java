package com.example.user.medcareapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.example.user.medcareapp.com.example.user.medcareapp.pojo.User;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import de.hdodenhof.circleimageview.CircleImageView;

public class EditProfileActivity extends AppCompatActivity {

    private EditText etID,etIllness,etKinNameContact,etContact,etEmail,etAddress,etName;
    private CircleImageView  ibEdProfileImage;

    private CircleImageView ivPhoto;

    private Uri filePath;
    private StorageReference mStorageReference;
    private Uri uri, uriSecond;
    private FirebaseUser user;
    private DatabaseReference databaseUser;
    private Button btnSubmitProfile;
    private int PICK_IMAGE_REQUEST = 111;
    private User userpojo;
    private String  downloadImageUrl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        etID = findViewById(R.id.etID);
        etIllness= findViewById(R.id.etIllness);
        etEmail = findViewById(R.id.etEmail);
        etAddress = findViewById(R.id.etAddress);
        etName = findViewById(R.id.etNames);
        etContact = findViewById(R.id.etContact);
        etKinNameContact = findViewById(R.id.etKinNameContact);
        ibEdProfileImage = findViewById(R.id.ibEdProfileImage);
        btnSubmitProfile = findViewById(R.id.btnSubmitProfile);

        user = FirebaseAuth.getInstance().getCurrentUser();
        mStorageReference = FirebaseStorage.getInstance().getReference();
        databaseUser = FirebaseDatabase.getInstance().getReference("Users/" + user.getUid());
        btnSubmitProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = etAddress.getText().toString();
                final String illness = etIllness.getText().toString();
                final String email = etEmail.getText().toString();
                final String address = etAddress.getText().toString();
                final String kin = etKinNameContact.getText().toString();
                final String contact = etContact.getText().toString();

                final String name = etName.getText().toString();

                final StorageReference childRef = mStorageReference.child("UserImage").child(filePath.getLastPathSegment());
               // UploadTask uploadTask = childRef.putFile(filePath);

                final UploadTask uploadTask = childRef.putFile(filePath);

                databaseUser = FirebaseDatabase.getInstance().getReference("Users/" + user.getUid() );

                uploadTask.addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e)
                    {
                        String message = e.toString();
                        Toast.makeText(getApplicationContext(), "Error: " + message, Toast.LENGTH_SHORT).show();
                        //loadingBar.dismiss();
                    }
                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot)
                    {
                        Toast.makeText(getApplicationContext(), "Product Image uploaded Successfully...", Toast.LENGTH_SHORT).show();

                        Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                            @Override
                            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception
                            {
                                if (!task.isSuccessful())
                                {
                                    throw task.getException();
                                }

                                  downloadImageUrl = childRef.getDownloadUrl().toString();
                                return childRef.getDownloadUrl();
                            }
                        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                            @Override
                            public void onComplete(@NonNull Task<Uri> task)
                            {
                                if (task.isSuccessful())
                                {
                                    downloadImageUrl = task.getResult().toString();

                                    User user = new User();
                        user.setName(name);
                        user.setEmail(email);
                        user.setAddresses(address);
                        user.setKins(kin);
                        user.setId(downloadImageUrl);
                        user.setIllnesses(illness);
                        user.setContact(contact);
                        databaseUser.setValue(user);

Intent intent = new Intent(getApplicationContext(),ProfileActivity.class);
startActivity(intent);
                                    Toast.makeText(getApplicationContext(), "got the Product image Url Successfully...", Toast.LENGTH_SHORT).show();


                                }
                            }
                        });
                    }
                });
//                uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                    @Override
//                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                        Uri uir = taskSnapshot.getTask().getResult();
//
//                        User user = new User();
//                        user.setName(name);
//                        user.setEmail(email);
//                        user.setAddresses(address);
//                        user.setKins(kin);
//                        user.setId(uir.toString());
//                        user.setIllnesses(illness);
//                        user.setContact(contact);
//
//                        Toast.makeText(getApplicationContext(), "Upload successful ", Toast.LENGTH_SHORT).show();
//                    }
//                }).addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//
//                        Toast.makeText(getApplicationContext(), "Upload Failed -> " + e, Toast.LENGTH_SHORT).show();
//                    }
//                });
//




            }
        });
        ibEdProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               // isImage = "2";
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_PICK);
                startActivityForResult(Intent.createChooser(intent, "Select Image"), PICK_IMAGE_REQUEST);

            }
        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();


            try {
                //getting image from gallery
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);

                //Setting image to ImageView
                ibEdProfileImage.setImageBitmap(bitmap);
            } catch (Exception e) {
                e.printStackTrace();
            }
            ibEdProfileImage.setImageURI(filePath);
        }

    }

}
