package com.example.tictactoe;

//Adapter class of Fragment 1

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AdapterFrag1 extends FirebaseRecyclerAdapter<ModelFrag1,AdapterFrag1.myviewholder>
{
    private String currentUid;
    private Context context;

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */



    public AdapterFrag1(@NonNull FirebaseRecyclerOptions<ModelFrag1> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myviewholder holder, int position, @NonNull ModelFrag1 model) {
        currentUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        holder.receiverUid.setText(model.getRecieverUid());
        String check  = holder.receiverUid.getText().toString();
        Log.d("demo","Id of receiver = "+check + " Id of user id logged in = "+ currentUid);
        if(check.equals(currentUid))
        {
            holder.senderName.setText(model.getSenderUsername());
            holder.receiverName.setText(model.getRecieverUsername());
            holder.senderUid.setText(model.getSenderUid());

            holder.decline.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    DatabaseReference myRef;
                    myRef = FirebaseDatabase.getInstance().getReference().child("Requests");
                    myRef.child(currentUid).getRef().removeValue();

                }
            });

            holder.accept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //starting the tic tac toe game
                    Intent intent = new Intent(v.getContext(),OnlineGame.class);
                    intent.putExtra("recieverUid",currentUid);
                    v.getContext().startActivity(intent);

                    v.getContext().startActivity(new Intent(v.getContext(),Game.class));

                }
            });

        }
        else{

            //Code to remove unwanted segments in the lists

            holder.itemView.setVisibility(View.GONE);
            ViewGroup.LayoutParams params = holder.itemView.getLayoutParams();
            params.height = 0;
            holder.itemView.setLayoutParams(params);
        }

    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.all_requests_layout, parent, false);
            return new myviewholder(view);
    }


    public class myviewholder extends RecyclerView.ViewHolder
    {

        TextView receiverName;
        TextView senderUid;
        TextView receiverUid;
        TextView senderName;
        Button accept;
        Button decline;

        public myviewholder(@NonNull View itemView) {
            super(itemView);
            senderName = itemView.findViewById(R.id.sender_user_name);
            receiverName = itemView.findViewById(R.id.receiver_user_name);
            senderUid = itemView.findViewById(R.id.sender_uid);
            receiverUid = itemView.findViewById(R.id.receiver_uid);
            accept = itemView.findViewById(R.id.accept);
            decline = itemView.findViewById(R.id.decline);
        }
    }
}

