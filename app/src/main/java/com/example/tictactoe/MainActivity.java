package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private EditText username;
    private EditText password;
    private Button login;
    private TextView message;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        username = (EditText)findViewById(R.id.euid);
        password = (EditText)findViewById(R.id.epass);
        login = (Button) findViewById(R.id.but);
        message =(TextView)findViewById(R.id.emsg);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validate(username.getText().toString(), password.getText().toString());
            }
        });
    }
    private void validate(String uid,String pass){
        message.setVisibility(View.GONE);
        if((uid == "Admin")&& (pass == "1234")) {
            Intent intent = new Intent(MainActivity.this, SecondActivity.class);
            startActivity(intent);
        }
        else{
            message.setVisibility(View.VISIBLE);
        }
    }
}