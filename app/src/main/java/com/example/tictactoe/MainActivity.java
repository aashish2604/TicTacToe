package com.example.tictactoe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseError;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    private EditText username;
    private EditText password;
    private Button login;
    private TextView message;
    private TextView signup;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        username = (EditText)findViewById(R.id.euid);
        password = (EditText)findViewById(R.id.epass);
        login = (Button)findViewById(R.id.but);
        message =(TextView)findViewById(R.id.emsg);
        signup = (TextView)findViewById(R.id.esig);
        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputname = username.getText().toString();
                String inputpassword = password.getText().toString();
                if(inputname.isEmpty() || inputpassword.isEmpty()){
                    Toast.makeText(MainActivity.this,"Please enter the credentials",Toast.LENGTH_SHORT).show();
                }
                else
                validate(inputname ,inputpassword);
            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,SignUp.class));
            }
        });
    }
    private void validate(String uid,String pass){
        progressDialog.setMessage("Take a breath !");
        progressDialog.show();
        message.setVisibility(View.INVISIBLE);
        firebaseAuth.signInWithEmailAndPassword(uid,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    progressDialog.dismiss();
                    Toast.makeText(MainActivity.this,"Login Successful",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(MainActivity.this,MainScreen.class));
                }
                else{
                    progressDialog.dismiss();
                    Toast.makeText(MainActivity.this,"Login Failed",Toast.LENGTH_SHORT).show();
                    message.setVisibility(View.VISIBLE);
                }
            }
        });

    }
}