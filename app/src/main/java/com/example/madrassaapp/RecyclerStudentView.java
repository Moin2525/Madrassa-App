package com.example.madrassaapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecyclerStudentView extends RecyclerView.Adapter<RecyclerStudentView.MyVH> {

    List<Student> SList;
    Context context;
     public RecyclerStudentView (List<Student> l, Context context)
    {
        this.context = context;
        SList=l;
    }


    @NonNull
    @Override
    public RecyclerStudentView.MyVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.student_single_item, parent,false);

        final MyVH viewHolder = new MyVH(itemView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyVH holder, int position) {
            holder.S=SList.get(position);

            Integer.toString(holder.S.getStudentClass());
            holder.Class.setText("Class : "+Integer.toString(holder.S.getStudentClass()));
            holder.name.setText(holder.S.getName());
            holder.age.setText("Age :"+Integer.toString(holder.S.getAge()));

        // Add click listener to ViewStudentRecord button
        holder.ViewStudentRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Start the activity
                Intent intent = new Intent(context, ViewHistory.class);
                intent.putExtra("id", holder.S.getId());
                intent.putExtra("name",holder.S.getName());
                intent.putExtra("class",holder.S.getStudentClass());
                intent.putExtra("age",holder.S.getStudentClass());
                context.startActivity(intent);
            }
        });

        holder.EnterStudentRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Start the activity
                Intent intent = new Intent(context, DailyRecordEntry.class);
                intent.putExtra("id", holder.S.getId());
                context.startActivity(intent);
            }
        });

    }



    @Override
    public int getItemCount() {
        return SList.size();
    }


    //This is my ViewHolder
    public class MyVH extends RecyclerView.ViewHolder
    {
        TextView name;
        TextView Class;
        TextView age;
        Button EnterStudentRecord, ViewStudentRecord;
        int id;
        Student S;


        public MyVH(@NonNull View itemView) {
            super(itemView);

            name=itemView.findViewById(R.id.textViewFriendName);
            Class = itemView.findViewById(R.id.textViewClass);
            age=itemView.findViewById(R.id.textViewAge);
            EnterStudentRecord=itemView.findViewById(R.id.EnterStudentRecordBtn);
            ViewStudentRecord = itemView.findViewById(R.id.ViewStudentRecordBtn);

        }
    }

};
