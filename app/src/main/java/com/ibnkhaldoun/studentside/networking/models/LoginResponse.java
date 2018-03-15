package com.ibnkhaldoun.studentside.networking.models;


import com.ibnkhaldoun.studentside.models.Professor;
import com.ibnkhaldoun.studentside.models.Student;

public class LoginResponse extends Response{
    private boolean isStudent;
    private Student student;
    private Professor professor;

    public LoginResponse(int status) {
        super(status);
    }

    public boolean isStudent() {
        return isStudent;
    }

    public void setIsStudent(boolean student) {
        isStudent = student;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Professor getProfessor() {
        return professor;
    }

    public void setProfessor(Professor professor) {
        this.professor = professor;
    }
}
