package com.example.jamal.orderhr_noninstant.Activities.Defuncts;

import android.support.test.runner.AndroidJUnit4;

import com.example.jamal.orderhr_noninstant.Datastructures.Defunct;
import com.example.jamal.orderhr_noninstant.Datastructures.DefunctWrapper;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Created by Robin on 5/23/2018.
 */

@RunWith(AndroidJUnit4.class)
public class ViewDefunctDetailsActivityTest {
    ArrayList<DefunctWrapper> listofwraps;
    @Before
    public void setUp() throws Exception {
        listofwraps  = new ArrayList<>();
        //1
        DefunctWrapper wrapwrap1 = new DefunctWrapper();
        wrapwrap1.setPk(1);
        Defunct towrap1 = new Defunct();
        towrap1.setType("Missing");
        towrap1.setHandled(true);
        wrapwrap1.setFields(towrap1);
        //2
        DefunctWrapper wrapwrap2 = new DefunctWrapper();
        wrapwrap2.setPk(2);
        Defunct towrap2 = new Defunct();
        towrap2.setHandled(true);
        towrap2.setType("No Type");
        wrapwrap2.setFields(towrap2);
        //3
        DefunctWrapper wrapwrap3 = new DefunctWrapper();
        wrapwrap3.setPk(3);
        Defunct towrap3 = new Defunct();
        towrap3.setType("Missing");
        towrap3.setHandled(false);
        wrapwrap3.setFields(towrap3);
        //4
        DefunctWrapper wrapwrap4 = new DefunctWrapper();
        wrapwrap4.setPk(4);
        Defunct towrap4 = new Defunct();
        towrap4.setType("Software");
        towrap4.setHandled(true);
        wrapwrap4.setFields(towrap4);
        //5
        DefunctWrapper wrapwrap5 = new DefunctWrapper();
        wrapwrap5.setPk(5);
        Defunct towrap5 = new Defunct();
        towrap5.setType("No Type");
        towrap5.setHandled(true);
        wrapwrap5.setFields(towrap5);

        listofwraps.add(wrapwrap1);
        listofwraps.add(wrapwrap2);
        listofwraps.add(wrapwrap3);
        listofwraps.add(wrapwrap4);
        listofwraps.add(wrapwrap5);
    }
    
    @Test
    public void FilteredMissingWithHandled(){
        assertEquals(0, ViewDefunctDetailsActivity.FillListWithFilteredItems("broken",true,listofwraps).size());

        assertEquals(1, ViewDefunctDetailsActivity.FillListWithFilteredItems("soFtwArE",true,listofwraps).size());

        assertEquals(2, ViewDefunctDetailsActivity.FillListWithFilteredItems("MiSSinG",true,listofwraps).size());
    }

    @Test
    public void FilteredMissingWithoutHandled(){
        assertEquals(1, ViewDefunctDetailsActivity.FillListWithFilteredItems("MiSSinG",false,listofwraps).size());

        assertEquals(0, ViewDefunctDetailsActivity.FillListWithFilteredItems("soFtwArE",false,listofwraps).size());

        assertEquals(0, ViewDefunctDetailsActivity.FillListWithFilteredItems("broken",false,listofwraps).size());
    }

    @Test
    public void NoFilterWithHandled(){
        assertEquals(2, ViewDefunctDetailsActivity.FillListWithFilteredItems("No Type",true,listofwraps).size());
    }

    @Test
    public void NoFilterWithoutHandled(){
        assertEquals(0, ViewDefunctDetailsActivity.FillListWithFilteredItems("No Type",false,listofwraps).size());
    }

}