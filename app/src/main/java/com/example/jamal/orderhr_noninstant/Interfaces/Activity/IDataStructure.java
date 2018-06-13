package com.example.jamal.orderhr_noninstant.Interfaces.Activity;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Created by jamal on 4/5/2018.
 */
//This interface means that the implementing class has certain datastructures that need to be filled using a certain mapper.
public interface IDataStructure {

    void IFillDataStructures(ObjectMapper objectMapper, String json);
}
