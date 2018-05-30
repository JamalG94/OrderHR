package com.example.jamal.orderhr_noninstant.Activities.Defuncts;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Looper;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.test.ActivityUnitTestCase;

import com.example.jamal.orderhr_noninstant.Datastructures.Defunct;
import com.example.jamal.orderhr_noninstant.Datastructures.DefunctWrapper;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Created by Robin on 5/23/2018.
 */

@RunWith(AndroidJUnit4.class)
public class DefunctDetailActivityTest {
    ArrayList<DefunctWrapper> listofwraps;
    @Before
    public void setUp() throws Exception {
        listofwraps  = new ArrayList<>();
        DefunctWrapper wrapwrap1 = new DefunctWrapper();
        wrapwrap1.setPk(1);
        wrapwrap1.setModel("web.defuncts");
        Defunct towrap1 = new Defunct();
        towrap1.setType("Missing");
        towrap1.setDescription("ABCDEF");
        towrap1.setHandled(true);
        wrapwrap1.setFields(towrap1);
        DefunctWrapper wrapwrap2 = new DefunctWrapper();
        wrapwrap2.setPk(2);
        wrapwrap2.setModel("web.defuncts");
        Defunct towrap2 = new Defunct();
        towrap2.setHandled(true);
        towrap2.setType("No Type");
        towrap2.setDescription("ABCDEF");
        wrapwrap2.setFields(towrap2);
        DefunctWrapper wrapwrap3 = new DefunctWrapper();
        wrapwrap3.setPk(3);
        wrapwrap3.setModel("web.defuncts");
        Defunct towrap3 = new Defunct();
        towrap3.setType("Missing");
        towrap3.setDescription("ABCDEF");
        towrap3.setHandled(false);
        wrapwrap3.setFields(towrap3);
        listofwraps.add(wrapwrap1);
        listofwraps.add(wrapwrap2);
        listofwraps.add(wrapwrap3);
    }

    @Test
    public void fillListWithFilteredItems() throws Exception {
        assertEquals(2,DefunctDetailActivity.FillListWithFilteredItems("MiSSinG",true,listofwraps).size());

    }

    @Test
    public void IFillDataStructures() throws Exception {

//        DefunctDetailActivity.IFillDataStructures(new ObjectMapper(),"[{\"model\": \"web.defuncts\", \"pk\": 1, \"fields\": {\"type\": \"Missing\", \"handled\": false, \"description\": \"De tafel is weg\", \"room\": \"WN.04.003\"}}, {\"model\": \"web.defuncts\", \"pk\": 2, \"fields\": {\"type\": \"notype\", \"handled\": false, \"description\": \"What am I doing\", \"room\": \"WD.03.116\"}}, {\"model\": \"web.defuncts\", \"pk\": 3, \"fields\": {\"type\": \"notype\", \"handled\": false, \"description\": \"test\", \"room\": \"WD.1.016\"}}, {\"model\": \"web.defuncts\", \"pk\": 4, \"fields\": {\"type\": \"missing\", \"handled\": false, \"description\": \"wat\", \"room\": \"WD.1.016\"}}]");
//        assertEquals(testtargetclass.receiveddefuncts.size(),4);
    }

}