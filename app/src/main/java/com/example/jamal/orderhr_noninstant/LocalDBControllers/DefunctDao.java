package com.example.jamal.orderhr_noninstant.LocalDBControllers;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.jamal.orderhr_noninstant.Datastructures.DefunctWrapper;

import java.util.List;

/**
 * Created by Robin on 6/7/2018.
 */

//Interface for the defunct table of the local ROOM database
@Dao
public interface DefunctDao {
    @Query("SELECT * FROM defunctwrapper")
    List<DefunctWrapper> getAll();

    @Insert
    void insertAll(DefunctWrapper... defunctWrapper);

    @Query("DELETE FROM defunctwrapper")
    void deleteAll();
}