package com.example.jamal.orderhr_noninstant.Datastructures;

import java.io.Serializable;

/**
 * Created by Robin on 5/11/2018.
 */

public class DefunctWrapper implements Serializable {

    private String model;
    private int pk;
    private Defunct fields;

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getPk() {
        return pk;
    }

    public void setPk(int pk) {
        this.pk = pk;
    }

    public Defunct getFields() {
        return fields;
    }

    public void setFields(Defunct fields) {
        this.fields = fields;
    }
}