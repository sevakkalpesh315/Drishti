package com.gkbhitech.drishti.dateslider;

import java.util.Calendar;

import android.content.Context;

/**
 * This is an abstract class whose job is create TimeViews that can be used
 * to populate a ScrollLayout, and to generate TimeObjects from times that
 * can be used to populate the TimeViews.
 */
public abstract class Labeler {
    /**
     * The default width of views that this labeler generates, in dp
     */
    private final int viewWidthDP;
    /**
     * The default height of views that this labeler generates, in dp
     */
    private final int viewHeightDP;
    
    protected int minuteInterval=1;

    /**
     * @param viewWidthDB The default width of views labeled by this labeler in dp
     * @param viewHeightDB The default height of views labeled by this labeler in dp
     */
    public Labeler(int viewWidthDP, int viewHeightDP) {
        this.viewWidthDP = viewWidthDP;
        this.viewHeightDP = viewHeightDP;
    }

    /**
     * Converts from a time to a TimeObject according to the rules of this labeler.
     *
     * @param time The time to display
     * @return the TimeObject representing "time" suitable for populating TimeViews
     * returned from {@link #createView(Context, boolean)}
     */
    public TimeObject getElem(long time) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(time);
        return timeObjectfromCalendar(c);
    }

    /**
     * Returns a new TimeView instance appropriate for population using TimeObjects
     * retrieved from {@link #getElem(long)}.
     *
     * @param context
     * @param isCenterView is true when the view is the central view
     * @return The new unpopulated TimeView object
     */
    public TimeView createView(Context context, boolean isCenterView) {
        return new TimeTextView(context, isCenterView, 25);
    }

    /**
     * This method adds "val" time units (where a time unit is the amount of time that
     * separates one TimeView generated by this labeler from the next -- e.g. if this
     * labeler is producing TimeViews each representing a day, then a time unit would
     * be a single day) to the specified time and returns a TimeObject representing
     * the result.
     *
     * This method will be called constantly, whenever new date information is required.
     *
     * @param time The time
     * @param val The number of units to add to the time
     * @return The resulting TimeObject
     */
    public abstract TimeObject add(long time, int val);
    /**
     * This method converts from a calendar to a TimeObject -- it does the actual
     * work of turning a point time into the range and display string that componse
     * a TimeObject.
     *
     * @param c The time to convert
     * @return The resulting TimeObject
     */
    protected abstract TimeObject timeObjectfromCalendar(Calendar c);

    /**
     * This method return the preferred width of TimeViews labeled by this labeler.
     *
     * @param context
     * @return The preferred width, in pixels
     */
    public int getPreferredViewWidth(Context context) {
        return (int)(viewWidthDP * context.getResources().getDisplayMetrics().density);
    }

    /**
     * This method return the preferred width of TimeViews labeled by this labeler.
     *
     * @param context
     * @return The preferred width, in pixels
     */
    public int getPreferredViewHeight(Context context) {
        return (int)(viewHeightDP * context.getResources().getDisplayMetrics().density);
    }
    
    /**
     * This method sets a minute interval to only show multiples of this number in any
     * minute slider 
     * 
     * @param minInterval
     */
    public void setMinuteInterval(int minInterval) {
    	this.minuteInterval = minInterval;
    }
}