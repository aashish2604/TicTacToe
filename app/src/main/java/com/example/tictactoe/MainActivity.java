package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private EditText username;
    private EditText password;
    private Button login;
    private TextView message;
    private TextView signup;
    private String sampleuid = "Admin";
    private String sampass = "12345";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        username = (EditText)findViewById(R.id.euid);
        password = (EditText)findViewById(R.id.epass);
        login = (Button)findViewById(R.id.but);
        message =(TextView)findViewById(R.id.emsg);
        signup = (TextView)findViewById(R.id.esig);
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
        message.setVisibility(View.GONE);
        if((uid.equals(sampleuid)) && (pass.equals(sampass))) {
            Intent intent = new Intent(MainActivity.this, SecondActivity.class);
            startActivity(intent);
            Toast.makeText(MainActivity.this,"Login successful",Toast.LENGTH_SHORT).show();
        }
        else{
            message.setVisibility(View.VISIBLE);

        }
    }
}