
package com.br.les.timeitup;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class ActivityTI implements Comparable<ActivityTI> {

    private String name;
    private Date day;
    private String priority;
    private long duration;
    private final Calendar c = Calendar.getInstance();

    /**
     * Creator for a new ActivityTI.
     * 
     * @param name - The name of this Activity.
     * @param finished
     * @param started - The time spent on this Activity
     */

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

    public ActivityTI(String name, Date day, String priority, long duration) {
        super();
        this.name = name;
        this.day = day;
        this.priority = priority;
        this.duration = duration;
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
        // this.started += hour;
        // this.finished += minute;
        //
        // if (this.finished > 60) {
        // this.started++;
        // this.finished -= 60;
        // }
    }

    /**
     * Method get for the Activity's priority.
     * 
     * @return - int that represents this activity priority.
     */
    public final String getPriority() {
        return priority;
    }

    /**
     * Method set for the Activity's priority.
     * 
     * @param priority - The new priority of this ActivityTI.
     */
    public final void setPriority(final String priority) {
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
        // if (getStart() > actTi.started) {
        // return -1;
        // } else if (getStart() < actTi.getStart()) {
        // return 1;
        // }
        return 0;
    }

    public final Date getStart() {
        return day;
    }

    public final void setStart(final Date newStart) {
        this.day = newStart;
    }

    public final String getDuration() {
        return String.format(
                "%d min, %d sec",
                TimeUnit.MILLISECONDS.toHours(duration),
                TimeUnit.MILLISECONDS.toMinutes(duration)
                        -
                        TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS
                                .toHours(duration))
                );
    }

}
