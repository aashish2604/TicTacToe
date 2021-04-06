package com.example.tictactoe;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class adapter extends FirebaseRecyclerAdapter<model,adapter.myviewholder>
{
    //adapter class for the recycler view of the users in Fragment2
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private String senderUid;
    private String senderUname;

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public adapter(@NonNull FirebaseRecyclerOptions<model> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myviewholder holder, int position, @NonNull model model) {
    holder.unamesrd.setText(model.getUsername());
    holder.recieverUid.setText(model.getUid());
    String ruid = holder.recieverUid.getText().toString();
    String recieverUsername = holder.unamesrd.getText().toString();

        holder.request.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View v) {

                if(senderUid.equals(ruid))
                {
                    Toast.makeText(v.getContext(), "You cannot send play request to yourself",Toast.LENGTH_SHORT).show();
                    Log.d("Demo","Id of receiver is " + ruid + "Id of the sender is " + senderUid);

                }
                else
                {
                    Log.d("Demo","Id of receiver is " + ruid + "Id of the sender is " + senderUid);
                    String B = "disabled";

                     DatabaseReference senderUsername = FirebaseDatabase.getInstance().getReference().child("Gamers").child(senderUid);
                    senderUsername.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                             senderUname = snapshot.child("username").getValue().toString().trim();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                    RequestDataHolder ob = new RequestDataHolder(B,ruid,recieverUsername,senderUid,senderUname);
                    DatabaseReference myRef;
                    myRef = FirebaseDatabase.getInstance().getReference().child("Requests");
                    myRef.child(ruid).setValue(ob);
                    Toast.makeText(v.getContext(), "Request Sent", Toast.LENGTH_SHORT).show();
                    senderUname = "";
                }
            }

        });
    }


    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.singlerowdesign,parent,false);
        senderUid=FirebaseAuth.getInstance().getCurrentUser().getUid();
        return new myviewholder(view);
    }


    public class myviewholder extends RecyclerView.ViewHolder
    {
        TextView unamesrd;
        TextView recieverUid;
        Button request;


        public myviewholder(@NonNull View itemView) {
            super(itemView);
            unamesrd=itemView.findViewById(R.id.unamesrd);
            recieverUid=itemView.findViewById(R.id.current_uid);
            request=itemView.findViewById(R.id.request);
        }
    }
}
