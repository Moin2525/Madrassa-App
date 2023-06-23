package com.example.madrassaapp;
import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class DBHandler extends SQLiteOpenHelper {

    private final String createStudentsTable = "CREATE TABLE IF NOT EXISTS students (\n" +
            "    student_id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
            "    name TEXT,\n" +
            "    class INTEGER,\n" +
            "    age INTEGER\n" +
            ");";
    private final String createRecordsTable = "CREATE TABLE IF NOT EXISTS records (\n" +
            "    recordID INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
            "    studentId INTEGER,\n" +
            "    Manzil INTEGER,\n" +
            "    Sabqi INTEGER,\n" +
            "    ParaNo INTEGER,\n" +
            "    startingVerse INTEGER,\n" +
            "    endingVerse INTEGER,\n" +
            "    surahName TEXT,\n" +
            "    lesson_date DATE,\n" +
            "    FOREIGN KEY (studentId) REFERENCES students(student_id)\n" +
            ");";


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(createStudentsTable);
        db.execSQL(createRecordsTable);
    }
    private static final String DATABASE_NAME = "students.db";

    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS " + "students";
        String sql2 = "DROP TABLE IF EXISTS " + "records";
        db.execSQL(sql);
        db.execSQL(sql2);
        onCreate(db);
    }


    public boolean insertNewStudent(String name, int studentClass, int age) {
        
        try {


            SQLiteDatabase db = this.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put("name", name);
            values.put("class", studentClass);
            values.put("age", age);

            db.insert("students", null, values);

            db.close();
        }
        catch(Exception E)
        {
            return false;
        }

        return true;
        
    }


    //getting all students
    public ArrayList<Student> getAllStudents() {
        ArrayList<Student> studentList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;

        try {
            cursor = db.query("students", null, null, null, null, null, null);

            if (cursor != null && cursor.moveToFirst()) {
                int studentIdIndex = cursor.getColumnIndex("student_id");
                int nameIndex = cursor.getColumnIndex("name");
                int classIndex = cursor.getColumnIndex("class");
                int ageIndex = cursor.getColumnIndex("age");

                do {
                    int studentId = cursor.getInt(studentIdIndex);
                    String name = cursor.getString(nameIndex);
                    int studentClass = cursor.getInt(classIndex);
                    int age = cursor.getInt(ageIndex);

                    Student student = new Student(name, studentClass,studentId, age);
                    studentList.add(student);
                } while (cursor.moveToNext());
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }

        return studentList;
    }



    //getting students by name
    // getting students by name (case-insensitive) or whose name begins with the entered string
    public ArrayList<Student> getStudentByName(String name) {
        ArrayList<Student> matchingStudents = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;

        try {
            String selection = "name COLLATE NOCASE LIKE ?";
            String[] selectionArgs = {name + "%"};

            cursor = db.query("students", null, selection, selectionArgs, null, null, null);

            if (cursor != null && cursor.moveToFirst()) {
                int studentIdIndex = cursor.getColumnIndex("student_id");
                int nameIndex = cursor.getColumnIndex("name");
                int classIndex = cursor.getColumnIndex("class");
                int ageIndex = cursor.getColumnIndex("age");

                do {
                    int studentId = cursor.getInt(studentIdIndex);
                    String studentName = cursor.getString(nameIndex);
                    int studentClass = cursor.getInt(classIndex);
                    int age = cursor.getInt(ageIndex);

                    Student student = new Student(studentName, studentClass, studentId, age);
                    matchingStudents.add(student);
                } while (cursor.moveToNext());
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }

        return matchingStudents;
    }



    public void insertRandomRecordsForStudentOne() {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values1 = new ContentValues();
        values1.put("studentId",0);
        values1.put("Manzil", 3);
        values1.put("Sabqi", 1);
        values1.put("ParaNo", 5);
        values1.put("startingVerse", 10);
        values1.put("endingVerse", 20);
        values1.put("surahName", "Surah Al-Fatiha");
        values1.put("lesson_date", "2023-06-20");

        db.insert("records", null, values1);

        ContentValues values2 = new ContentValues();
        values2.put("studentId", 1);
        values2.put("Manzil", 2);
        values2.put("Sabqi", 2);
        values2.put("ParaNo", 10);
        values2.put("startingVerse", 30);
        values2.put("endingVerse", 40);
        values2.put("surahName", "Surah Al-Baqarah");
        values2.put("lesson_date", "2023-06-21");

        db.insert("records", null, values2);

        db.close();
    }

    public ArrayList<DailyTask> getStudentRecord(int id) {
        ArrayList<DailyTask> studentRecords = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;

        try {
            String[] columns = {"Manzil", "Sabqi", "ParaNo", "surahName", "startingVerse", "endingVerse", "lesson_date"};
            String selection = "studentId=?";
            String[] selectionArgs = {String.valueOf(id)};

            cursor = db.query("records", columns, selection, selectionArgs, null, null, null);

            if (cursor != null && cursor.moveToFirst()) {
                int manzilIndex = cursor.getColumnIndex("Manzil");
                int sabqiIndex = cursor.getColumnIndex("Sabqi");
                int paraIndex = cursor.getColumnIndex("ParaNo");
                int surahNameIndex = cursor.getColumnIndex("surahName");
                int startingVerseIndex = cursor.getColumnIndex("startingVerse");
                int endingVerseIndex = cursor.getColumnIndex("endingVerse");
                int dateIndex = cursor.getColumnIndex("lesson_date");

                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

                do {
                    int manzil = cursor.getInt(manzilIndex);
                    int sabqi = cursor.getInt(sabqiIndex);
                    int para = cursor.getInt(paraIndex);
                    String surahName = cursor.getString(surahNameIndex);
                    int startingVerse = cursor.getInt(startingVerseIndex);
                    int endingVerse = cursor.getInt(endingVerseIndex);
                    String dateString = cursor.getString(dateIndex);

                    Date date;
                    try {
                        date = dateFormat.parse(dateString);
                    } catch (ParseException e) {
                        date = new Date();
                    }

                    DailyTask task = new DailyTask(manzil, sabqi, para, surahName, startingVerse, endingVerse);
                    task.setD(date);
                    studentRecords.add(task);
                } while (cursor.moveToNext());
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }

        return studentRecords;
    }

    public DailyTask getLatestRecordById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        DailyTask latestRecord = null;

        try {
            String[] columns = {"Manzil", "Sabqi", "ParaNo", "surahName", "startingVerse", "endingVerse", "lesson_date"};
            String selection = "studentId=?";
            String[] selectionArgs = {String.valueOf(id)};
            String orderBy = "lesson_date DESC";
            String limit = "1";

            cursor = db.query("records", columns, selection, selectionArgs, null, null, orderBy, limit);

            if (cursor != null && cursor.moveToFirst()) {
                int manzilIndex = cursor.getColumnIndex("Manzil");
                int sabqiIndex = cursor.getColumnIndex("Sabqi");
                int paraIndex = cursor.getColumnIndex("ParaNo");
                int surahNameIndex = cursor.getColumnIndex("surahName");
                int startingVerseIndex = cursor.getColumnIndex("startingVerse");
                int endingVerseIndex = cursor.getColumnIndex("endingVerse");
                int dateIndex = cursor.getColumnIndex("lesson_date");

                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

                int manzil = cursor.getInt(manzilIndex);
                int sabqi = cursor.getInt(sabqiIndex);
                int para = cursor.getInt(paraIndex);
                String surahName = cursor.getString(surahNameIndex);
                int startingVerse = cursor.getInt(startingVerseIndex);
                int endingVerse = cursor.getInt(endingVerseIndex);
                String dateString = cursor.getString(dateIndex);

                Date date;
                try {
                    date = dateFormat.parse(dateString);
                } catch (ParseException e) {
                    date = new Date();
                }

                latestRecord = new DailyTask(manzil, sabqi, para, surahName, startingVerse, endingVerse);
                latestRecord.setD(date);
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }

        return latestRecord;
    }

    public void insertRecord(int studentId, DailyTask dailyTask) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("studentId", studentId);
        values.put("Manzil", dailyTask.getMazil());
        values.put("Sabqi", dailyTask.getSabaqi());
        values.put("ParaNo", dailyTask.getPara());
        values.put("startingVerse", dailyTask.getStartingVerse());
        values.put("endingVerse", dailyTask.getEndingVerse());
        values.put("surahName", dailyTask.getSurahName());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String dateString = dateFormat.format(dailyTask.getD());
        values.put("lesson_date", dateString);

        db.insert("records", null, values);
        db.close();
    }

};
