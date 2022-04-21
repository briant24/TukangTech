package com.teknologi.tukang;

public class HandlerUser {
    public String nama,email,password;
    public int isAdmin;

    public HandlerUser(){

    }

    public HandlerUser(String nama, String email, String password, int isAdmin){
        this.nama = nama;
        this.email = email;
        this.password = password;
        this.isAdmin = isAdmin;
    }
}

