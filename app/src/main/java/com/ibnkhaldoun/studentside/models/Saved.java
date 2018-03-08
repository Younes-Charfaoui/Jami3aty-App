package com.ibnkhaldoun.studentside.models;


public class Saved {
    //todo : add code to use the saved object
    private long id;
    private String professor,text,date;

    public Saved(long id, String professor , String text , String date){
        this.id = id;
        this.professor = professor;
        this.text = text;
        this.date = date;
    }

    public long getId(){
        return this.id;
    }

    public String getText(){
        return this.text;
    }

    public String getProfessor(){
        return this.professor;
    }

    public String getDate(){
        return this.date;
    }
}
