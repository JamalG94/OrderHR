package com.example.jamal.orderhr_noninstant.LocalDBControllers;

        import android.arch.lifecycle.LiveData;
        import android.arch.persistence.room.Dao;
        import android.arch.persistence.room.Delete;
        import android.arch.persistence.room.Insert;
        import android.arch.persistence.room.Query;

        import com.example.jamal.orderhr_noninstant.Datastructures.BookingWrapper;
        import com.example.jamal.orderhr_noninstant.Datastructures.DefunctWrapper;

        import java.util.List;

/**
 * Created by Robin on 6/5/2018.
 */
@Dao
public interface BookingDao {

    @Query("SELECT * FROM BookingWrapper")
    BookingWrapper[] getAll();

    @Insert
    void insertAll(BookingWrapper... bookingWrapper);

    @Query("DELETE FROM BookingWrapper")
    void deleteAll();
}

