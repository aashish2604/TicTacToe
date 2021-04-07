package com.example.tictactoe;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Game extends AppCompatActivity {

    int gameState;
    int counter = 0;
    String currentuid;
    String player1_name;
    String player2_name;

    //Player 1 is the sender of request
    //Player 2 is the receiver of the request

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);


        /* gameState = 1 :- game is playable
           gameState = 2 :- game is won by someone
           gameState = 3 :- game is draw */

        gameState = 1;
        currentuid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference players = FirebaseDatabase.getInstance().getReference().child("Requests").child(currentuid);
        players.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                player1_name = snapshot.child("senderUsername").getValue().toString().trim();
                player2_name = snapshot.child("recieverUsername").getValue().toString().trim();
                TextView player_1 = findViewById(R.id.player_1_name);
                TextView player_2 = findViewById(R.id.player_2_name);
                player_1.setText(player1_name);
                player_2.setText(player2_name);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }

    public void GameBoardClick(View view){
        ImageView selectImage = (ImageView) view;

        int selectedBlock = 0;
        switch (selectImage.getId()){
            case R.id.iv_11: selectedBlock = 1; break;
            case R.id.iv_12: selectedBlock = 2; break;
            case R.id.iv_13: selectedBlock = 3; break;

            case R.id.iv_21: selectedBlock = 4; break;
            case R.id.iv_22: selectedBlock = 5; break;
            case R.id.iv_23: selectedBlock = 6; break;

            case R.id.iv_31: selectedBlock = 7; break;
            case R.id.iv_32: selectedBlock = 8; break;
            case R.id.iv_33: selectedBlock = 9; break;
        }

        PlayGame(selectedBlock, selectImage);
    }

    int activePlayer = 1;
    ArrayList<Integer> player1 = new ArrayList<Integer>();
    ArrayList<Integer> player2 = new ArrayList<Integer>();
    private void PlayGame(int selectedBlock, ImageView selectImage) {

        if(gameState==1){
            if(activePlayer ==1){
                //Player 1 gets the first call and gets the symbol X
                selectImage.setImageResource(R.drawable.x);
                player1.add(selectedBlock);
                activePlayer=2;
                counter++;
            }
            else if(activePlayer ==2){
                //Player 2 gets  the second call and gets the symbol O
                selectImage.setImageResource(R.drawable.goldeno);
                player2.add(selectedBlock);
                activePlayer=1;
                counter++;
            }

            selectImage.setEnabled(false);
            checkWinner();
        }

    }

    private void checkWinner() {
        int winner= 0;
        TextView w1 = (TextView)findViewById(R.id.winner1_message);
        TextView w2 = (TextView)findViewById(R.id.winner2_message);
        TextView w3 = (TextView)findViewById(R.id.draw_message);
        Button exit = (Button)findViewById(R.id.exit);

        /*  Checking the winning of player 1  */
        if(player1.contains(1) && player1.contains(2) && player1.contains(3)) { winner =1; }
        if(player1.contains(4) && player1.contains(5) && player1.contains(6)) { winner =1; }
        if(player1.contains(7) && player1.contains(8) && player1.contains(9)) { winner =1; }

        if(player1.contains(1) && player1.contains(4) && player1.contains(7)) { winner =1; }
        if(player1.contains(2) && player1.contains(5) && player1.contains(8)) { winner =1; }
        if(player1.contains(3) && player1.contains(6) && player1.contains(9)) { winner =1; }

        if(player1.contains(1) && player1.contains(5) && player1.contains(9)) { winner =1; }
        if(player1.contains(7) && player1.contains(5) && player1.contains(3)) { winner =1; }


        /*  Checking the winning of player 2  */
        if(player2.contains(1) && player2.contains(2) && player2.contains(3)) { winner =2; }
        if(player2.contains(4) && player2.contains(5) && player2.contains(6)) { winner =2; }
        if(player2.contains(7) && player2.contains(8) && player2.contains(9)) { winner =2; }

        if(player2.contains(1) && player2.contains(4) && player2.contains(7)) { winner =2; }
        if(player2.contains(2) && player2.contains(5) && player2.contains(8)) { winner =2; }
        if(player2.contains(3) && player2.contains(6) && player2.contains(9)) { winner =2; }

        if(player2.contains(1) && player2.contains(5) && player2.contains(9)) { winner =2; }
        if(player2.contains(7) && player2.contains(5) && player2.contains(3)) { winner =2; }

        if(winner!=0 && gameState ==1)
        {
            if(winner == 1)
                w1.setVisibility(View.VISIBLE);
            else if(winner == 2)
                w2.setVisibility(View.VISIBLE);
            gameState = 2;



            exit.setClickable(true);
            exit.setVisibility(View.VISIBLE);
            exit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(Game.this,Fragment1.class));
                }
            });

        }
        else if(winner == 0 && counter == 9)
        {
            w3.setVisibility(View.VISIBLE);
            gameState =3;
            exit.setClickable(true);
            exit.setVisibility(View.VISIBLE);
            exit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(Game.this,Fragment1.class));
                }
            });
        }
    }
}