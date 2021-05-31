package com.example.tictactoe;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import de.hdodenhof.circleimageview.CircleImageView;

public class SetupActivity extends AppCompatActivity {
    private CircleImageView setupImage;
    private Uri mainImageURI = null;
    private Button setupBtn;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference UsersRef;
    private String downloadUrl;
    private StorageReference storageReference , UsersProfileImageRef;
    private ProgressBar setupProgress;
    private final static int Gallery_Pick = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);

        setupImage = (CircleImageView)findViewById(R.id.upload_profile_pic);
        Button upload = (Button)findViewById(R.id.upload_image_button);
        Button skip = (Button)findViewById(R.id.skip_upload_button);
        setupProgress = new ProgressBar(this);

        setupImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validatePost();
            }
        });

        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri imageUri = Uri.parse("android.resource://"+ R.class.getPackage().getName()+"/"+R.drawable.defaultprofpic);
                mainImageURI = imageUri;
                StoringImageToFirebase();
            }
        });
    }

    private void validatePost() {
        if(mainImageURI==null)
            Toast.makeText(this,"Please select an image",Toast.LENGTH_SHORT).show();
        else
        {
            setupProgress.setVisibility(View.VISIBLE);
            StoringImageToFirebase();
        }


    }

    private void StoringImageToFirebase() {

        String uid = FirebaseAuth.getInstance().getUid();
        StorageReference filePath = FirebaseStorage.getInstance().getReference().child("Profile Images").child(mainImageURI.getLastPathSegment() + uid + ".jpg");
        final UploadTask uploadTask = filePath.putFile(mainImageURI);

        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                String errorMsg = e.toString();
                Toast.makeText(SetupActivity.this, "Error :- "+errorMsg, Toast.LENGTH_SHORT).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                Toast.makeText(SetupActivity.this, "Profile Image is uploaded successfully", Toast.LENGTH_SHORT).show();
                Task<Uri> uriTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {

                        if(!task.isSuccessful()){
                            throw task.getException();
                        }
                        downloadUrl = filePath.getDownloadUrl().toString();
                        return filePath.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {

                        if(task.isSuccessful())
                        {
                            downloadUrl = task.getResult().toString();
                            Toast.makeText(SetupActivity.this, "Got upload image Url successfully", Toast.LENGTH_SHORT).show();
                            savingPostInformationToDatabase();
                        }

                    }
                });

            }
        });


    }

    private void savingPostInformationToDatabase() {

        String uid = FirebaseAuth.getInstance().getUid();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Gamers").child(uid).child("Image Url");
        databaseReference.setValue(downloadUrl);
        setupProgress.setVisibility(View.INVISIBLE);
        startActivity(new Intent(SetupActivity.this,MainScreen.class));

    }

    private void openGallery() //method for selecting image to be uploaded
    {
        Intent galleryIntent = new Intent();
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent,Gallery_Pick);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==Gallery_Pick && resultCode==RESULT_OK && data!=null){

            mainImageURI = data.getData();
            setupImage.setImageURI(mainImageURI);
        }
    }
}