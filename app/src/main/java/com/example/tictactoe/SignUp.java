package com.example.tictactoe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUp extends AppCompatActivity {
    private EditText username;
    private EditText email;
    private EditText password;
    private EditText cpassword;
    private EditText phonenum;
    private Button button;
    private TextView sign;
    private FirebaseAuth firebaseAuth;

    public SignUp() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        setupUI();
        firebaseAuth = FirebaseAuth.getInstance();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validate()){//creating database of the registered id
                    String user_email = email.getText().toString().trim();
                    String user_password = password.getText().toString().trim();
                    firebaseAuth.createUserWithEmailAndPassword(user_email,user_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()) {
                                Toast.makeText(SignUp.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                                    String uname= username.getText().toString().trim();
                                    String em= email.getText().toString().trim();
                                    String p=phonenum.getText().toString().trim();
                                    dataholder obj= new dataholder(uname,em);
                                    FirebaseDatabase db=FirebaseDatabase.getInstance();
                                    DatabaseReference node= db.getReference("Gamers");
                                    node.child(p).setValue(obj);
                                    username.setText("");
                                    email.setText("");
                                startActivity(new Intent(SignUp.this ,MainActivity.class));
                            }
                            else
                                Toast.makeText(SignUp.this,"Registration Unsuccessful",Toast.LENGTH_SHORT).show();

                        }
                    });
                }
            }
        });
        sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUp.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
    private void setupUI(){
        username = (EditText)findViewById(R.id.euser);
        email = (EditText)findViewById(R.id.eemail);
        password = (EditText)findViewById(R.id.epassword);
        cpassword = (EditText)findViewById(R.id.ecpassword);
        button = (Button)findViewById(R.id.butt);
        sign = (TextView)findViewById(R.id.esign);
        phonenum =(EditText)findViewById(R.id.ephonenum);
    }
    private Boolean validate(){
        Boolean r = false;
        String u = username.getText().toString();
        String e = email.getText().toString();
        String p = password.getText().toString();
        String cp = cpassword.getText().toString();
        if(u.isEmpty() || e.isEmpty() || p.isEmpty() || cp.isEmpty())
            Toast.makeText(SignUp.this,"Please enter all the credentials",Toast.LENGTH_SHORT).show();
        else if(p.equals(cp)){
            r=true;
        }
        else
            Toast.makeText(SignUp.this, "Password and confirm password should be same", Toast.LENGTH_SHORT).show();
        return r;

    }

}