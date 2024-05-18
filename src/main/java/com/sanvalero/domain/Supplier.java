package com.sanvalero.domain;

import java.util.List;

public class Supplier {

    private int id;
    private String name;
    private String cif;
    private String phone;
    private String email;

    private List<Product> products;

    public Supplier() {

    }

    public Supplier(String name, String cif, String phone, String email) {
        this.name = name;
        this.cif = cif;
        this.phone = phone;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCif() {
        return cif;
    }

    public void setCif(String cif) {
        this.cif = cif;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
