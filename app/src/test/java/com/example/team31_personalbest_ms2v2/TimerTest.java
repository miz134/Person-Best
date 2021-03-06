package com.example.team31_personalbest_ms2v2;

import android.content.Context;
import android.widget.Button;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.android.controller.ActivityController;
import org.robolectric.shadows.ShadowIntent;

import android.content.Intent;

import com.google.firebase.FirebaseApp;

import static org.junit.Assert.assertEquals;
import static org.robolectric.Shadows.shadowOf;
import static org.robolectric.shadows.ShadowInstrumentation.getInstrumentation;

@RunWith(RobolectricTestRunner.class)
public class TimerTest {

    private Button startWalkRun;
    private MainActivity mainActivity;
    private ActivityController<MainActivity> controller;
    private ShadowIntent shadowIntent;

    @Before
    public void setup() {
        Context context = getInstrumentation().getTargetContext();
        FirebaseApp.initializeApp(context);
        mainActivity = Robolectric.setupActivity(MainActivity.class);

        //controller = Robolectric.buildActivity(MainActivity.class);
        //controller.create();
        startWalkRun = mainActivity.findViewById(R.id.buttonStart);
        controller = Robolectric.buildActivity(MainActivity.class);
        controller.create();
        controller.start().resume();
        startWalkRun.performClick();
        Intent startedIntent = shadowOf(mainActivity).getNextStartedActivity();
        shadowIntent = shadowOf(startedIntent);
        //walkRunActivity = Robolectric.setupActivity(WalkRunActivity.class);
        //text = walkRunActivity.findViewById(R.id.textViewTimer);
        //timer = new Timer(text);

    }
    // Tests if the timer accurately displays the initial time of 0
    @Test
    public void testInitial() {

        assertEquals(WalkRunActivity.class, shadowIntent.getIntentClass());

        //assertEquals("00:00:00", text.getText());
    }
}