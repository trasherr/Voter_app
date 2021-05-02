package com.rkgit.voter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {

    private List<MainData> dataList;
    private Activity context;
    private RoomDB database;

    public MainAdapter(Activity context, List<MainData> dataList){

        this.context = context;
        this.dataList = dataList;
        notifyDataSetChanged();
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row_main,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MainData data = dataList.get(position);
        database = RoomDB.getInstance(context);
        holder.textView.setText(data.getCandidate());

        holder.bt_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainData d = dataList.get(holder.getAdapterPosition());
                database.mainDao().delete(d);

                int postion = holder.getAdapterPosition();
                dataList.remove(position);
                notifyItemRemoved(position);
                notifyItemChanged(position,dataList.size());
            }
        });

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView textView;
        ImageView bt_exit;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textView = itemView.findViewById(R.id.text_view);
            bt_exit = itemView.findViewById(R.id.bt_exit);

        }
    }



}
