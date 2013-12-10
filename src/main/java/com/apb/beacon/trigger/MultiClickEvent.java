package com.apb.beacon.trigger;


/**
 * Registers hardware clicks in the time interval
 */
public class MultiClickEvent {
    public static final int TIME_INTERVAL = 5000;
    private static final int TOTAL_CLICKS = 5;

    private Long firstEventTime;
    private int clickCount = 0;

    public void reset() {
    	firstEventTime = null;
    	clickCount = 0;
    }

    public void registerClick(Long eventTime) {
        if (isFirstClick() || notWithinLimit(eventTime)) {
            firstEventTime = eventTime;
            clickCount = 1;
            return;
        }
        clickCount++;
    }

    private boolean notWithinLimit(long current) {
        return (current - firstEventTime) > TIME_INTERVAL;
    }

    private boolean isFirstClick() {
        return firstEventTime == null;
    }

    public boolean isActivated() {
        return clickCount >= TOTAL_CLICKS;
    }
}
