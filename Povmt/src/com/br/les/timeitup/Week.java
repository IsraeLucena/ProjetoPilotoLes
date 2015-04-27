
package com.br.les.timeitup;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

public class Week {

    private int weekOftheyear;
    private final List<ActivityTI> tiList;
    private final List<ActivityTI> tiListHigh;
    private final List<ActivityTI> tiListMedium;
    private final List<ActivityTI> tiListLow;

    /*
     * A new Week constructor
     */
    public Week() {
        weekOftheyear = Calendar.WEEK_OF_YEAR;
        tiList = new ArrayList<ActivityTI>();
        tiListHigh = new ArrayList<ActivityTI>();
        tiListMedium = new ArrayList<ActivityTI>();
        tiListLow = new ArrayList<ActivityTI>();
    }

    /**
     * Method that returns the week of the year
     * 
     * @return - The number that represents the registered week of the year.
     */
    public int getWeekOfYear() {
        return weekOftheyear;
    }

    /**
     * Add a new TI to the week or sums the already existing invested time with
     * the new one.
     * 
     * @param actTI - TI to be registered or to have its time updated
     */
    public void addTI(ActivityTI actTI) {
        if (tiList.contains(actTI)) {
            int index = tiList.indexOf(actTI);
            tiList.get(index).addTime(actTI.getHour(), actTI.getMinute());
        } else {
            tiList.add(actTI);
        }
    }

    /**
     * Decrements the current week by a specified number.
     * 
     * @param decrement - A int that will be subtracted from weekOfTheYear
     */
    public void decrementWeekOfTheYear(int decrement) {
        weekOftheyear -= decrement;
    }

    /**
     * Method to return the invested time of an specific ActivityTI of this
     * week.
     * 
     * @param actTI - The ActivityTI to be searched
     * @return - The total invested time on this Activity
     */
    public String timeOfTI(ActivityTI actTI) {
        float saida = 0;
        if (tiList.contains(actTI)) {
            int index = tiList.indexOf(actTI);
            saida = tiList.get(index).getTime();
        }
        return String.valueOf(saida);
    }

    /**
     * Creates a rank based on the invested time of each TI of this week
     * 
     * @return A sorted List of ActivityTI based on the invested time of each
     *         one
     */
    public String[] tiRank() {

        String[] out;
        if (tiList.isEmpty()) {
            out = new String[] {
                    "Nao ha atividade cadastrada"
            };
        } else {
            ordenateLevels();
            out = new String[tiList.size() + 1];

            for (int i = 0; i < out.length - 1; i++) {
                out[i] = tiList.get(i).toString();
                DecimalFormat df = new DecimalFormat("0.00");

                out[i] += " - " + df.format(proportion(tiList.get(i))) + "%";

            }

            out[out.length - 1] = "High : " + weekHigh() + " - Medium : "
                    + weekMed() + " - Low : " + weekLow();
        }

        return out;

    }

    private String weekLow() {
        return formatPriority(tiListLow);
    }

    private String weekMed() {
        return formatPriority(tiListMedium);
    }

    private String weekHigh() {
        return formatPriority(tiListHigh);
    }

    private String formatPriority(List<ActivityTI> list) {
        int hours = 0;
        int minutes = 0;
        for (ActivityTI actTi : list) {
            hours += actTi.getHour();
            minutes += actTi.getMinute();
            if (minutes >= 60) {
                hours++;
                minutes -= 60;
            }
        }
        return String.valueOf(list.size()
                + " ("
                + (String.valueOf(hours).length() >= 2 ? hours : ("0" + hours))
                + ":"
                + (String.valueOf(minutes).length() >= 2 ? minutes
                        : ("0" + minutes))
                + ")");
    }

    /**
     * Get the proportion of the activityTI
     * 
     * @return A float with the proportion
     */
    private float proportion(ActivityTI activityTI) {
        float total = 0;

        for (ActivityTI actTi : tiList) {
            total += actTi.getTime();
        }
        return activityTI.getTime() / total * 100;

    }

    @Override
    public String toString() {
        return tiList.toString();
    }

    private void ordenateLevels() {
        String high = "High";
        String medium = "Medium";
        for (ActivityTI actTi : tiList) {
            if (actTi.getPriority().equals(high)) {
                tiListHigh.add(actTi);
            } else if (actTi.getPriority().equals(medium)) {
                tiListMedium.add(actTi);
            } else {
                tiListLow.add(actTi);
            }
        }

        Collections.sort(tiListHigh);
        Collections.sort(tiListMedium);
        Collections.sort(tiListLow);

        tiList.clear();

        tiList.addAll(tiListHigh);
        tiList.addAll(tiListMedium);
        tiList.addAll(tiListLow);

    }

    public int getLastDayWithTI() {
        int maior = 0;
        for (ActivityTI actTI : tiList) {
            if (actTI.getDayOfTI() > maior) {
                maior = actTI.getDayOfTI();
            }
        }
        return maior;
    }

}
