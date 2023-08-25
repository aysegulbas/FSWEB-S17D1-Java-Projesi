package com.workintech.rest.controller.entity;

public class Animal {
    private int id;
    private String name;

    private Animal(){
        //Spring containerda işaretlensin diye
    }

    public Animal(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
}
