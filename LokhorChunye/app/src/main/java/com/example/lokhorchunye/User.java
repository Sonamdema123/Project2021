package com.example.lokhorchunye;

public class User {
    public String name, email, password, confirm;

    public User(){

    }
    public User(String name, String email, String password, String confirm ){
        this.name = name;
        this.email = email;
        this.password = password;
        this.confirm = confirm;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirm() {
        return confirm;
    }

    public void setConfirm(String confirm) {
        this.confirm = confirm;
    }
}
