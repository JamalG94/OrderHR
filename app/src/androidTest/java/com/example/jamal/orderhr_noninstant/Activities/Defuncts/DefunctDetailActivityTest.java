//package com.example.jamal.orderhr_noninstant.Activities.Defuncts;
//
//import android.content.Intent;
//import android.os.Looper;
//import android.test.ActivityUnitTestCase;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//
//import org.junit.Before;
//import org.junit.Test;
//
//import static org.junit.Assert.*;
//
///**
// * Created by Robin on 5/23/2018.
// */
//public class DefunctDetailActivityTest extends ActivityUnitTestCase<DefunctDetailActivity> {
//    DefunctDetailActivity testtargetclass;
//
//    @Override
//    protected void setUp() throws Exception {
//        super.setUp();
//        Intent test = new Intent(getInstrumentation()
//                .getTargetContext(), DefunctDetailActivity.class);
//        startActivity(test, null, null);
////        final Button launchNextButton =
////                (Button) getActivity()
////                        .findViewById(R.id.launch_next_activity_button);
//    }
//
//    @Test
//    public void fillListWithFilteredItems() throws Exception {
//
//    }
//
//    @Test
//    public void IFillDataStructures() throws Exception {
//        testtargetclass.IFillDataStructures(new ObjectMapper(),"[{\"model\": \"web.defuncts\", \"pk\": 1, \"fields\": {\"type\": \"Missing\", \"handled\": false, \"description\": \"De tafel is weg\", \"room\": \"WN.04.003\"}}, {\"model\": \"web.defuncts\", \"pk\": 2, \"fields\": {\"type\": \"notype\", \"handled\": false, \"description\": \"What am I doing\", \"room\": \"WD.03.116\"}}, {\"model\": \"web.defuncts\", \"pk\": 3, \"fields\": {\"type\": \"notype\", \"handled\": false, \"description\": \"test\", \"room\": \"WD.1.016\"}}, {\"model\": \"web.defuncts\", \"pk\": 4, \"fields\": {\"type\": \"missing\", \"handled\": false, \"description\": \"wat\", \"room\": \"WD.1.016\"}}]");
//        assertEquals(testtargetclass.receiveddefuncts.size(),4);
//    }
//
//}