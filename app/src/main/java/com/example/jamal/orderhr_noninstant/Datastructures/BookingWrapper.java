package com.example.jamal.orderhr_noninstant.Datastructures;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;

/**
 * Created by jamal on 5/8/2018.
 */
@Entity
public class BookingWrapper implements Serializable{
    @ColumnInfo(name="model")
    private String model;
    @PrimaryKey
    private int pk;
    @Embedded
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
