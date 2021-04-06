package com.example.tictactoe;

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
import com.google.firebase.database.FirebaseDatabase;

public class Fragment1 extends Fragment {

    RecyclerView recview;
    AdapterFrag1 myadapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment1_layout,container, false);

        recview =(RecyclerView)view.findViewById(R.id.request_list);
        recview.setLayoutManager(new LinearLayoutManager(getContext()));

        FirebaseRecyclerOptions<ModelFrag1> options =
                new FirebaseRecyclerOptions.Builder<ModelFrag1>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Requests"), ModelFrag1.class)
                        .build();

        myadapter = new AdapterFrag1(options);
        recview.setAdapter(myadapter);

        return view;
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
