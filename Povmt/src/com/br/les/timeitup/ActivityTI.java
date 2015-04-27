
package com.br.les.timeitup;

import java.util.Calendar;

public class ActivityTI implements Comparable<ActivityTI> {

    private String name;
    private int hour;
    private int minute;
    private int priority;
    private final int mDayOfYear;
    private final Calendar c = Calendar.getInstance();

    /**
     * Creator for a new ActivityTI.
     * 
     * @param name - The name of this Activity.
     * @param minute
     * @param hour - The time spent on this Activity
     */
    public ActivityTI(final String name, final int hour, final int minute,
            final int priority) {
        this.name = name;
        this.hour = hour;
        this.minute = minute;
        this.priority = priority;
        mDayOfYear = c.get(Calendar.DAY_OF_YEAR);
    }

    /**
     * Creator for a new ActivityTI.
     * 
     * @return the number of hashcode.
     */
    @Override
    public final int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (name == null ? 0 : name.hashCode());
        return result;
    }

    /**
     * Equals for the ActivityTI.
     * 
     * @param obj - The object to compare.
     * @return - the result of comparison
     */
    @Override
    public final boolean equals(final Object obj) {
        boolean isEqual = true;
        if (this == obj) {
            isEqual = true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            isEqual = false;
        }
        ActivityTI other = (ActivityTI) obj;
        if (name == null) {
            if (other.name != null) {
                isEqual = false;
            }
        } else if (!name.equals(other.name)) {
            isEqual = false;
        }
        return isEqual;
    }

    /**
     * Method get for the Activity`s name.
     * 
     * @return - The name of the ActivityTI.
     */
    public final String getName() {
        return name;
    }

    /**
     * Method set for the Activity`s name.
     * 
     * @param name - The new name for this ActivityTI.
     */
    public final void setName(final String name) {
        this.name = name;
    }

    /**
     * Method set for the Activity`s time.
     * 
     * @param hour - The time for add in this ActivityTI.
     */
    public final void addTime(final int hour, final int minute) {
        this.hour += hour;
        this.minute += minute;

        if (this.minute > 60) {
            this.hour++;
            this.minute -= 60;
        }
    }

    /**
     * Method get for the Activity's priority.
     * 
     * @return - int that represents this activity priority.
     */
    public final String getPriority() {
        switch (priority) {
            case 2:
                return "High";
            case 1:
                return "Medium";
            case 0:
                return "Low";
            default:
                return "";
        }
    }

    /**
     * Method set for the Activity's priority.
     * 
     * @param priority - The new priority of this ActivityTI.
     */
    public final void setPriority(final int priority) {
        this.priority = priority;
    }

    /**
     * Method compare to, uses the time of one's Activity for comparison.
     * 
     * @return - 0 if both have the same time, 1 if this activity spent more.
     *         time and -1 if not.
     */
    @Override
    public final int compareTo(final ActivityTI actTi) {
        if (getHour() > actTi.hour) {
            return -1;
        } else if (getHour() < actTi.getHour()) {
            return 1;
        }
        return 0;
    }

    /**
     * @return String in the format <Activity_name> : <Activity_time>.
     */
    @Override
    public final String toString() {
        return " "
                + getName()
                + " - "
                + (String.valueOf(getHour()).length() >= 2 ? getHour() : "0"
                        + getHour())
                + ":"
                + (String.valueOf(getMinute()).length() == 2 ? getMinute()
                        : "0" + getMinute()) + " - "
                + getPriority();
    }

    public final int getMinute() {
        return minute;
    }

    public final void setMinute(final int minute) {
        this.minute = minute;
    }

    public final int getHour() {
        return hour;
    }

    public final void setHour(final int hour) {
        this.hour = hour;
    }

    public final float getTime() {
        return getHour() * 60 + getMinute();
    }

    public final int getDayOfTI() {

        return mDayOfYear;
    }
}
