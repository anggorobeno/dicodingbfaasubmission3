package com.example.githubuserapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.githubuserapp.database.FavDAO;
import com.example.githubuserapp.database.FavoriteDatabase;
import com.example.githubuserapp.model.UserInfo;
import com.example.githubuserapp.utils.Constants;
import com.example.githubuserapp.view.DetailUser;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class FavouriteAdapter extends RecyclerView.Adapter<FavouriteAdapter.ListViewHolder> {
    private ArrayList<UserInfo> listFavouriteUser;
    private final Context context;

    public void setData(ArrayList<UserInfo> userInfos) {
        this.listFavouriteUser = userInfos;
        notifyDataSetChanged();
    }
    public void clearRV(ArrayList<UserInfo> clearListUser) {
        this.listFavouriteUser = clearListUser;
        this.listFavouriteUser.clear();
        notifyDataSetChanged();
    }

    public FavouriteAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.favourite_items_constraint, parent, false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, int position) {
        holder.bind(listFavouriteUser.get(position));
        holder.listener.setOnClickListener(v ->{
            // tes MoveActivity
            Intent intent = new Intent(v.getContext(), DetailUser.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            UserInfo favourite = new UserInfo();
            favourite.setUsername(listFavouriteUser.get(position).getUsername());
            favourite.setType(listFavouriteUser.get(position).getType());
            favourite.setUrl(listFavouriteUser.get(position).getUrl());
            intent.putExtra(Constants.FAV_PERSON, favourite);

            context.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return listFavouriteUser.size();
    }

    public class ListViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewUsername;
        public TextView textViewName;
        public ImageButton deleteFavButton;
        public ImageView avatar;
        public CardView listener;
        public ListViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewUsername = itemView.findViewById(R.id.textViewUsername);
            textViewName = itemView.findViewById(R.id.textViewTypeUser);
            avatar = itemView.findViewById(R.id.avatar);
            listener = itemView.findViewById(R.id.card_view);
            deleteFavButton = itemView.findViewById(R.id.deleteFavButton);
        }

        public void bind(UserInfo userInfo) {
            textViewUsername.setText(userInfo.getUsername());
            textViewName.setText("Type : " + userInfo.getType());
            Picasso.with(context)
                    .load(userInfo.getUrl())
                    .into(avatar);
            deleteFavButton.setOnClickListener(view ->{
                final android.app.AlertDialog.Builder deleteFavAlert = new AlertDialog.Builder(itemView.getContext());
                deleteFavAlert.setMessage("Are u sure?");
                deleteFavAlert.setTitle("DELETE");
                deleteFavAlert.setCancelable(false);
                deleteFavAlert.setPositiveButton("yes",(dialogInterface,i) -> {
                    FavDAO favDAO = Room.databaseBuilder(itemView.getContext(), FavoriteDatabase.class, "userinfo")
                            .allowMainThreadQueries()
                            .build()
                            .favDAO();
                    favDAO.deleteUsername(userInfo.getId());
                    listFavouriteUser.remove(userInfo);
                    notifyDataSetChanged();

                });
                deleteFavAlert.setNegativeButton("no",((dialogInterface, i) -> deleteFavAlert.setCancelable(true)));
                deleteFavAlert.show();
            });

        }
    }
}
