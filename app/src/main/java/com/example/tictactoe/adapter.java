package com.example.tictactoe;

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
import com.google.firebase.database.FirebaseDatabase;

public class adapter extends FirebaseRecyclerAdapter<model,adapter.myviewholder>
{
    //adapter class for the recycler view of the users in Fragment2
    public Button request;
    private FirebaseDatabase firebaseDatabase;

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

    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.singlerowdesign,parent,false);
        return new myviewholder(view);
    }

    public class myviewholder extends RecyclerView.ViewHolder
    {
        TextView unamesrd;

        public myviewholder(@NonNull View itemView) {
            super(itemView);
            unamesrd=itemView.findViewById(R.id.unamesrd);
            request=itemView.findViewById(R.id.request);
            request.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String selectedUser= FirebaseAuth.getInstance().getCurrentUser().getUid();
                    String user=sendRequest();
                    if(user.equals(selectedUser))
                    {

                    }
                    else
                    {

                    }
                }

            });
        }
    }
    public String sendRequest(){
        String currentuser = FirebaseAuth.getInstance().getCurrentUser().getUid();
        return currentuser;
    }
}
