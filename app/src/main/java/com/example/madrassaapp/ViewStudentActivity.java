package com.example.madrassaapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.madrassaapp.R.id;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class ViewStudentActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    Button SearchStudentBtn, CancelSearchBtn;

    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    EditText SearchField;
    List<Student> Slist = new ArrayList<Student>();
    Context C;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        try {

            Context C = this;
            setContentView(R.layout.activity_view_student);


            DBHandler Db = new DBHandler(this);
            Slist=Db.getAllStudents();


            //Getting views Over here
            SearchStudentBtn = findViewById(R.id.SearchBtn);
            CancelSearchBtn = findViewById((R.id.cancelSearchBtn));
            recyclerView = findViewById(R.id.StudentViewRecycler);
            SearchField = findViewById(R.id.SearchField);


            //Search On click listner
            SearchStudentBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   String searchData= SearchField.getText().toString();

                   if(searchData.equalsIgnoreCase(""))
                   {
                       Toast.makeText(ViewStudentActivity.this, "Please enter some text", Toast.LENGTH_SHORT).show();
                       return;
                   }

                   Slist=Db.getStudentByName(searchData);

                    adapter = new RecyclerStudentView(Slist,C) ;
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();

                }
            });

//            Cancel Button on click listener
            CancelSearchBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    //Testing purpose only
//                    Db.insertRandomRecordsForStudentOne();

                    Slist=Db.getAllStudents();
                    SearchField.setText("");
                    adapter = new RecyclerStudentView(Slist,C) ;
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
            });

            //Sir ka recycler view ka code samjh mei to wasa hi nhi aya but likh dia
            //---------------------------------------------------------
            recyclerView.setHasFixedSize(true);

            layoutManager = new LinearLayoutManager(ViewStudentActivity.this);

            recyclerView.setLayoutManager(layoutManager);

            adapter = new RecyclerStudentView(Slist,C) ;
            recyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();

        }
        catch (Exception E)
        {
            E.printStackTrace();
        }


//--------------------------------------------

    }
}