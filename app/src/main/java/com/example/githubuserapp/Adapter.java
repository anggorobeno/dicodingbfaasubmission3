package com.example.githubuserapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.githubuserapp.model.UserInfo;
import com.example.githubuserapp.utils.Constants;
import com.example.githubuserapp.view.DetailUser;
import com.squareup.picasso.Picasso;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ListViewHolder> {
    private List<UserInfo> listuser;
    private final Context context;

    public void setData(List<UserInfo> userInfos) {
        this.listuser = userInfos;
        notifyDataSetChanged();
    }

    public void clearRV(List<UserInfo> clearListUser) {
        this.listuser = clearListUser;
        this.listuser.clear();
        notifyDataSetChanged();
    }

    public Adapter(Context context) {
        this.context = context;
    }


    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_github_constraint, parent, false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter.ListViewHolder holder, int position) {
        holder.bind(listuser.get(position));
        holder.listener.setOnClickListener(v -> {
            // tes MoveActivity
            Toast.makeText(context, "You clicked " + listuser.get(position).getUsername(), Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(v.getContext(), DetailUser.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            intent.putExtra(Constants.EXTRA_PERSON, listuser.get(position).getUsername());

            context.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return listuser.size();
    }

    public class ListViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewUsername;
        public TextView textViewName;

        public ImageView avatar;
        public CardView listener;

        public ListViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewUsername = itemView.findViewById(R.id.textViewUsername);
            textViewName = itemView.findViewById(R.id.textViewTypeUser);
            avatar = itemView.findViewById(R.id.avatar);
            listener = itemView.findViewById(R.id.card_view);
        }

        public void bind(UserInfo userInfo) {
            textViewUsername.setText(userInfo.getUsername());
            textViewName.setText("Type : " + userInfo.getType());
            Picasso.with(context)
                    .load(userInfo.getUrl())
                    .into(avatar);
        }

    }
}
