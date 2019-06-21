package com.example.bonnana.tusky.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.bonnana.tusky.R;
import com.example.bonnana.tusky.model.Topic;

import java.util.ArrayList;

public class CardViewAdapter extends RecyclerView.Adapter<CardViewAdapter.CardViewHolder> {
    private ArrayList<Topic> topics;
    private View.OnClickListener mOnItemClickListener;

    public CardViewAdapter(ArrayList<Topic> topics) {
        this.topics = topics;
    }

    @Override
    public CardViewAdapter.CardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.topics_view, parent, false);

        CardViewHolder vh = new CardViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(CardViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.textView.setText(topics.get(position).getTitle());

    }

    @Override
    public int getItemCount() {
        return topics.size();
    }

    public void setOnItemClickListener(View.OnClickListener itemClickListener) {
        mOnItemClickListener = itemClickListener;
    }

    public class CardViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView textView;

        public CardViewHolder(View v) {
            super(v);
            textView = v.findViewById(R.id.topic_title);

            v.setTag(this);
            v.setOnClickListener(mOnItemClickListener);
        }
    }
}
