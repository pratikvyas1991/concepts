package com.concepts.myconcepts.retrofit.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.concepts.myconcepts.R;
import com.concepts.myconcepts.retrofit.model.StudentData;

import java.util.List;

/**
 * Created by tasol on 10/5/18.
 */

public class ConceptRecyclerAdapter extends RecyclerView.Adapter<ConceptRecyclerAdapter.ConceptViewHolder> {
    StudentData studentData;
    Context context;

    public ConceptRecyclerAdapter(StudentData studentData, Context context) {
        this.studentData = studentData;
        this.context = context;
    }

    @Override
    public ConceptViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_retrofit,parent,false);
        ConceptViewHolder viewHolder = new ConceptViewHolder(view);
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(ConceptViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ConceptViewHolder extends RecyclerView.ViewHolder{

        public ConceptViewHolder(View itemView) {
            super(itemView);
        }
    }
}
