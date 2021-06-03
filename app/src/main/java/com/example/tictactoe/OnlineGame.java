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

public class OnlineGame extends AppCompatActivity {

    int selectedBlock = 0;
    int counter = 0;
    String status;
    String sign = "X";

    String currentuid;
    String turn;

    String confirm;

    TextView player_1_chance;
    TextView player_2_chance;

    DatabaseReference gamePlay;

    String player1_name,player_1_uid;
    String player2_name,player_2_uid;

    //chance = 1 for player 1
    //chance = 2 for player 2

    ArrayList<Integer> player1 = new ArrayList<Integer>();
    ArrayList<Integer> player2 = new ArrayList<Integer>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_online_game);

        Intent intent = getIntent();
        String uid = intent.getStringExtra("recieverUid");

        currentuid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference players = FirebaseDatabase.getInstance().getReference().child("Requests").child(uid);
        players.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                player1_name = snapshot.child("senderUsername").getValue().toString().trim();
                player2_name = snapshot.child("recieverUsername").getValue().toString().trim();
                player_1_uid = snapshot.child("senderUid").getValue().toString().trim();
                player_2_uid = snapshot.child("recieverUid").getValue().toString().trim();
                TextView player_1 = findViewById(R.id.player_1_name);
                TextView player_2 = findViewById(R.id.player_2_name);
                player_1_chance = findViewById(R.id.player_1_turn);
                player_2_chance = findViewById(R.id.player_2_turn);
                player_1.setText(player1_name);
                player_2.setText(player2_name);
                player_1_chance.setVisibility(View.VISIBLE);

                gamePlay = FirebaseDatabase.getInstance().getReference().child("Arena").child(player_1_uid + " - " + player_2_uid);
                gamePlay.child("GameState").setValue("playable");

                if(currentuid == player_1_uid){
                    gamePlay.child("Turn").setValue(player1_name);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }

    /* X is for player 1
       O is for player 2 */

    public void GameBoardClick(View view){

        ImageView selectImage = (ImageView) view;

        switch (selectImage.getId()){
            case R.id.iv_11: selectedBlock = 1; gamePlay.child("iv_11").setValue(sign); break;
            case R.id.iv_12: selectedBlock = 2; break;
            case R.id.iv_13: selectedBlock = 3; break;

            case R.id.iv_21: selectedBlock = 4; break;
            case R.id.iv_22: selectedBlock = 5; break;
            case R.id.iv_23: selectedBlock = 6; break;

            case R.id.iv_31: selectedBlock = 7; break;
            case R.id.iv_32: selectedBlock = 8; break;
            case R.id.iv_33: selectedBlock = 9; break;
        }
        gamePlay.child("Turn").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String chance = snapshot.getValue().toString().trim();

                if (chance == player1_name) {
                    gamePlay.child("position").child(String.valueOf(selectedBlock)).setValue((selectedBlock)+"X");
                    gamePlay.child("Turn").setValue(player2_name);
                    selectImage.setEnabled(false);
                    PlayGame1(selectedBlock, selectImage);

                }
                else if(chance == player2_name){
                    player_2_chance.setVisibility(View.VISIBLE);
                    gamePlay.child("position").child(String.valueOf(selectedBlock)).setValue((selectedBlock)+"O");
                    gamePlay.child("Turn").setValue(player1_name);
                    selectImage.setEnabled(false);
                    PlayGame2(selectedBlock,selectImage);

                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void PlayGame2(int selectedBlock, ImageView selectImage) {

        gamePlay.child("GameState").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                status = snapshot.getValue().toString().trim();
                if(status == "playable"){
                    selectImage.setImageResource(R.drawable.goldeno);
                    player2.add(selectedBlock);
                    player_2_chance.setVisibility(View.INVISIBLE);
                    counter++;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    private void PlayGame1(int selectedBlock, ImageView selectImage) {
            gamePlay.child("GameState").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    status = snapshot.getValue().toString().trim();
                    if(status == "playable"){
                        selectImage.setImageResource(R.drawable.x);
                        player1.add(selectedBlock);
                        player_1_chance.setVisibility(View.INVISIBLE);
                        counter++;
                        winner();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });


        }

    private void winner() {
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

        if(winner!=0 && status == "playable")
        {
            if(winner == 1)
                w1.setVisibility(View.VISIBLE);
            else if(winner == 2)
                w2.setVisibility(View.VISIBLE);
            status = "won";



            exit.setClickable(true);
            exit.setVisibility(View.VISIBLE);
            exit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(OnlineGame.this,Fragment1.class));
                }
            });

        }
        else if(winner == 0 && counter == 9)
        {
            w3.setVisibility(View.VISIBLE);
            status ="draw";
            exit.setClickable(true);
            exit.setVisibility(View.VISIBLE);
            exit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(OnlineGame.this,Fragment1.class));
                }
            });
        }
        gamePlay.child("GamePlay").setValue(status);
    }

}

