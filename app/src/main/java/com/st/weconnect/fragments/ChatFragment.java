package com.st.weconnect.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.st.weconnect.R;
import com.st.weconnect.adapters.ChatsAdapter;
import com.st.weconnect.model.ChatsModel;

import java.util.ArrayList;

public class ChatFragment extends Fragment {


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View chatView = LayoutInflater.from(getContext()).inflate(R.layout.activity_chat_fragment,container,false);

        populateChats(chatView);
        return chatView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void populateChats(View view)
    {
        ChatsAdapter chatsAdapter;
        ArrayList<ChatsModel> chatsLists = new ArrayList<>();
        RecyclerView chatsRecyclerView = view.findViewById(R.id.chats_rv);

        
    }
}
