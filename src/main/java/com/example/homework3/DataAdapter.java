package com.example.homework3;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder>{
    private List<Data> mDataList;
    private OnItemClickListener mOnItemClickListener;


    //内部接口
    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }



    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.data_item,parent,false);
        final ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Data data = mDataList.get(position);
        holder.dataTitle.setText(data.getTitle());
        holder.dataShareUser.setText(data.getShareUser());
        holder.dataChapterId.setText(String.valueOf(data.getChapterId()));
        holder.dataLink.setText(data.getLink());
        holder.dataView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClickListener.onItemClick(v, position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        View dataView;
        TextView dataChapterId;
        TextView dataShareUser;
        TextView dataTitle;
        TextView dataLink;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            dataView = itemView;
            dataChapterId = itemView.findViewById(R.id.chapter_id);
            dataLink = itemView.findViewById(R.id.link);
            dataShareUser = itemView.findViewById(R.id.share_user);
            dataTitle = itemView.findViewById(R.id.title);

        }
    }


    public DataAdapter(List<Data> mDataList) {
        this.mDataList = mDataList;
    }


}
