package com.test.iotanalysis;

import android.content.Context;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ActivityAdapter extends RecyclerView.Adapter<ActivityAdapter.MyViewHolder> {

    private Context mContext;


    List<Data> mData;


    public ActivityAdapter(Context mContext, List<Data> mData) {

        this.mContext = mContext;

        this.mData = mData;

    }






    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {



        View view ;

        LayoutInflater mInflater = LayoutInflater.from(mContext);

        view = mInflater.inflate(R.layout.activity_item,parent,false);

        return new MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        holder.time.setText(mData.get(position).getTime());

        if (mData.get(position).getStatus().equals("true"))
            holder.status.setText("On");
        else
            holder.status.setText("Off");
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView status, time;

        public MyViewHolder(View itemView) {
            super(itemView);

            status = itemView.findViewById(R.id.status);
            time = itemView.findViewById(R.id.time);

        }
    }
}