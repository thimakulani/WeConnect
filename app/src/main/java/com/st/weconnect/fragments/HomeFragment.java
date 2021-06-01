package com.st.weconnect.fragments;

import android.os.Build;
import android.os.Bundle;
import android.os.Debug;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.st.weconnect.R;
import com.st.weconnect.adapters.PostsAdapter;
import com.st.weconnect.dialogs.AddPostDialogFragment;
import com.st.weconnect.model.AppUser;
import com.st.weconnect.model.PostsModel;

import java.util.ArrayList;


public class HomeFragment extends Fragment {


    private ArrayList<PostsModel> Items;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    private ShimmerFrameLayout mShimmerViewContainer;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        FloatingActionButton fab_new_post = (FloatingActionButton) view.findViewById(R.id.fab_add_post);

        /*testing the
        custom dialog layout
         */

        //call to show post dialog
        fab_new_post.setOnClickListener(this::ShowPostDialog);
        ConnectViews(view);

        return view;
    }

    private void ConnectViews(View view) {

        RecyclerView recycler = (RecyclerView) view.findViewById(R.id.RecyclerPosts);
        mShimmerViewContainer = view.findViewById(R.id.shimmer_view_container);
        final ArrayList<PostsModel> Items = new ArrayList<PostsModel>();
        recycler.setLayoutManager(new LinearLayoutManager(view.getContext()));
        final PostsAdapter adapter = new PostsAdapter(Items, getChildFragmentManager());
        recycler.setAdapter(adapter);

        try {
            mShimmerViewContainer.startShimmerAnimation();
            FirebaseFirestore
                    .getInstance()
                    .collection("Feeds")
                    .addSnapshotListener(new EventListener<QuerySnapshot>() {
                        @RequiresApi(api = Build.VERSION_CODES.N)
                        @Override
                        public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {


                            if (value != null) {

                                for (final DocumentChange dc : value.getDocumentChanges()) {
                                    switch (dc.getType()) {
                                        case ADDED:
                                            Items.add(dc.getDocument().toObject(PostsModel.class));
                                            adapter.notifyDataSetChanged();

                                            break;
                                        case MODIFIED:
                                            Items.set(dc.getOldIndex(), dc.getDocument().toObject(PostsModel.class));
                                            adapter.notifyDataSetChanged();
                                            break;
                                        case REMOVED:
                                            Items.remove(dc.getOldIndex());
                                            adapter.notifyDataSetChanged();
                                    }
                                }
                            }
                            mShimmerViewContainer.stopShimmerAnimation();
                            mShimmerViewContainer.setVisibility(View.GONE);
                        }
                    });

        } catch (Exception ex) {
            Toast.makeText(view.getContext(), ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void ShowPostDialog(View view) {

        AddPostDialogFragment postDialogFragment = new AddPostDialogFragment();
        postDialogFragment.show(getChildFragmentManager().beginTransaction(), "post dialog");

    }
}