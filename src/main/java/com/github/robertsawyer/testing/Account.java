package com.github.robertsawyer.testing;

public class Account {

    private boolean active;
    private Address address;

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
