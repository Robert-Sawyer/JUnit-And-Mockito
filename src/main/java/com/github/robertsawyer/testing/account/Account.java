package com.github.robertsawyer.testing.account;

public class Account {

    private boolean active;
    private Address address;


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
}
