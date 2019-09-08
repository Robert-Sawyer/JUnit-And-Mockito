package com.github.robertsawyer.testing.account;

public class Account {

    private boolean active;
    private Address address;
    private String email;


    public Account(Address address) {
        this.address = address;
        if (address != null) {
            activate();
        } else {
            this.active = false;
        }
    }

    public Account() {
        this.active = false;
    }

    public void activate(){
        this.active = true;
    }

    public boolean isActive(){
        return this.active;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public void setEmail(String email) {
        if (email.matches("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$")) {
            this.email = email;
        } else {
            throw new IllegalArgumentException("Niepoprawny email");
        }
    }

    public String getEmail() {
        return email;
    }
}
