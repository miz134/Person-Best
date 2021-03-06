package com.example.team31_personalbest_ms2v2;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

/**
 * Clock class is an AsyncTask that offloads a timer task onto a thread
 * to continuously update the timer text on WalkRunActivity
 */
public class Timer extends AsyncTask<String, String, String> {

    private String resp;
    private boolean isCancelled;
    // Holds the number of seconds since beginning
    private long time = 0;

    // Hours/Minutes/Seconds displayed in 00:00:00 format
    private int hours;
    private int minutes;
    private int seconds;
    private TextView timeDisplay;
    private FitnessService fitnessService;

    public Timer(TextView tv, FitnessService fitnessService) {
        this.timeDisplay = tv;
        this.fitnessService = fitnessService;
    }

    public void cancel() {
        isCancelled = true;
    }


    /**
     * Updates the timer while session still occurring
     */
    @Override
    protected String doInBackground(String... params) {
        // Loop that iterates each second to update time
        while (true) {
            if (isCancelled) break;
            // Updates time display
            updateTime();
            publishProgress(getTime());
            // Waits for 1 second before each update
            try {
                Thread.sleep(Constants.MS_PER_SEC);
                time++;
                Log.i("timer: ", String.valueOf(time));
            } catch (Exception e) {
                e.printStackTrace();
                resp = e.getMessage();
            }
        }
        return resp;
    }

    /**
     * present so that we can extend the classs
     */
    @Override
    protected void onPreExecute() {
        // Doesn't need to to anything before : - )
    }

    /**
     * updating timeDisplay so that the text is updated on the activity
     */
    @Override
    protected void onProgressUpdate(String ... s) {
        timeDisplay.setText(s[0]);
    }

    /**
     * Format time to pad 1 digits numbers with a 0 e.g. "01, 02"
     * @return Current time formatted in 00:00:00
     */
    public String getTime() {
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }

    /**
     * @return the number of total seconds
     */
    public long getSeconds() {
        return time;
    }
    /**
     * Incrementing seconds, minutes, and hours if seconds
     * or minutes overflows above 60
     */
    public void updateTime() {
        int temp = (int) time;
        // Updates hours
        hours = temp / Constants.SECS_PER_HOUR;
        temp = temp % Constants.SECS_PER_HOUR;
        // Updates minutes
        minutes = temp / Constants.SECS_PER_MIN;
        temp = temp % Constants.SECS_PER_MIN;
        // Updates seconds
        seconds = temp;

        fitnessService.updateStepCount();

        Log.i("timer value: ", hours + " " + minutes + " " + seconds);
    }

    /**
     * present so that we can extend the class
     */
    @Override
    protected void onPostExecute(String result) {
        // Doesn't need to to anything after finishing : - )
    }
}