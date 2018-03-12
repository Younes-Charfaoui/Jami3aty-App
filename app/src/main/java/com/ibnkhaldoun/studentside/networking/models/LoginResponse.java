package com.ibnkhaldoun.studentside.networking.models;


import com.ibnkhaldoun.studentside.models.Professor;
import com.ibnkhaldoun.studentside.models.Student;

import static com.ibnkhaldoun.studentside.providers.KeyDataProvider.NETWORK_EMAIL_ERROR;
import static com.ibnkhaldoun.studentside.providers.KeyDataProvider.NETWORK_PASSWORD_ERROR;
import static com.ibnkhaldoun.studentside.providers.KeyDataProvider.NETWORK_SUCCESS;

public class LoginResponse {
    private int status;
    private boolean isStudent;
    private Student student;
    private Professor professor;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public boolean isSuccess() {
        return this.status == NETWORK_SUCCESS;
    }

    public boolean inEmailError() {
        return this.status == NETWORK_EMAIL_ERROR;
    }

    public boolean inPasswordError() {
        return this.status == NETWORK_PASSWORD_ERROR;
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
