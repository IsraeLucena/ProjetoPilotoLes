
package com.br.les.timeitup;

import java.util.Calendar;

public class User {

    private Week[] weeks;
    private long id;
    private String name;
    private String email;
    private static User instance = null;

    /**
     * Default Creator of a new User
     */
    public User() {
        weeks = new Week[3];
        initializeWeeks();
    }

    /**
     * Creator for a new user, with its Id, Name and Email
     * 
     * @param name - Name of the new User
     * @param email - The email for this User, it has to be unique
     */
    public User(String name, String email) {

        this.name = name;
        this.email = email;
        initializeWeeks();
    }

    public static User getInstance() {
        if (instance == null) {
            instance = new User();
        }
        return instance;
    }

    /**
     * Private method used to initialize the weeks for this User.
     */
    private void initializeWeeks() {
        weeks = new Week[3];
        for (int i = 0; i < weeks.length; i++) {
            weeks[i] = new Week();
        }
        weeks[0].decrementWeekOfTheYear(2);
        weeks[1].decrementWeekOfTheYear(1);
    }

    /**
     * Method get for the User's id.
     * 
     * @return - The Id of the User
     */
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    /**
     * Method get for the User's name
     * 
     * @return - The name of this User
     */
    public String getName() {
        return name;
    }

    /**
     * Method set for the User's name
     * 
     * @param name - The new name of this User
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Method get for the User's email
     * 
     * @return - The email of this User
     */
    public String getEmail() {
        return email;
    }

    /**
     * Method set for the User
     * 
     * @param email - The new email for this User
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * To String Method for User Format - <User's name> : <User's email>
     * 
     * @return - String that represents the User
     */
    @Override
    public String toString() {
        return "Name: " + name + "  |  Email: " + email;
    }

    /**
     * Method get for the User's week
     * 
     * @return - The weeks of this User
     */
    public Week[] getWeeks() {
        return weeks;
    }

    /**
     * Method get for the User's week
     * 
     * @return The actual week
     */
    public Week getFirstWeek() {
        return weeks[2];
    }

    /**
     * Method that verifies if the current week for this user is the same
     * current week of the year
     */
    public void isActualWeek() {
        if (weeks[2].getWeekOfYear() != Calendar.WEEK_OF_YEAR) {
            Week[] temp = new Week[3];
            System.arraycopy(weeks, 0, temp, 0, 3);

            weeks[0] = temp[1];
            weeks[0].decrementWeekOfTheYear(1);

            weeks[1] = temp[2];
            weeks[1].decrementWeekOfTheYear(1);

            weeks[2] = new Week();
        }
    }

    /**
     * Method get for the User's week
     * 
     * @return The second week
     */
    public Week getSecondWeek() {

        return weeks[1];
    }

    /**
     * Method get for the User's week
     * 
     * @return The third week
     */
    public Week getThirdWeek() {

        return weeks[0];
    }

    public int getlastDayWithTI() {

        return weeks[2].getLastDayWithTI();
    }

}
