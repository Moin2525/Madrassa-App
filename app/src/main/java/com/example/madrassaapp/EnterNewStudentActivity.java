package com.example.madrassaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EnterNewStudentActivity extends AppCompatActivity {

    EditText studentName, age, Class;
    Button cancel, save;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_new_student);

        studentName=findViewById(R.id.studentNameTextField);
        age=findViewById(R.id.AgeTextField);
        Class=findViewById(R.id.classTextField);
        save=findViewById(R.id.saveBtn);
        cancel=findViewById(R.id.cancelBtn);


        //Saving Student here
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                try {
                   String name= studentName.getText().toString();
                   int age_no = Integer.parseInt(age.getText().toString());
                   int Class_no = Integer.parseInt(Class.getText().toString());

                    if (age_no <= 0 || Class_no <= 0) {
                        Toast.makeText(EnterNewStudentActivity.this, "Age and class must be positive values", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    DBHandler dbHandler = new DBHandler(EnterNewStudentActivity.this);
                    boolean success = dbHandler.insertNewStudent(name,Class_no, age_no);

                    if (success) {
                        // Data inserted successfully
                        Toast.makeText(EnterNewStudentActivity.this, "Student data saved successfully", Toast.LENGTH_SHORT).show();
                    } else {
                        // Failed to insert data
                        Toast.makeText(EnterNewStudentActivity.this, "Failed to save student data", Toast.LENGTH_SHORT).show();
                    }


                }
                catch(Exception E)
                {
                    E.printStackTrace();
                }


            }
        });


        //Cancel here
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}