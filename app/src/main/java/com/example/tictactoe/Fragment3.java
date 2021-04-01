package com.example.tictactoe;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Fragment3 extends Fragment {
    private FloatingActionButton addpostbtn;
    private RecyclerView postList;
    private DatabaseReference PostRef;
    AdapterFrag3 myadapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment3_layout,container,false);

        PostRef = FirebaseDatabase.getInstance().getReference().child("Posts");

        postList = (RecyclerView)v.findViewById(R.id.post_list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        postList.setLayoutManager(linearLayoutManager);

        FirebaseRecyclerOptions<Frag3Module> options =
                new FirebaseRecyclerOptions.Builder<Frag3Module>()
                        .setQuery(PostRef, Frag3Module.class)
                        .build();
        myadapter = new AdapterFrag3(options);
        postList.setAdapter(myadapter);


        addpostbtn = (FloatingActionButton) v.findViewById(R.id.add_post_button);
        addpostbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),NewBlogPost.class));
            }
        });
        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        myadapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        myadapter.stopListening();
    }


}
