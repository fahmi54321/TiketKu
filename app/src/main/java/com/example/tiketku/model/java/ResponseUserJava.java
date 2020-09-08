package com.example.tiketku.model.java;

import java.util.List;


// todo 3 membuat model
public class ResponseUserJava {
    private String nama;
    private String bio;
    private String photo;
    private String id_profile;
    private int no;
    private String username;
    private String password;
    private String email_address;
    private int money;
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail_address() {
        return email_address;
    }

    public void setEmail_address(String email_address) {
        this.email_address = email_address;
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public String getId_profile() {
        return id_profile;
    }

    public void setId_profile(String id_profile) {
        this.id_profile = id_profile;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public class ResponseUserJavaData
    {
        private List<ResponseUserJava> results;

        public List<ResponseUserJava> getResults() {
            return results;
        }

        public void setResults(List<ResponseUserJava> results) {
            this.results = results;
        }
    }
}
