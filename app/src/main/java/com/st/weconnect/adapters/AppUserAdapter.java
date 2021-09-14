package com.st.weconnect.adapters;

import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;
import com.squareup.picasso.Picasso;
import com.st.weconnect.R;
import com.st.weconnect.model.AppUser;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

public class AppUserAdapter extends RecyclerView.Adapter<AppUserViewHolder>{

    List<AppUser> Items = new ArrayList<>();

    public AppUserAdapter(List<AppUser> items) {
        Items = items;
    }

    @NonNull
    @Override
    public AppUserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewGroup view = (ViewGroup) LayoutInflater.from(parent.getContext()).inflate(R.layout.row_add_friend_view,parent,false);
        return new AppUserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AppUserViewHolder holder, int position) {

        holder.Txt_Username.setText(MessageFormat.format("{0} {1}", Items.get(position).getName(), Items.get(position).getLastName()));
        holder.Btn_Add.setOnClickListener(v -> {

        });
        if(Items.get(position).getImg_url() != null)
        {
            Picasso.get().load(Items.get(position).getImg_url())
                    .resize(200, 200)
                    .into(holder.Img_Profile);
        }
        else{
            ColorGenerator generator = ColorGenerator.MATERIAL;
            TextDrawable drawable2 = TextDrawable.builder()
                    .beginConfig()

                    .useFont(Typeface.DEFAULT)
                    .fontSize(30) /* size in px */
                    .bold()
                    .toUpperCase()
                    .endConfig()
                    .buildRound(Items.get(position).getName().substring(0,1), generator.getRandomColor());
            holder.Img_Profile.setImageDrawable(drawable2);
            //
        }
    }

    @Override
    public int getItemCount() {
        return Items.size();
    }
}
class AppUserViewHolder extends RecyclerView.ViewHolder {

    MaterialTextView Txt_Username;
    MaterialButton Btn_Add;
    AppCompatImageView Img_Profile;
    public AppUserViewHolder(@NonNull View itemView) {
        super(itemView);
        Txt_Username = itemView.findViewById(R.id.user_row_username);
        Btn_Add = itemView.findViewById(R.id.row_add_friend_btn);
        Img_Profile = itemView.findViewById(R.id.friend_image);

    }
}