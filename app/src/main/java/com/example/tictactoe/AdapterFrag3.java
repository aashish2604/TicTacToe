package com.example.tictactoe;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

//Adapter class for RecyclerViewAdapter of fragment 3

public class AdapterFrag3 extends FirebaseRecyclerAdapter<Frag3Module,AdapterFrag3.myviewholder>
{

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public AdapterFrag3(@NonNull FirebaseRecyclerOptions<Frag3Module> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myviewholder holder, int position, @NonNull Frag3Module model) {
        holder.post_user_name.setText(model.getUsername());
        holder.post_description.setText(model.getDescription());
        holder.post_date.setText(model.getDate());
        holder.post_time.setText(model.getTime());
        Glide.with(holder.post_image.getContext()).load(model.getPost_image()).into(holder.post_image);
    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.all_post_layout,parent,false);
        return new myviewholder(view);
    }

    public class myviewholder extends RecyclerView.ViewHolder
    {
        TextView post_user_name;
        TextView post_date;
        TextView post_time;
        TextView post_description;
        ImageView post_image;

        public myviewholder(@NonNull View itemView) {
            super(itemView);

            post_user_name = itemView.findViewById(R.id.sender_user_name);
            post_date = itemView.findViewById(R.id.post_date);
            post_time = itemView.findViewById(R.id.post_time);
            post_description = itemView.findViewById(R.id.post_description);
            post_image = itemView.findViewById(R.id.post_image);
        }
    }

}
