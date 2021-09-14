package com.st.weconnect.fragments;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.st.weconnect.R;
import com.st.weconnect.adapters.AppUserAdapter;
import com.st.weconnect.model.AppUser;

import java.util.ArrayList;
import java.util.List;

public class FriendsFragments extends Fragment{





    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View view = (ViewGroup) inflater.inflate(R.layout.activity_friends_fragments,container,false);

        ConnectViews(view);
        return view;
    }
    List<AppUser> Items = new ArrayList<>();
    private void ConnectViews(View view) {

        RecyclerView recycler = view.findViewById(R.id.friends_rv);
        recycler.setLayoutManager(new LinearLayoutManager(view.getContext()));
        AppUserAdapter adapter = new AppUserAdapter(Items);
        recycler.setAdapter(adapter);

        FirebaseFirestore
                .getInstance()
                .collection("Users")
                .whereNotEqualTo("id", FirebaseAuth.getInstance().getUid())
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if(!value.isEmpty()){
                            for (DocumentChange dc : value.getDocumentChanges()){
                                AppUser user = new AppUser();
                                switch (dc.getType()) {
                                    case ADDED:
                                        user = dc.getDocument().toObject(AppUser.class);
                                        Items.add(user);
                                        adapter.notifyDataSetChanged();
                                        Toast.makeText(view.getContext(), user.getId(), Toast.LENGTH_SHORT).show();
                                        break;
                                    case MODIFIED:
                                        user = dc.getDocument().toObject(AppUser.class);
                                        if(dc.getDocument().getId().equals(Items.get(dc.getOldIndex()).getId()))
                                        {
                                            Items.set(dc.getOldIndex(), user);
                                        }
                                        else{
                                            Items.set(dc.getOldIndex(), user);
                                        }
                                        adapter.notifyDataSetChanged();
                                        break;
                                    case REMOVED:
                                        break;
                                }
                            }
                        }
                    }
                });

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }



}
