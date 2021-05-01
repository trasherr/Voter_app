package com.rkgit.voter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class VoterAdapter extends RecyclerView.Adapter<VoterAdapter.ViewHolder> {

    private List<Voter> dataList2;
    private Activity context;
    private RoomDB database;

    public VoterAdapter(Activity context, List<Voter> dataList2){

        this.context = context;
        this.dataList2 = dataList2;
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
        Voter data = dataList2.get(position);
        database = RoomDB.getInstance(context);
        holder.textView.setText(data.getVoter());

        holder.bt_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Voter d = dataList2.get(holder.getAdapterPosition());
                database.voterDoa().delete(d);

                int postion = holder.getAdapterPosition();
                dataList2.remove(position);
                notifyItemRemoved(position);
                notifyItemChanged(position,dataList2.size());
            }
        });

    }

    @Override
    public int getItemCount() {
        return dataList2.size();
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
