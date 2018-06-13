package com.example.jamal.orderhr_noninstant.LocalDBControllers;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Room;
import android.os.AsyncTask;

import com.example.jamal.orderhr_noninstant.Datastructures.Booking;
import com.example.jamal.orderhr_noninstant.Datastructures.BookingWrapper;
import com.example.jamal.orderhr_noninstant.Datastructures.DefunctWrapper;

import java.util.List;

/**
 * Created by Robin on 6/5/2018.
 */

//This class binds all interfaces for our local database together, and implements async calls to the local database
public class LocalDatabaseRepository {
    private DefunctDao mDefunctDao;
    private BookingDao mBookingDao;
    private List<DefunctWrapper> mAllDefuncts;
    private BookingWrapper[] mAllBooking;
    public LocalDatabaseRepository(Application application){
        appDataBaseLocal db = appDataBaseLocal.getDatabase(application);
        mDefunctDao = db.defunctDao();
        mBookingDao = db.BookingDao();
    }
    public List<DefunctWrapper> getmAllDefuncts(){
        selectallAsyncTask test = new selectallAsyncTask(mDefunctDao);
        try{
            mAllDefuncts = test.execute().get();
        }catch(Exception e){

        }
        return mAllDefuncts;
    }
    public BookingWrapper[] getmAllBookings(){
        selectallBookingsAsyncTask test = new selectallBookingsAsyncTask(mBookingDao);
        try{
            mAllBooking = test.execute().get();
        }catch(Exception e){

        }
        return mAllBooking;
    }
    public void insertBooking(BookingWrapper booking){
        new insertBookingAsyncClass(mBookingDao).execute(booking);
    }
    public void insert(DefunctWrapper defunct){
        new insertAsyncTask(mDefunctDao).execute(defunct);
    }
    public void synchronizeDatabaseWithNewDataAfterDelete(List<DefunctWrapper> defuncts){
        new synchronizeDatabaseTask(mDefunctDao).execute(defuncts);
    }
    public void synchronizeBookingDatabaseWithNewDataAfterDelete(BookingWrapper[] bookings){
        new synchronizeBookingDatabaseTask(mBookingDao).execute(bookings);
    }
    private static class insertBookingAsyncClass extends AsyncTask<BookingWrapper,Void,Void>{
        private BookingDao mAsyncTaskDao;
        insertBookingAsyncClass(BookingDao dao){
            mAsyncTaskDao = dao;
        }
        @Override
        protected Void doInBackground(final BookingWrapper... params){
            mAsyncTaskDao.insertAll(params);
            return null;
        }
    }
    private static class insertAsyncTask extends AsyncTask<DefunctWrapper,Void,Void>{
        private DefunctDao mAsyncTaskDao;
        insertAsyncTask(DefunctDao dao){
            mAsyncTaskDao = dao;
        }
        @Override
        protected Void doInBackground(final DefunctWrapper ... params){
            mAsyncTaskDao.insertAll(params);
            return null;
        }
    }
    private static class selectallAsyncTask extends AsyncTask<Void, Void,List<DefunctWrapper>>{
        private DefunctDao mAsyncTaskDao;
        selectallAsyncTask(DefunctDao dao){
            mAsyncTaskDao = dao;
        }
        @Override
        protected List<DefunctWrapper> doInBackground(Void... params){
            return mAsyncTaskDao.getAll();
        }
    }
    //deletes and loads new database table defuncts
    private static class synchronizeDatabaseTask extends AsyncTask<List<DefunctWrapper>,Void,Void>{
        private DefunctDao mAsyncTaskDao;
        synchronizeDatabaseTask(DefunctDao dao){
            mAsyncTaskDao = dao;
        }
        @Override
        protected Void doInBackground(final List<DefunctWrapper> ... params){
            mAsyncTaskDao.deleteAll();
            for (DefunctWrapper wrappers : params[0]) {
                mAsyncTaskDao.insertAll(wrappers);
            }
            return null;
        }
    }
    //deletes and loads new database table booking
    private static class synchronizeBookingDatabaseTask extends AsyncTask<BookingWrapper[],Void,Void>{
        private BookingDao mAsyncTaskDao;
        synchronizeBookingDatabaseTask(BookingDao dao){
            mAsyncTaskDao = dao;
        }
        @Override
        protected Void doInBackground(final BookingWrapper[] ... params){
            mAsyncTaskDao.deleteAll();
            for (BookingWrapper wrappers : params[0]) {
                mAsyncTaskDao.insertAll(wrappers);
            }
            return null;
        }
    }
    private static class selectallBookingsAsyncTask extends AsyncTask<Void,Void,BookingWrapper[]>{
        private BookingDao mAsyncTaskDao;
        selectallBookingsAsyncTask(BookingDao dao){
            mAsyncTaskDao = dao;
        }
        @Override
        protected BookingWrapper[] doInBackground(Void... params){
            return mAsyncTaskDao.getAll();
        }
    }
}
