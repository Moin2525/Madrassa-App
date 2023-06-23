package com.example.madrassaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MenuActivity extends AppCompatActivity {


    Button newStudent, EnterRec, ViewRec;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        //getting views by id

        newStudent = findViewById(R.id.newStuBtn);
        EnterRec = findViewById(R.id.enterRecordBtn);

        newStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent I= new Intent(MenuActivity.this, EnterNewStudentActivity.class );
                startActivity(I);
            }
        });

        EnterRec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent I= new Intent(MenuActivity.this, ViewStudentActivity.class );

                startActivity(I);
            }
        });
    }
}