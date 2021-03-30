package com.example.tictactoe;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.Toolbar;

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

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class NewBlogPost extends AppCompatActivity {
    private Toolbar newPostToolbar;
    private ImageView newPostImage;
    private EditText newPostDesc;
    private Button newPostBtn;
    private static final int Gallery_Pick = 1;
    private Uri ImageUri;
    private String Description;
    private StorageReference PostImagesReference;
    private String saveCurrentDate;
    private String saveCurrentTime;
    private String postRandomName, current_user_id;
    private String downloadUrl;
    private DatabaseReference userRef, postRef;
    private FirebaseAuth mAuth;
    private ProgressDialog loading;
    private FirebaseDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_blog_post);

        mAuth = FirebaseAuth.getInstance();

        PostImagesReference = FirebaseStorage.getInstance().getReference();
        userRef = FirebaseDatabase.getInstance().getReference().child("Gamers");
        current_user_id = mAuth.getCurrentUser().getUid();

        newPostImage = findViewById(R.id.upload_image);
        newPostDesc = findViewById(R.id.add_description);
        newPostBtn = findViewById(R.id.post);
        loading = new ProgressDialog(this);

        newPostImage.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                opengallery();
            }
        }));

        newPostBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 ValidatePostInfo();
            }
        });
    }

    private void ValidatePostInfo() {

        Description = newPostDesc.getText().toString().trim();
        if(ImageUri==null)
        {
            Toast.makeText(this, "Please select a post image", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(Description))
        {
            Toast.makeText(this, "Please say something about your image", Toast.LENGTH_SHORT).show();
        }
        else
        {
            loading.setTitle("Uploading your post");
            loading.setMessage("Please wait for a while");
            loading.show();
            loading.setCanceledOnTouchOutside(false);
            StoringImageToFirebaseStorage();
        }

    }

    private void StoringImageToFirebaseStorage()
    {
        Calendar calender = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("dd-MM-yyyy");
        saveCurrentDate= currentDate.format(calender.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH-mm");
        saveCurrentTime= currentTime.format(calender.getTime());

        postRandomName = saveCurrentDate + saveCurrentTime;

        StorageReference filePath = PostImagesReference.child("Post Images").child(ImageUri.getLastPathSegment() + postRandomName + ".jpg");
        final UploadTask uploadTask = filePath.putFile(ImageUri);

        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                String message = e.toString();
                Toast.makeText(NewBlogPost.this, "Error : " + message, Toast.LENGTH_SHORT).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot)
            {
                Toast.makeText(NewBlogPost.this, "Image Uploaded Successfully", Toast.LENGTH_SHORT).show();


                Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception
                    {
                        if (!task.isSuccessful()){
                            throw task.getException();
                        }
                        downloadUrl = filePath.getDownloadUrl().toString();
                        return filePath.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task)
                    {

                        if(task.isSuccessful())
                        {
                            downloadUrl = task.getResult().toString();
                            Toast.makeText(NewBlogPost.this, "Got upload image Url successfully", Toast.LENGTH_SHORT).show();
                            savingPostInformationToDatabase();
                        }
                    }
                });
            }
        });

    }

    private void savingPostInformationToDatabase()
    {   String uid = FirebaseAuth.getInstance().getUid();
        String desc = newPostDesc.getText().toString().trim();
        BlogDataHolder obj= new BlogDataHolder(uid,saveCurrentDate,saveCurrentTime,desc,downloadUrl);
        DatabaseReference myRef;
        myRef = FirebaseDatabase.getInstance().getReference().child("Posts");
        myRef.child(postRandomName + current_user_id).setValue(obj);
        startActivity(new Intent(NewBlogPost.this,SecondActivity.class));
    }

    private void opengallery() {//Method to allow user choose a pic from his gallery on clicking the image
        Intent galleryIntent = new Intent();
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, Gallery_Pick);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==Gallery_Pick && resultCode==RESULT_OK && data!=null){

            ImageUri = data.getData();
            newPostImage.setImageURI(ImageUri);

        }

    }
}