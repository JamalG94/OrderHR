package com.example.jamal.orderhr_noninstant.Interfaces.Activity;

/**
 * Created by jamal on 6/6/2018.
 */

public interface ITableFiller<T> {

    void FillRows(T data);

    void ClearRows();
}
