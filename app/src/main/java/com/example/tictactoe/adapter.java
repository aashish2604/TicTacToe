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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class adapter extends FirebaseRecyclerAdapter<model,adapter.myviewholder>
{
    //adapter class for the recycler view of the users in Fragment2
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private String recieverUid , senderUid;


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
    recieverUid = getRef(position).getKey();

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
        Button request;

        public myviewholder(@NonNull View itemView) {
            super(itemView);
            unamesrd=itemView.findViewById(R.id.unamesrd);
            request=itemView.findViewById(R.id.request);
            request.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(senderUid.equals(recieverUid))
                    {
                        Toast.makeText(v.getContext(), "You cannot send play request to yourself",Toast.LENGTH_SHORT).show();
                        Log.d("Demo","Id of receiver is " + recieverUid + "Id of the sender is " + senderUid);
                    }
                    else
                    {
                        Toast.makeText(v.getContext(), "You can send request",Toast.LENGTH_SHORT).show();
                        Log.d("Demo","Id of receiver is" + recieverUid + "Id of the sender is" + senderUid);
                    }
                }

            });
        }
    }
}
/* After clicking the button adjacent to the name of a particular registered users.
 I should be getting the uid of that user in the recieverUid but I am getting the userID of the user logged in */