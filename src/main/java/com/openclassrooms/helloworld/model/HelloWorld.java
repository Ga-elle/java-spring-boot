package com.openclassrooms.helloworld.model;

public class HelloWorld {

    private String value = "He, how are you doing?!";

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString(){
        return value;
    }
}
