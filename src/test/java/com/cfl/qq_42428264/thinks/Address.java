package com.cfl.qq_42428264.thinks;

public class Address implements Cloneable{
    private String address;

    public Address(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    protected Address clone() throws CloneNotSupportedException {
        return (Address)super.clone();
    }

    @Override
    public String toString() {
        return "Address{" +
                "address='" + address + '\'' +
                '}';
    }
}
