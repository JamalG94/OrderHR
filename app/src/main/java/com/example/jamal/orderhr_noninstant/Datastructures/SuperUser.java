package com.example.jamal.orderhr_noninstant.Datastructures;

/**
 * Created by jamal on 5/24/2018.
 */

public class SuperUser {

    private String concreteUser;

    public void SetTypeUser(String type){
        concreteUser = type;
    }

    public String TypeOfUser(){
        return concreteUser;
    }


}

