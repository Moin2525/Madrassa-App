package com.example.madrassaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DailyRecordEntry extends AppCompatActivity {

    private TextView dateTextView;
    private Button pickDateButton;
    int year, month, day;
    String selectedValue;
    EditText Para, Sverse, Everse;
    CheckBox manzil;
    Spinner Surah;

    Button submit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_record_entry);

        //getting the submit over here
        submit = findViewById(R.id.SubmitRecord);

        //getting manzil check over here
        manzil = findViewById(R.id.checkBox);


        //getting para
        Para = findViewById(R.id.ParaEnterField);

        //verses
        Sverse = findViewById(R.id.SVerse);
        Everse = findViewById(R.id.Everse);

        Spinner mySpinner = findViewById(R.id.spinner);

        //handling spinner views over here
        ArrayList<String> dataList = QuranSurah.convertArrayToList();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, dataList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mySpinner.setAdapter(adapter);


        mySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedValue = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
               selectedValue = parent.getItemAtPosition(0).toString();
            }
        });


        //Handling dates over here
        dateTextView = findViewById(R.id.HistoryDate);
        pickDateButton = findViewById(R.id.HistorydateBtn);

        pickDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });
    }


    //Date Diologue box
    private void showDatePickerDialog() {
        final Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);


        //Date Picker Done
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay) {
                        // Here, you can handle the selected date
                        calendar.set(selectedYear, selectedMonth, selectedDay);
                        String formattedDate = DateFormat.getDateInstance().format(calendar.getTime());
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                        formattedDate = dateFormat.format(calendar.getTime());
                        dateTextView.setText(formattedDate);
                    }
                },
                year, month, day
        );

        datePickerDialog.show();



        //Getting id from intent
        Intent I = getIntent();
        int id = I.getIntExtra("id",-1);


        DBHandler Db = new DBHandler(this);
        try {

            //Throwing error if id is -1
            if(id==-1) throw new Exception();

            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    //Checking if there is any pre record
                    boolean isEmptyRec=false;
                    //Getting the latest record over here
                    int newManzil=0, newSabqi=0;
                    Date date;
                    DailyTask Dold = Db.getLatestRecordById(id);

                    //new will store our new record
                    DailyTask Dnew ;

                    if(Dold==null) {
                        Toast.makeText(DailyRecordEntry.this, "The given record is empty", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        newManzil=Dold.getMazil();
                    }

                    // To check if the CheckBox is checked or not
                    boolean isChecked = manzil.isChecked();

                    // To perform an action based on the CheckBox state
                    if (isChecked) {
                        newManzil+=1;
                    }

                    //Hnadled para over
                    Db.getLatestRecordById(id);
                    int parahTmp= Integer.parseInt(Para.getText().toString());
                    int endV = Integer.parseInt(Everse.getText().toString());
                    int startV =Integer.parseInt(Sverse.getText().toString());
                    newSabqi=parahTmp;

                    if(parahTmp == 0)
                    {
                        Toast.makeText(DailyRecordEntry.this, "Para is empty", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if(startV>endV)
                    {
                        Toast.makeText(DailyRecordEntry.this, "Starting cannot be greater then ending", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if(parahTmp>1)
                    {
                        newSabqi-=1;
                    }
                    if(newManzil>parahTmp)
                    {
                        newManzil=1;
                    }

                    String pattern = "yyyy-MM-dd";

                    SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);

                    date = new Date();

                    try {

                        date = dateFormat.parse(dateTextView.getText().toString());
                        System.out.println("Parsed Date: " + date);
                    }
                    catch(Exception E) {
                        Toast.makeText(DailyRecordEntry.this, "This is Date pasrse", Toast.LENGTH_LONG).show();
                        E.printStackTrace();
                        return;
                    }


                    Dnew = new DailyTask(newManzil,newSabqi,parahTmp,date,selectedValue,startV,endV);

                    //Bismillah Allah khair kre sara code chal jye shi :)

                    Db.insertRecord(id,Dnew);
                }
            });


        }
        catch(Exception E)
        {
            E.printStackTrace();
        }
    }




}