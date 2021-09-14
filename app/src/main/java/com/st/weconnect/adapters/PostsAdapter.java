package com.st.weconnect.adapters;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.cazaea.sweetalert.SweetAlertDialog;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.st.weconnect.R;
import com.st.weconnect.dialogs.PopUpCommentsDialog;
import com.st.weconnect.model.AppUser;
import com.st.weconnect.model.PostsModel;
/*import com.weconnect.we_connect.R;
import com.weconnect.we_connect.dialogs.CommentsDialogFragment;
import com.weconnect.we_connect.model.PostsModel;*/

import java.util.ArrayList;

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.PostsViewHolder>  {

    private ArrayList<PostsModel> Items = new ArrayList<PostsModel>();
    //private final onButtonClickListener clickListener;

    public PostsAdapter(ArrayList<PostsModel> items, FragmentManager fragmentTransaction) {
        Items = items;
        Fm = fragmentTransaction;
    }

    private final FragmentManager Fm;
    @NonNull
    @Override
    public PostsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View inflater = LayoutInflater.from(parent.getContext()).inflate(R.layout.new_post_row, parent, false);
        return new PostsViewHolder(inflater);
    }

    @Override
    public void onBindViewHolder(@NonNull final PostsViewHolder holder, final int position) {
        holder.Row_Video.setVisibility(View.GONE);
        holder.Row_DeletePost.setVisibility(View.GONE);
        holder.Row_EditPost.setVisibility(View.GONE);
        holder.Row_PostImage.setVisibility(View.GONE);

        holder.Row_TxtPostMessage.setText(Items.get(position).getMessage());
        holder.Row_DateTime.setText(Items.get(position).getDate_posted());

        FirebaseFirestore
                .getInstance()
                .collection("Users")
                .document(Items.get(position).getUid())
                .addSnapshotListener((value, error) -> {
                    assert value != null;
                    if(value.exists()){
                        AppUser user = value.toObject(AppUser.class);
                        holder.Row_TxtSenderName.setText(String.format("%s %s", user.getName(), user.getLastName()));

                        //delete button to be enabled
                        if(user.getId().equals(FirebaseAuth.getInstance().getUid())){
                            holder.Row_DeletePost.setVisibility(View.VISIBLE);
                            holder.Row_DeletePost.setOnClickListener(v -> {

                                SweetAlertDialog pDialog = new SweetAlertDialog(holder.itemView.getContext(), SweetAlertDialog.WARNING_TYPE);
                                pDialog.setTitleText("Confirm");

                                pDialog.setCancelable(true);
                                pDialog.setCancelText("No");
                                pDialog.setContentText("Are you sure you want to delete this post");
                                pDialog.setConfirmText("Yes")
                                        .setConfirmClickListener(sweetAlertDialog -> {
                                            FirebaseFirestore.getInstance()
                                                    .collection("Feeds")
                                                    .document(Items.get(position).getId())
                                                    .delete();
                                            sweetAlertDialog.dismiss();
                                        });
                                pDialog.setCancelClickListener(Dialog::dismiss);
                                pDialog.show();


                            });
                        }




                        if(user.getImg_url() == null){
                            ColorGenerator generator = ColorGenerator.MATERIAL;
                            TextDrawable drawable2 = TextDrawable.builder()
                                    .beginConfig()

                                    .useFont(Typeface.DEFAULT)
                                    .fontSize(30) /* size in px */
                                    .bold()
                                    .toUpperCase()
                                    .endConfig()
                                    .buildRound(user.getName().substring(0,1), generator.getRandomColor());
                            holder.Row_SenderProfile.setImageDrawable(drawable2);
                        }
                        else{
                            Picasso.get().load(user.getImg_url())
                                    .resize(200, 200)
                                    .into(holder.Row_SenderProfile);
                        }


                    }
                });

        if(Items.get(position).getUrl() != null) {
            Picasso.get()
                    .load(Items.get(position).getUrl())
                    .resize(150, 150)
                    .into(holder.Row_PostImage, new Callback() {
                        @Override
                        public void onSuccess() {
                            holder.Row_PostImage.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onError(Exception e) {

                        }
                    });


        }

    }

    @Override
    public int getItemCount()
    {
        return Items.size();
    }

    private View mView;
    public class PostsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public AppCompatImageView Row_SenderProfile;
        public AppCompatImageView Row_PostImage;
        public AppCompatImageButton Row_DeletePost;
        public AppCompatImageView Row_EditPost;
        public MaterialTextView Row_TxtSenderName;
        public MaterialTextView Row_TxtPostMessage;
        public VideoView Row_Video;
        public AppCompatImageView Row_CommentImg;
        public MaterialTextView Row_TxtComment_Count;
        public AppCompatImageView Row_LikeImg;
        public MaterialTextView Row_Like_Count;
        public MaterialTextView Row_DateTime;

        public PostsViewHolder(@NonNull View itemView) {
            super(itemView);

            mView = itemView;

            Row_PostImage = (AppCompatImageView)itemView.findViewById(R.id.row_post_img);
            Row_SenderProfile = (AppCompatImageView)itemView.findViewById(R.id.row_post_user_profile);
            Row_DeletePost = (AppCompatImageButton)itemView.findViewById(R.id.row_post_delete);
            Row_EditPost = (AppCompatImageView)itemView.findViewById(R.id.row_post_edit);
            Row_CommentImg = (AppCompatImageView)itemView.findViewById(R.id.row_post_comment_img);
            Row_LikeImg = (AppCompatImageView)itemView.findViewById(R.id.row_post_like_img);
            Row_Video = (VideoView) itemView.findViewById(R.id.row_post_video);

            Row_TxtPostMessage = (MaterialTextView) itemView.findViewById(R.id.row_post_caption);
            Row_TxtSenderName = (MaterialTextView) itemView.findViewById(R.id.row_post_sender_name);
            Row_TxtComment_Count = (MaterialTextView) itemView.findViewById(R.id.row_post_count_comments);
            Row_Like_Count = (MaterialTextView) itemView.findViewById(R.id.row_post_count_likes);
            Row_DateTime = (MaterialTextView) itemView.findViewById(R.id.row_post_date_and_time);

            Row_CommentImg.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            if (v.getId() == Row_CommentImg.getId())
            {
                int pos = getAbsoluteAdapterPosition();

                //clickListener.onButtonClick(Items.get(pos).getId(), pos);
                //Toast.makeText(mView.getContext(), Items.get(pos).getId(),Toast.LENGTH_LONG).show();

                PopUpCommentsDialog dlg = new PopUpCommentsDialog(Items.get(pos).getId());
                FragmentTransaction ft = Fm.beginTransaction();
                dlg.show(ft, "Comments");
            }
        }
    }
}
