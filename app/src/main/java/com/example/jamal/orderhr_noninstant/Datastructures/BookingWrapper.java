package com.example.jamal.orderhr_noninstant.Datastructures;

import java.io.Serializable;

/**
 * Created by jamal on 5/8/2018.
 */

public class BookingWrapper implements Serializable{

    private String model;
    private int pk;
    private Booking fields;

    public Booking getFields() {
        return fields;
    }

    public void setFields(Booking fields) {
        this.fields = fields;
    }

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

}
