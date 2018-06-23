package com.concepts.myconcepts.service;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.AppCompatSeekBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import com.concepts.myconcepts.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tasol on 16/5/18.
 */

public class MyServiceActivity extends Activity {
    Button btnStop,btnStart;
    RecyclerView rvList;
    List<String>studentList =new ArrayList<>();
    StudentAdapter studentAdapter;
    LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);
        btnStart = (Button)findViewById(R.id.btnStart);
        btnStop= (Button)findViewById(R.id.btnStop);
        rvList = (RecyclerView)findViewById(R.id.rvList);
        linearLayoutManager =new LinearLayoutManager(MyServiceActivity.this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvList.setLayoutManager(linearLayoutManager);
//        rvList.setHasFixedSize(true);


        populateStudentList();


        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(MyServiceActivity.this,MySimpleService.class);
                stopService(intent);
            }
        });

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(MyServiceActivity.this,MySimpleService.class);
                startService(intent);
            }
        });
    }

    private void populateStudentList() {
        studentList.add("Sachin");
        studentList.add("Sehvagh");
        studentList.add("Gangully");
        studentList.add("Dravid");
        studentList.add("Dhoni");
        studentList.add("Yuvraj");
        studentList.add("Virat");
        studentList.add("Anil");
        studentList.add("Ghambhir");
        studentList.add("Rishabh");
        studentList.add("Harbhajan");
        studentList.add("zaheer");
        studentList.add("kartik");
        studentList.add("Gayle");
        studentAdapter =new StudentAdapter();
        rvList.setAdapter(studentAdapter);
    }

    public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.StudentHolder>{
        @Override
        public StudentHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(MyServiceActivity.this).inflate(R.layout.item_adapter,parent,false);
            return new StudentHolder(view);
        }

        @Override
        public void onBindViewHolder(final StudentHolder holder, int position) {
            holder.tvName.setText(studentList.get(position));

        }

        @Override
        public int getItemViewType(int position) {
            return position;
        }

        @Override
        public int getItemCount() {
            return studentList.size();
        }

        public class StudentHolder extends RecyclerView.ViewHolder{
            TextView tvName,tvValue;
            AppCompatSeekBar sbSlider;
            public StudentHolder(View itemView) {
                super(itemView);
                sbSlider = (AppCompatSeekBar)itemView.findViewById(R.id.sbSlider);
                tvName = (TextView)itemView.findViewById(R.id.tvName);
                tvValue = (TextView)itemView.findViewById(R.id.tvValue);
            }
        }
    }
}
