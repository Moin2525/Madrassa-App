package com.example.madrassaapp;

import java.util.ArrayList;

public class Student {
    private String name;
    private int studentClass;
    private int id;
    private int age;
    ArrayList<DailyTask> history = null;

    public ArrayList<DailyTask> getHistory() {
        return history;
    }

    public void setHistory(ArrayList<DailyTask> history) {
        this.history = history;
    }

    public Student(String name, int studentClass, int id, int age, ArrayList<DailyTask> history) {
        this.name = name;
        this.studentClass = studentClass;
        this.id = id;
        this.age = age;
        this.history = history;
    }

    public Student(String name, int studentClass, int id, int age) {
        this.name = name;
        this.studentClass = studentClass;
        this.id = id;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStudentClass() {
        return studentClass;
    }

    public void setStudentClass(int studentClass) {
        this.studentClass = studentClass;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Student(String name, int studentClass, int age) {
        this.name = name;
        this.studentClass = studentClass;
        this.age = age;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
