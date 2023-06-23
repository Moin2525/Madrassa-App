package com.example.madrassaapp;

import java.util.Date;

public class DailyTask {
    int Mazil;
    int Sabaqi;
    int para;

    Date D;

    public Date getD() {
        return D;
    }

    public void setD(Date d) {
        D = d;
    }

    public int getPara() {
        return para;
    }

    public void setPara(int para) {
        this.para = para;
    }

    public DailyTask(int mazil, int sabaqi, int para, String surahName, int startingVerse, int endingVerse) {
        Mazil = mazil;
        Sabaqi = sabaqi;
        this.para = para;
        SurahName = surahName;
        this.startingVerse = startingVerse;
        this.endingVerse = endingVerse;
    }

    public DailyTask(int mazil, int sabaqi, int para, Date d, String surahName, int startingVerse, int endingVerse) {
        Mazil = mazil;
        Sabaqi = sabaqi;
        this.para = para;
        D = d;
        SurahName = surahName;
        this.startingVerse = startingVerse;
        this.endingVerse = endingVerse;
    }

    String SurahName;

    public int getMazil() {
        return Mazil;
    }

    public void setMazil(int mazil) {
        Mazil = mazil;
    }

    public int getSabaqi() {
        return Sabaqi;
    }

    public void setSabaqi(int sabaqi) {
        Sabaqi = sabaqi;
    }

    public String getSurahName() {
        return SurahName;
    }

    public void setSurahName(String surahName) {
        SurahName = surahName;
    }

    public int getStartingVerse() {
        return startingVerse;
    }

    public void setStartingVerse(int startingVerse) {
        this.startingVerse = startingVerse;
    }

    public int getEndingVerse() {
        return endingVerse;
    }

    public void setEndingVerse(int endingVerse) {
        this.endingVerse = endingVerse;
    }

    int startingVerse;
    int endingVerse;

    public DailyTask(int mazil, int sabaqi, String surahName, int startingVerse, int endingVerse) {
        Mazil = mazil;
        Sabaqi = sabaqi;
        SurahName = surahName;
        this.startingVerse = startingVerse;
        this.endingVerse = endingVerse;
    }



}
