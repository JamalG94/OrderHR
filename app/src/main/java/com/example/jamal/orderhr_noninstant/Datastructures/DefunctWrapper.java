package com.example.jamal.orderhr_noninstant.Datastructures;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.Relation;

import java.io.Serializable;

/**
 * Created by Robin on 5/11/2018.
 */
@Entity
public class DefunctWrapper implements Serializable {
    @ColumnInfo(name="model")
    private String model;
    @PrimaryKey
    private int pk;
    @Embedded
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